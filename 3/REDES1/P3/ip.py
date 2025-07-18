'''
    ip.py
    
    Funciones necesarias para implementar el nivel IP
    Autor: Javier Ramos <javier.ramos@uam.es>
    2022 EPS-UAM
'''
from ethernet import *
from arp import *
from fcntl import ioctl
import subprocess
SIOCGIFMTU = 0x8921
SIOCGIFNETMASK = 0x891b
# Diccionario de protocolos. Las claves con los valores numéricos de protocolos de nivel superior a IP
# por ejemplo (1, 6 o 17) y los valores son los nombres de las funciones de callback a ejecutar.
protocols = {}
# Tamaño mínimo de la cabecera IP
IP_MIN_HLEN = 20
# Tamaño máximo de la cabecera IP
IP_MAX_HLEN = 60
IP_ETHType = bytes([0x08, 0x00])


def chksum(msg):
    '''
        Nombre: chksum
        Descripción: Esta función calcula el checksum IP sobre unos datos de entrada dados (msg)
        Argumentos:
            -msg: array de bytes con el contenido sobre el que se calculará el checksum
        Retorno: Entero de 16 bits con el resultado del checksum en ORDEN DE RED
    '''
    s = 0
    y = 0x27af
    for i in range(0, len(msg), 2):
        if (i+1) < len(msg):
            a = msg[i]
            b = msg[i+1]
            s = s + (a+(b << 8))
        elif (i+1) == len(msg):
            s += msg[i]
        else:
            raise 'Error calculando el checksum'
    y = y & 0x00ff
    s = s + (s >> 16)
    s = ~s & 0xffff

    return s


def getMTU(interface):
    '''
        Nombre: getMTU
        Descripción: Esta función obteiene la MTU para un interfaz dada
        Argumentos:
            -interface: cadena con el nombre la interfaz sobre la que consultar la MTU
        Retorno: Entero con el valor de la MTU para la interfaz especificada
    '''
    s = socket.socket(socket.AF_PACKET, socket.SOCK_RAW)
    ifr = struct.pack('16sH', interface.encode("utf-8"), 0)
    mtu = struct.unpack('16sH', ioctl(s, SIOCGIFMTU, ifr))[1]

    s.close()

    return mtu


def getNetmask(interface):
    '''
        Nombre: getNetmask
        Descripción: Esta función obteiene la máscara de red asignada a una interfaz 
        Argumentos:
            -interface: cadena con el nombre la interfaz sobre la que consultar la máscara
        Retorno: Entero de 32 bits con el valor de la máscara de red
    '''
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    ip = fcntl.ioctl(
        s.fileno(),
        SIOCGIFNETMASK,
        struct.pack('256s', (interface[:15].encode('utf-8')))
    )[20:24]
    s.close()
    return struct.unpack('!I', ip)[0]


def getDefaultGW(interface):
    '''
        Nombre: getDefaultGW
        Descripción: Esta función obteiene el gateway por defecto para una interfaz dada
        Argumentos:
            -interface: cadena con el nombre la interfaz sobre la que consultar el gateway
        Retorno: Entero de 32 bits con la IP del gateway
    '''
    p = subprocess.Popen(
        ['ip r | grep default | awk \'{print $3}\''], stdout=subprocess.PIPE, shell=True)
    dfw = p.stdout.read().decode('utf-8')
    print(dfw)
    return struct.unpack('!I', socket.inet_aton(dfw))[0]


