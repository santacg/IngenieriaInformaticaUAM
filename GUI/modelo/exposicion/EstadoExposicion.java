package GUI.modelo.exposicion;
/**
 * Clase EstadoExposicion.
 * Representa los posibles estados en los que puede encontrarse una exposición.
 * Estos estados permiten a los administradores crear, publicar, cancelar y
 * prorrogar exposiciones.
 * 
 * Los estados disponibles son:
 * {@link #EN_CREACION}: La exposición está en creación.
 * {@link #PUBLICADA}: La exposición se encuentra publicada.
 * {@link #CANCELADA}: La exposición se cancela.
 * {@link #PRORROGADA}: La exposición se prorroga.
 * {@link #CERRADATEMPORALMENTE}: La exposición se cierra temporalmente.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public enum EstadoExposicion {
   EN_CREACION, 
   PUBLICADA,
   CANCELADA,
   PRORROGADA,
   CERRADATEMPORALMENTE,
}
