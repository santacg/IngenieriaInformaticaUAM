package gui.modelo.centroExposicion;

/**
 * Enumerado TipoActividad.
 * 
 * Representa los tipos de actividades que se pueden realizar en un centro de
 * exposición.
 * 
 * Los tipos de actividades disponibles son:
 * {@link #CONFERENCIA}: Conferencia.
 * {@link #MESA_REDONDA}: Mesa redonda.
 * {@link #PROYECCION}: Proyección.
 * {@link #ACTUACION_EN_VIVO}: Actuación en vivo.
 * {@link #VISITA_GUIADA}: Visita guiada.
 * {@link #FORMATIVA}: Actividad formativa.
 * {@link #OTROS}: Otros tipos de actividades.
 *
 * @author Carlos García Santa
 */
public enum TipoActividad {
    /** Conferencia. */
    CONFERENCIA,
    /** Mesa redonda. */ 
    MESA_REDONDA,
    /** Proyección. */ 
    PROYECCION,
    /** Actuación en vivo. */
    ACTUACION_EN_VIVO, 
    /** Visita guiada. */
    VISITA_GUIADA,
    /** Formativa. */ 
    FORMATIVA,
    /** Otros. */ 
    OTROS;
}
