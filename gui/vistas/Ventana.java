package gui.vistas;

import javax.swing.*;

import gui.controlador.*;

import java.awt.*;

/**
 * Clase Ventana
 * Esta clase es la encargada de gestionar las distintas ventanas de la
 * aplicación.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Ventana extends JFrame {
	// Cartas
	private JPanel cartas;
	private String cartaPrevia, cartaActual;

	// Nombres de las cartas
	private final static String LOGINGESTOR = "logInGestor";
	private final static String LOGINEMPLEADO = "logInEmpleado";
	private final static String ENVIARMENSAJES = "enviarMensajes";
	private final static String AJUSTARCLIMATIZACION = "ajustarClimatizacion";
	private final static String EXPOSICIONES = "exposiciones";
	private final static String REGISTRO = "registro";
	private final static String PANELPRINCIPAL = "panelPrincipal";
	private final static String GESTORPRINCIPAL = "gestorPrincipal";
	private final static String EMPLEADOPRINCIPAL = "empleadoPrincipal";
	private final static String CLIENTEPRINCIPAL = "clientePrincipal";
	private final static String VENTAENTRADAS = "ventaEntradas";
	private final static String DESBLOQUEOCLIENTES = "desbloqueoClientes";
	private final static String PERFILEMPLEADO = "perfilEmpleado";


	// Vistas y controladores
	private ControladorPantallaPrincipal controladorPantallaPrincipal;
	private PantallaPrincipal vistaPantallaPrincipal;

	private LoginEmpleado vistaLoginEmpleado;
	private ControladorLoginEmpleado controladorLoginEmpleado;

	private BusquedaExposiciones vistaBusquedaExposiciones;
	private ControladorBusquedaExposiciones controladorBusquedaExposiciones;

	private LoginGestor vistaLoginGestor;
	private ControladorLoginGestor controladorLoginGestor;

	private RegistroUsuario vistaRegistro;
	private ControladorRegistro controladorRegistro;

	private EmpleadoPrincipal vistaEmpleadoPrincipal;

	private ClientePrincipal vistaClientePrincipal;
	private ControladorCliente controladorCliente;

	private VentaEntradas vistaVentaEntradas;
	private ControladorVentaEntradas controladorVentaEntradas;

	private GestorPrincipal vistaGestorPrincipal;
	private ControladorGestor controladorGestor;

	private ControladorEnviarMensajes controladorEnviarMensajes;
	private EnviarMensajes vistaEnviarMensajes;

	private ControladorEmpleado controladorEmpleado;

	private ControladorAjustarClimatizacion controladorAjustarClimatizacion;
	private AjustarClimatizacion vistaAjustarClimatizacion;

	private ControladorDesbloqueoClientes controladorDesbloqueoClientes;
	private DesbloqueoClientes vistaDesbloqueoClientes;

	private ControladorPerfilEmpleado controladorPerfilEmpleado;
	private PerfilEmpleado vistaPerfilEmpleado;


	/**
	 * Constructor de la clase Ventana
	 */
	public Ventana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		cartas = new JPanel();
		cartas.setLayout(new CardLayout());

		this.vistaLoginEmpleado = new LoginEmpleado();
		cartas.add(vistaLoginEmpleado, LOGINEMPLEADO);

		this.vistaEnviarMensajes = new EnviarMensajes();
		cartas.add(vistaEnviarMensajes, ENVIARMENSAJES);

		this.vistaLoginGestor = new LoginGestor();
		cartas.add(vistaLoginGestor, LOGINGESTOR);

		this.vistaRegistro = new RegistroUsuario();
		cartas.add(vistaRegistro, REGISTRO);

		this.vistaBusquedaExposiciones = new BusquedaExposiciones();
		cartas.add(vistaBusquedaExposiciones, EXPOSICIONES);

		this.vistaVentaEntradas = new VentaEntradas();
		cartas.add(vistaVentaEntradas, VENTAENTRADAS);

		setContentPane(cartas);
	}

	/**
	 * Devuelve el nombre del panel principal
	 * 
	 * @return PanelPrincipal string PANELPRINCIPAL
	 */
	public String getPanelPrincipal() {
		return PANELPRINCIPAL;
	}

	/**
	 * Devuelve el nombre del panel de logInGestor
	 * 
	 * @return LogInGestor string LOGINGESTOR
	 */
	public String getLogInGestor() {
		return LOGINGESTOR;
	}

	/**
	 * Devuelve el nombre del panel de logInEmpleado
	 * 
	 * @return LoginEmpleado string LOGINEMPLEADO
	 */
	public String getLogInEmpleado() {
		return LOGINEMPLEADO;
	}

	/**
	 * Devuelve el nombre del panel de exposiciones
	 * 
	 * @return Exposiciones string EXPOSICIONES
	 */
	public String getExposiciones() {
		return EXPOSICIONES;
	}

	/**
	 * Devuelve el nombre del panel de registro
	 * 
	 * @return Registro string REGISTRO
	 */
	public String getRegistro() {
		return REGISTRO;
	}

	/**
	 * Devuelve el nombre del panel de empleado principal
	 * 
	 * @return EmpleadoPrincipal string EMPLEADOPRINCIPAL
	 */
	public String getEmpleadoPrincipal() {
		return EMPLEADOPRINCIPAL;
	}

	/**
	 * Devuelve el nombre del panel de cliente principal
	 * 
	 * @return ClientePrincipal string CLIENTEPRINCIPAL
	 */
	public String getClientePrincipal() {
		return CLIENTEPRINCIPAL;
	}

	/**
	 * Devuelve el nombre del panel de gestor principal
	 * 
	 * @return GestorPrincipal string GESTORPRINCIPAL
	 */
	public String getGestorPrincipal() {
		return GESTORPRINCIPAL;
	}

	/**
	 * Devuelve el nombre del panel de enviar mensajes
	 * 
	 * @return EnviarMensajes string ENVIARMENSAJES
	 */
	public String getEnviarMensajes() {
		return ENVIARMENSAJES;
	}

	/**
	 * Devuelve el nombre del panel de ajustar climatización
	 * 
	 * @return AjustarClimatizacion string AJUSTARCLIMATIZACION
	 */
	public String getAjustarClimatizacion() {
		return AJUSTARCLIMATIZACION;
	}

	/**
	 * Devuelve el nombre del panel de desbloquo de clientes
	 * 
	 * @return DesbloquoClientes string DESBLOQUEOCLIENTES
	 */
	public String getDesbloqueoClientes() {
		return DESBLOQUEOCLIENTES;
	}

	/**
	 * Devuelve el nombre del panel de desbloquo de clientes
	 * 
	 * @return DesbloquoClientes string PERFILEMPLEADO
	 */
	public String getPerfilEmpleado() {
		return PERFILEMPLEADO;
	}

	/**
	 * Devuelve el nombre del panel de la venta de entradas
	 * 
	 * @return VentaEntradas string VENTAENTRADAS
	 */
	public String getVentaEntradas() {
		return VENTAENTRADAS;
	}

	/**
	 * Devuelve la vista de la pantalla principal
	 * 
	 * @return PantallaPrincipal vistaPantallaPrincipal
	 */
	public PantallaPrincipal getVistaPantallaPrincipal() {
		this.vistaPantallaPrincipal = new PantallaPrincipal();
		cartas.add(vistaPantallaPrincipal, PANELPRINCIPAL);
		return vistaPantallaPrincipal;
	}

	public PerfilEmpleado getVistaPerfilEmpleado() {
		if (this.vistaPerfilEmpleado == null) {
			this.vistaPerfilEmpleado = new PerfilEmpleado();
			cartas.add(vistaPerfilEmpleado, "perfilEmpleado");
		}
		return vistaPerfilEmpleado;
	}

	public void setControladorPerfilEmpleado(ControladorPerfilEmpleado controlador) {
		getVistaPerfilEmpleado().setControlador(controlador.getGuardarCambiosListener(),
				controlador.getCancelarCambiosListener());
	}

	/**
	 * Devuelve la vista de registro
	 * 
	 * @return RegistroUsuario vistaRegistro
	 */
	public RegistroUsuario getVistaRegistro() {
		return vistaRegistro;
	}

	/**
	 * Devuelve la vista de logInGestor
	 * 
	 * @return LoginGestor vistaLoginGestor
	 */
	public LoginGestor getVistaLogInGestor() {
		return vistaLoginGestor;
	}

	/**
	 * Devuelve la vista de logInEmpleado
	 * 
	 * @return LoginEmpleado vistaLoginEmpleado
	 */
	public LoginEmpleado getVistaLoginEmpleado() {
		return vistaLoginEmpleado;
	}

	/**
	 * Devuelve la vista de enviar mensajes
	 * 
	 * @return EnviarMensajes vistaEnviarMensajes
	 */
	public EnviarMensajes getVistaEnviarMensajes() {
		return vistaEnviarMensajes;
	}

	/**
	 * Devuelve la vista de ajustar climatización
	 * 
	 * @return AjustarClimatizacion vistaAjustarClimatizacion
	 */
	public AjustarClimatizacion getVistaAjustarClimatizacion() {
		return vistaAjustarClimatizacion;
	}

	/**
	 * Devuelve la vista de desbloqueo de clientes
	 * 
	 * @return DesbloqueoClientes vistaDesbloqueoClientes
	 */
	public DesbloqueoClientes getVistaDesbloqueoClientes() {
		return vistaDesbloqueoClientes;
	}

	/**
	 * Devuelve la vista de la venta de entradas
	 * 
	 * @return VentaEntradas vistaVentaEntradas
	 */
	public VentaEntradas getVistaVentaEntradas() {
		return vistaVentaEntradas;
	}

	/**
	 * Devuelve la vista de empleado principal
	 * 
	 * @return EmpleadoPrincipal vistaEmpleadoPrincipal
	 */
	public EmpleadoPrincipal getVistaEmpleadoPrincipal() {
		return vistaEmpleadoPrincipal;
	}

	/**
	 * Devuelve la vista de gestor principal
	 * 
	 * @return GestorPrincipal vistaGestorPrincipal
	 */
	public GestorPrincipal getVistaGestorPrincipal() {
		if (this.vistaGestorPrincipal != null) {
			return vistaGestorPrincipal;
		}

		return vistaGestorPrincipal;
	}

	/**
	 * Devuelve la vista de cliente principal
	 */
	public ClientePrincipal getVistaClientePrincipal() {
		return vistaClientePrincipal;
	}

	/**
	 * Devuelve la vista de busqueda de exposiciones
	 */
	public BusquedaExposiciones getVistaBusquedaExposiciones() {
		return vistaBusquedaExposiciones;
	}

	/**
	 * Establece el controlador de la vista de gestor principal
	 * 
	 * @param controlador ControladorGestor
	 */
	public void setControladorGestor(ControladorGestor controlador) {
		this.controladorGestor = controlador;
		this.vistaGestorPrincipal.setControlador(controladorGestor.getObraEjecutarListener(),
				controladorGestor.getObraAgregarListener(), controladorGestor.getObraLeerCSVListener(),
				controladorGestor.getSalaEjecutarListener(),
				controladorGestor.getExposicionEjecutarListener(), controladorGestor.getExposicionAgregarListener(),
				controladorGestor.getEmpleadoAgregarListener(),
				controladorGestor.getEmpleadoConfigurarContraseniaListener(),
				controladorGestor.getSorteoAgregarListener(),
				controladorGestor.getDescuentoAgregarListener(),
				controladorGestor.getActividadAgregarListener(),
				controladorGestor.getCerrarSesionListener(),
				controladorGestor.getCambiarHorasListener(),
				controladorGestor.getCambiarPenalizacionSorteosListener(),
				controladorGestor.getSorteoCelebrarListener());
	}

	/**
	 * Establece el controlador de la vista de cliente principal
	 * 
	 * @param controlador ControladorCliente
	 */
	public void setControladorCliente(ControladorCliente controlador) {
		this.controladorCliente = controlador;
		this.vistaClientePrincipal.setControlador(controladorCliente.getComprarListener(),
				controladorCliente.getActualizarDatos(), controladorCliente.getCerrarSesion(),
				controladorCliente.getInscribirse(), controlador.getFiltroFechaListener(),
				controlador.getFiltroTempListener(), controlador.getFiltroTipoObraListener(),
				controlador.getEliminarFiltrosListener());
	}

	/**
	 * Establece el controlador de la vista de empleado principal
	 * 
	 * @param controlador ControladorEmpleado
	 */
	public void setControladorEmpleado(ControladorEmpleado controlador) {
		this.controladorEmpleado = controlador;
		this.vistaEmpleadoPrincipal.setControlador(controlador.getEnviarMsjListener(),
				controlador.getClimatizacionListener(), controlador.getVenderEntrada(),
				controlador.getCerrarSesionListener(), controlador.getDesbloquearListener(),
				controlador.getInscribirActividad(), controlador.getPerfilListener());
	}

	/**
	 * Establece el controlador para la busqueda de exposiciones
	 * 
	 * @param controlador ControladorBusquedaExposiciones
	 */
	public void setControladorBusquedaExposiciones(ControladorBusquedaExposiciones controlador) {
		this.controladorBusquedaExposiciones = controlador;
		this.vistaBusquedaExposiciones.setControlador(controlador.getAtrasListener());
	}

	/**
	 * Establece el controlador para enviar mensajes
	 * 
	 * @param controlador ControladorEnviarMensajes
	 */
	public void setControladorEnviarMensajes(ControladorEnviarMensajes controlador) {
		if (controladorEnviarMensajes == null) {
			this.controladorEnviarMensajes = controlador;
			this.vistaEnviarMensajes.setControlador(controlador.getEnviarListener(), controlador.getAtrasListener());
		}
	}

	/**
	 * Establece el controlador para ajustar climatización
	 * 
	 * @param controlador ControladorAjustarClimatizacion
	 */
	public void setControladorAjustarClimatizacion(ControladorAjustarClimatizacion controlador) {
		this.controladorAjustarClimatizacion = controlador;
		this.vistaAjustarClimatizacion.setControlador(controlador.getAtrasListener(),
				controlador.getConfirmarListener(), controlador.getTemperaturaListener(),
				controlador.getHumedadListener());

	}

	/**
	 * Establece el controlador de la vista de ventas
	 * 
	 * @param controlador controladorVentaEntradas
	 */
	public void setControladorVentaEntradas(ControladorVentaEntradas controlador) {
		this.controladorVentaEntradas = controlador;
		this.vistaVentaEntradas.setControlador(controlador.getVentaListener(),
				controlador.getCerrarSesionListener(), controlador.getAtrasListener());
	}

	/**
	 * Establece el controlador de la vista de desbloquo de clientes
	 * 
	 * @param controlador controladorDesbloqueoClientes
	 */
	public void setControladorDesbloqueoClientes(ControladorDesbloqueoClientes controlador) {
		this.controladorDesbloqueoClientes = controlador;
		this.vistaDesbloqueoClientes.setControlador(controlador.getAtrasListener(),
				controlador.getDesbloquearListener());
	}

	/**
	 * Establece el controlador de la pantalla principal
	 * 
	 * @param controlador controladorPantallaPrincipal
	 */
	public void setControladorPantallaPrincipal(ControladorPantallaPrincipal controlador) {
		this.controladorPantallaPrincipal = controlador;
		this.vistaPantallaPrincipal.setControlador(
				controladorPantallaPrincipal.getBuscaListener(),
				controladorPantallaPrincipal.getAcceptListener(),
				controladorPantallaPrincipal.getGestorListener(),
				controladorPantallaPrincipal.getEmpleadoListener(),
				controladorPantallaPrincipal.getRegistrarListener());
	}

	/**
	 * Establece el controlador general de la aplicación
	 * 
	 * @param controlador El controlador de Expofy
	 */
	public void setControlador(Controlador controlador) {
		this.controladorPantallaPrincipal = controlador.getControladorPantallaPrincipal();
		setControladorPantallaPrincipal(controladorPantallaPrincipal);

		this.controladorRegistro = controlador.getControladorRegistro();
		this.vistaRegistro.setControlador(controladorRegistro.getRegistrarListener(),
				controladorRegistro.getCancelarListener());

		this.controladorLoginGestor = controlador.getControladorLoginGestor();
		this.vistaLoginGestor.setControlador(controladorLoginGestor.getAceptarListener(),
				controladorLoginGestor.getAtrasListener());

		this.controladorLoginEmpleado = controlador.getControladorLoginEmpleado();
		this.vistaLoginEmpleado.setControlador(controladorLoginEmpleado.getAcceptListener(),
				controladorLoginEmpleado.getAtrasListener());

		this.controladorBusquedaExposiciones = controlador.getControladorBusquedaExposiciones();
		this.vistaBusquedaExposiciones.setControlador(controladorBusquedaExposiciones.getAtrasListener());

	}

	/**
	 * Muestra el panel indicado
	 * 
	 * @param carta El nombre del panel a mostrar
	 */
	public void mostrarPanel(String carta) {
		cartaPrevia = cartaActual;
		cartaActual = carta;

		CardLayout l = (CardLayout) cartas.getLayout();
		l.show(cartas, carta);
	}

	/**
	 * Muestra el panel previo, util para ir hacia atrás.
	 */
	public void mostrarPanelPrevio() {
		cartaActual = cartaPrevia;
		if (cartaPrevia != null) {
			mostrarPanel(cartaPrevia);
		}

	}

	public JPanel getCartas() {
		return cartas;
	}

	public void setCartaClientePrincipal() {
		this.vistaClientePrincipal = new ClientePrincipal();
		cartas.add(vistaClientePrincipal, CLIENTEPRINCIPAL);
	}

	public void setCartaEmpleadoPrincipal() {
		this.vistaEmpleadoPrincipal = new EmpleadoPrincipal();
		cartas.add(vistaEmpleadoPrincipal, EMPLEADOPRINCIPAL);
	}

	public void setCartaGestorPrincipal() {
		this.vistaGestorPrincipal = new GestorPrincipal();
		cartas.add(vistaGestorPrincipal, GESTORPRINCIPAL);
	}

	public void setCartaAjustarClimatizacion() {
		this.vistaAjustarClimatizacion = new AjustarClimatizacion();
		cartas.add(vistaAjustarClimatizacion, AJUSTARCLIMATIZACION);
	}

	public void setCartaDesbloqueoClientes() {
		this.vistaDesbloqueoClientes = new DesbloqueoClientes();
		cartas.add(vistaDesbloqueoClientes, DESBLOQUEOCLIENTES);
	}

	public void setCartaPantallaPrincipal() {

	}
}
