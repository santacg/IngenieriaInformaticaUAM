package gui.modelo.obra;

/**
 * Clase Estado.
 * Representa los posibles estados en los que puede encontrarse una obra de arte
 * dentro de una colección.Estos estados permiten a los administradores de la
 * colección y otros usuarios del sistema gestionar y planificar adecuadamente
 * las actividades relacionadas con cada obra.
 * 
 * Los estados disponibles son:
 * {@link #EXPUESTA}: La obra está siendo exhibida actualmente.
 * {@link #ALMACENADA}: La obra se encuentra almacenada.
 * {@link #PRESTADA}: La obra ha sido prestada a otra institución.
 * {@link #RESTAURACION}: La obra está en proceso de restauración.
 * {@link #RETIRADA}: La obra ha sido retirada de la colección, ya sea de
 * forma temporal o permanente.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public enum Estado {
    /** Obra expuesta en sala. */
    EXPUESTA,
    /** Obra almacenada en centro. */
    ALMACENADA,
    /** Obra prestada a otro centro. */
    PRESTADA,
    /** Obra en resturación. */
    RESTAURACION,
    /** Obra retirada. */
    RETIRADA,
}
