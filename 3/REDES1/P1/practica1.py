
from rc1_pcap import *
import sys
import binascii
import signal
import argparse
from argparse import RawTextHelpFormatter
import time
import logging

ETH_FRAME_MAX = 1514
PROMISC = 1
NO_PROMISC = 0
TO_MS = 10
num_paquete = 0
TIME_OFFSET = 2700
flag = 0
flag1 = 0

def signal_handler(nsignal,frame):
    logging.info('Control C pulsado')
    logging.info(f'Total de paquetes capturados: {num_paquete-1}')
    if handle:
        pcap_breakloop(handle)

def procesa_paquete(us,header,data):
    global num_paquete, pdumper, flag
    header.ts.tv_sec = TIME_OFFSET + header.ts.tv_sec
    logging.info('Nuevo paquete de {} bytes capturado en el timestamp UNIX {}.{}'.format(header.len,header.ts.tv_sec,header.ts.tv_sec))
    num_paquete += 1 
    #TODO imprimir los N primeros bytes
    #Escribir el tráfico al fichero de captura con el offset temporal

    n_bytes = args.nbytes
    if header.len < n_bytes:
        n_bytes = header.len
    logging.info("Primeros bytes del paquete: " + " ".join('{:02x}'.format(i) for i in data[:n_bytes]))
    
    if flag == 1 and flag1 == 1: 
        if data[12] == 0x08 and data[13] == 0x06:
            pcap_dump(pdumperARP, header, data)
        else:
            pcap_dump(pdumperOther, header, data)

    # Stop capturing after reaching the specified number of packets
    if args.npkts and num_paquete >= args.npkts:
        pcap_breakloop(handle)

if __name__ == "__main__":
    global pdumper,args,handle
    parser = argparse.ArgumentParser(description='Captura tráfico de una interfaz ( o lee de fichero) y muestra la longitud y timestamp de los paquetes',
    formatter_class=RawTextHelpFormatter)
    parser.add_argument('--file', dest='tracefile', default=False,help='Fichero pcap a abrir')
    parser.add_argument('--itf', dest='interface', default=False,help='Interfaz a abrir')
    parser.add_argument('--nbytes', dest='nbytes', type=int, default=14,help='Número de bytes a mostrar por paquete')
    parser.add_argument('--npkts', dest='npkts', type=int, default=None, help='Número de paquetes a capturar')
    parser.add_argument('--debug', dest='debug', default=False, action='store_true',help='Activar Debug messages')
    args = parser.parse_args()

    if args.debug:
        logging.basicConfig(level = logging.DEBUG, format = '[%(asctime)s %(levelname)s]\t%(message)s')
    else:
        logging.basicConfig(level = logging.INFO, format = '[%(asctime)s %(levelname)s]\t%(message)s')

    if args.tracefile is False and args.interface is False:
        logging.error('No se ha especificado interfaz ni fichero')
        parser.print_help()
        sys.exit(-1)

    signal.signal(signal.SIGINT, signal_handler)

    errbuf = bytearray()
    handle = None
    pdumperARP = None
    pdumperOther = None
    
    # Handle pcap file if --file argument is provided
    if args.tracefile:
        handle = pcap_open_offline(args.tracefile, errbuf)
    
    # Handle interface capture if --itf argument is provided
    elif args.interface:
        handle = pcap_open_live(args.interface, ETH_FRAME_MAX, 1, 100, errbuf)
        captureARP = "capturaARP." + args.interface + "." + str(int(time.time())) + ".pcap"
        descr = pcap_open_dead(DLT_EN10MB, ETH_FRAME_MAX)
        pdumperARP = pcap_dump_open(descr, captureARP)
        if pdumperARP:
            flag = 1

        captureName = "captura." + args.interface + "." + str(int(time.time())) + ".pcap"
        descr = pcap_open_dead(DLT_EN10MB, ETH_FRAME_MAX)
        pdumperOther = pcap_dump_open(descr, captureName)
        if pdumperOther:
            flag1 = 1 
    
    ret = pcap_loop(handle,-1,procesa_paquete,None) 
    if ret == -1:
        logging.error('Error al capturar un paquete')
    elif ret == -2:
        logging.debug('pcap_breakloop() llamado')
    elif ret == 0:
        logging.debug('No mas paquetes o limite superado')
    logging.info('{} paquetes procesados'.format(num_paquete))
    pcap_close(handle)
    
    if flag is 1 and flag1 is 1:
        pcap_dump_close(pdumperARP)
        pcap_dump_close(pdumperOther)
    elif flag is 1 and flag1 is 0:
        pcap_dump_close(pdumperARP)
    elif flag is 0 and flag1 is 1:
        pcap_dump_close(pdumperOther)