def process_IP_datagram(us, header, data, srcMac):
    '''
        Nombre: process_IP_datagram
        Descripción: Esta función procesa datagramas IP recibidos.
            Se ejecuta una vez por cada trama Ethernet recibida con Ethertype 0x0800
            Esta función debe realizar, al menos, las siguientes tareas:
                -Extraer los campos de la cabecera IP (includa la longitud de la cabecera)
                -Calcular el checksum y comprobar que es correcto                    
                -Analizar los bits de de MF y el offset. Si el offset tiene un valor != 0 dejar de procesar el datagrama (no vamos a reensamblar)
                -Loggear (usando logging.debug) el valor de los siguientes campos:
                    -Longitud de la cabecera IP
                    -IPID
                    -TTL
                    -Valor de las banderas DF y MF
                    -Valor de offset
                    -IP origen y destino
                    -Protocolo
                -Comprobar si tenemos registrada una función de callback de nivel superior consultando el diccionario protocols y usando como
                clave el valor del campo protocolo del datagrama IP.
                    -En caso de que haya una función de nivel superior registrada, debe llamarse a dicha funciñón 
                    pasando los datos (payload) contenidos en el datagrama IP.

        Argumentos:
            -us: Datos de usuario pasados desde la llamada de pcap_loop. En nuestro caso será None
            -header: cabecera pcap_pktheader
            -data: array de bytes con el contenido del datagrama IP
            -srcMac: MAC origen de la trama Ethernet que se ha recibido
        Retorno: Ninguno
    '''
    ipVersion = (data[0]) >> 4
    IHL = (data[0] & 0x0F) * 4
    typeOfService = data[1]
    totalLength = struct.unpack("!H", data[2:4])[0]
    ipID = struct.unpack("!H", data[4:6])[0]
    Flags = (data[6] & 0b11100000) >> 5
    DF = (Flags & 0b010) >> 1
    MF = Flags & 0b001
    Offset = (data[6] & 0b00011111) << 8 | (data[7])
    TimeToLive = data[8]
    Protocol = data[9]
    HeaderChecksum = data[10:12]
    ipOrig = struct.unpack("!I", data[12:16])[0]
    ipDest = struct.unpack("!I", data[16:20])[0]

    if chksum(data[:IHL]) != 0:
        return

    if Offset != 0:
        return

    logging.debug(f"IP Header Length: {IHL}")
    logging.debug(f"IPID: {ipID}")
    logging.debug(f"TTL: {TimeToLive}")
    logging.debug(f"DF Flag: {DF}")
    logging.debug(f"MF Flag: {MF}")
    logging.debug(f"Offset: {Offset}")
    logging.debug(f"IP Source: {ipOrig}")
    logging.debug(f"IP Destination: {ipDest}")
    logging.debug(f"Protocol: {Protocol}")

    if Protocol in protocols:
        payload = data[IHL:]
        protocols[Protocol](us, header, payload, ipOrig)
    
    

def registerIPProtocol(callback, protocol):
    '''
        Nombre: registerIPProtocol
        Descripción: Esta función recibirá el nombre de una función y su valor de protocolo IP asociado y añadirá en la tabla 
            (diccionario) de protocolos de nivel superior dicha asociación. 
            Este mecanismo nos permite saber a qué función de nivel superior debemos llamar al recibir un datagrama IP  con un 
            determinado valor del campo protocolo (por ejemplo TCP o UDP).
            Por ejemplo, podemos registrar una función llamada process_UDP_datagram asociada al valor de protocolo 17 y otra 
            llamada process_ICMP_message asocaida al valor de protocolo 1. 
        Argumentos:
            -callback_fun: función de callback a ejecutar cuando se reciba el protocolo especificado. 
                La función que se pase como argumento debe tener el siguiente prototipo: funcion(us,header,data,srcIp):
                Dónde:
                    -us: son los datos de usuarios pasados por pcap_loop (en nuestro caso este valor será siempre None)
                    -header: estructura pcap_pkthdr que contiene los campos len, caplen y ts.
                    -data: payload del datagrama IP. Es decir, la cabecera IP NUNCA se pasa hacia arriba.
                    -srcIP: dirección IP que ha enviado el datagrama actual.
                La función no retornará nada. Si un datagrama se quiere descartar basta con hacer un return sin valor y dejará de procesarse.
            -protocol: valor del campo protocolo de IP para el cuál se quiere registrar una función de callback.
        Retorno: Ninguno 
    '''

    protocols[protocol] = callback


def initIP(interface, opts=None):
    global myIP, MTU, netmask, defaultGW, ipOpts, ipID
    '''
        Nombre: initIP
        Descripción: Esta función inicializará el nivel IP. Esta función debe realizar, al menos, las siguientes tareas:
            -Llamar a initARP para inicializar el nivel ARP
            -Obtener (llamando a las funciones correspondientes) y almacenar en variables globales los siguientes datos:
                -IP propia
                -MTU
                -Máscara de red (netmask)
                -Gateway por defecto
            -Almacenar el valor de opts en la variable global ipOpts
            -Registrar a nivel Ethernet (llamando a registerCallback) la función process_IP_datagram con el Ethertype 0x0800
            -Inicializar el valor de IPID con el número de pareja
        Argumentos:
            -interface: cadena de texto con el nombre de la interfaz sobre la que inicializar ip
            -opts: array de bytes con las opciones a nivel IP a incluir en los datagramas o None si no hay opciones a añadir
        Retorno: True o False en función de si se ha inicializado el nivel o no
    '''
    if initARP(interface) == False:
        return False

    myIP = getIP(interface)
    MTU = getMTU(interface)
    netmask = getNetmask(interface)
    defaultGW = getDefaultGW(interface)

    if opts is not None:
        if len(opts) > IP_MAX_HLEN:
            return False
    ipOpts = opts
    registerCallback(process_IP_datagram, IP_ETHType)
    ipID = 8

    return True


