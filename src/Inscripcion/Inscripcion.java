package src.Inscripcion;

import src.Expofy.ClienteRegistrado;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase Inscripcion.
 * Esta clase representa una inscripción de un cliente a un evento o exposición.
 * Mantiene la cantidad de entradas solicitadas por el cliente y la referencia
 * al cliente que realiza la inscripción.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Inscripcion implements Serializable{
    private int nEntradas;
    private ClienteRegistrado cliente;
    private Set<String> codigos = new HashSet<>();

    /**
     * Constructor que instancia una inscripción.
     * 
     * @param nEntradas Número de entradas solicitadas por el cliente.
     * @param cliente   El cliente que realiza la inscripción.
     */
    public Inscripcion(int nEntradas, ClienteRegistrado cliente) {
        if (nEntradas != 1 || nEntradas != 2) {
            nEntradas = 1;
        }
        this.nEntradas = nEntradas;
        this.cliente = cliente;
    }

    /**
     * Obtiene el número de entradas disponibles o asignadas.
     * 
     * @return El número total de entradas.
     */
    public int getnEntradas() {
        return nEntradas;
    }

    /**
     * Establece el número de entradas disponibles o asignadas.
     * Este método permite actualizar la cantidad total de entradas disponibles para
     * la venta o asignación.
     * 
     * @param nEntradas El nuevo número total de entradas disponibles.
     */
    public void setnEntradas(int nEntradas) {
        this.nEntradas = nEntradas;
    }

    /**
     * Obtiene el cliente asociado a las entradas.
     * 
     * @return El cliente que ha adquirido las entradas o al que están asignadas.
     */
    public ClienteRegistrado getCliente() {
        return cliente;
    }

    /**
     * Asigna un cliente a las entradas.
     * Este método permite vincular un conjunto de entradas a un cliente específico,
     * por ejemplo, tras una compra o reserva.
     * 
     * @param cliente El cliente al que se asignan las entradas.
     */
    public void setCliente(ClienteRegistrado cliente) {
        this.cliente = cliente;
    }

    /**
     * Devuelve los códigos asociados a las entradas.
     * Cada entrada puede tener un código único que se utiliza para verificarla o
     * para algún otro propósito específico del evento.
     * 
     * @return Un conjunto de códigos de entrada.
     */
    public Set<String> getCodigos() {
        return codigos;
    }

    /**
     * Añade un código a un sorteo
     * 
     * @param codigo el código a añadir
     */
    public void addCodigo(String codigo) {
        if (codigo.length() != 4) {
            return;
        }
        codigos.add(codigo);
    }

    /**
     * Elimina un código de un sorteo
     * 
     * @param codigo el código a eliminar
     */
    public void removeCodigo(String codigo) {
        codigos.remove(codigo);
    }

}
