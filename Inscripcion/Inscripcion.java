package Inscripcion;

import Expofy.ClienteRegistrado;

/**
 * Clase Inscripcion.
 * Esta clase representa una inscripción de un cliente a un evento o exposición.
 * Mantiene la cantidad de entradas solicitadas por el cliente y la referencia
 * al cliente que realiza la inscripción.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Inscripcion {
    private Integer nEntradas;
    private ClienteRegistrado cliente;

    /**
     * Constructor que instancia una inscripción.
     * 
     * @param nEntradas Número de entradas solicitadas por el cliente.
     * @param cliente   El cliente que realiza la inscripción.
     */
    public Inscripcion(Integer nEntradas, ClienteRegistrado cliente) {
        this.nEntradas = nEntradas;
        this.cliente = cliente;
    }

    /**
     * Getters y setters que facilitan la gestión de una inscripción.
     */
    public Integer getnEntradas() {
        return nEntradas;
    }

    public void setnEntradas(Integer nEntradas) {
        this.nEntradas = nEntradas;
    }

    public ClienteRegistrado getCliente() {
        return cliente;
    }

    public void setCliente(ClienteRegistrado cliente) {
        this.cliente = cliente;
    }

}