def sendIPDatagram(dstIP, data, protocol):
    global ipID, ipOpts, myIP, MTU
    '''
        Nombre: sendIPDatagram
        Descripción: Esta función construye un datagrama IP y lo envía. En caso de que los datos a enviar sean muy grandes la función
        debe generar y enviar el número de fragmentos IP que sean necesarios.
        Esta función debe realizar, al menos, las siguientes tareas:
            -Determinar si se debe fragmentar o no y calcular el número de fragmentos
            -Para cada datagrama o fragmento:
                -Construir la cabecera IP con los valores que corresponda.Incluir opciones en caso de que ipOpts sea distinto de None
                -Calcular el checksum sobre la cabecera y añadirlo a la cabecera
                -Añadir los datos a la cabecera IP
                -En el caso de que sea un fragmento ajustar los valores de los campos MF y offset de manera adecuada
                -Enviar el datagrama o fragmento llamando a sendEthernetFrame. Para determinar la dirección MAC de destino
                al enviar los datagramas se debe hacer unso de la máscara de red:                  
            -Para cada datagrama (no fragmento):
                -Incrementar la variable IPID en 1.
        Argumentos:
            -dstIP: entero de 32 bits con la IP destino del datagrama 
            -data: array de bytes con los datos a incluir como payload en el datagrama
            -protocol: valor numérico del campo IP protocolo que indica el protocolo de nivel superior de los datos
            contenidos en el payload. Por ejemplo 1, 6 o 17.
        Retorno: True o False en función de si se ha enviado el datagrama correctamente o no
          
    '''
    ip_header = bytearray()

    len_data = len(data)

    if ipOpts is None:
        maxData = MTU - IP_MIN_HLEN
    else:
        len_ipOpts = len(ipOpts)
        maxData = MTU - (len_ipOpts + IP_MIN_HLEN)

    if maxData % 8 != 0:
        maxData = (maxData // 8) * 8

    n_fragments = len_data // maxData + (1 if len_data % maxData != 0 else 0)

    version = 0b0100
    IHL = (IP_MIN_HLEN if ipOpts is None else (IP_MIN_HLEN + len_ipOpts)) // 4
    firstByte = (version << 4) | IHL
    typeOfService = 0x10
    IPID_header = ipID
    TimeToLive = 64

    # Construir la cabecera IP
    ip_header.extend(struct.pack('!B', firstByte))
    ip_header.extend(struct.pack('!B', typeOfService))
    ip_header.extend(struct.pack('!H', 0))  # Espacio para Total Length
    ip_header.extend(struct.pack('!H', IPID_header))
    ip_header.extend(struct.pack('!H', 0))  # Espacio para flags y offset
    ip_header.extend(struct.pack('!B', TimeToLive))
    ip_header.extend(struct.pack('!B', protocol))
    ip_header.extend(struct.pack('H', 0))  # Espacio para el checksum
    ip_header.extend(struct.pack('!I', myIP))
    ip_header.extend(struct.pack('!I', dstIP))

    # Añadir opciones IP si las hay
    if ipOpts is not None:
        ip_header.extend(ipOpts)
        len_header = len(ip_header)
        # Rellenar con ceros para que la longitud total sea múltiplo de 4
        while len_header % 4 != 0:
            ip_header.extend([0])

    len_header = len(ip_header)
    if (netmask & dstIP) == (netmask & myIP):
        macDest = ARPResolution(dstIP)
    else:
        macDest = ARPResolution(defaultGW)

    if macDest == None:
        logging.error("No Mac obtained")
        return False
    
    for i in range(n_fragments):
        if i < n_fragments - 1:
            totalLength = len_header + maxData
            MF = 0b1
        # Ultimo fragmento
        else:
            lastData = len_data - maxData * i
            totalLength = len_header + lastData
            MF = 0b0
        # Insertamos Total Length
        ip_header[2:4] = struct.pack('!H', totalLength)

        # Insertamos la flag y el offset como 2 bytes
        flag = 0b00 | MF
        Offset = (maxData * i) // 8
        flag_offset = (flag << 13) | Offset
        ip_header[6:8] = struct.pack('!H', flag_offset)

        # Calculamos y añadimos el checksum
        checksum = chksum(ip_header)
        ip_header[10:12] = struct.pack('H', checksum)

        # Ensamblamos el fragmento
        fragment_start = Offset * 8
        fragment_end = fragment_start + \
            (lastData if i == n_fragments - 1 else maxData)
        fragment_data = data[fragment_start:fragment_end]
        packet = ip_header + fragment_data

        # Enviar el datagrama o fragmento
        sendEthernetFrame(packet, totalLength, IP_ETHType, macDest)

    # Incrementar IPID
    ipID += 1
    return True
