package Inscripcion;

import Expofy.ClienteRegistrado;

public class Inscripcion {
    private Integer nEntradas;
    private ClienteRegistrado cliente;
   
    public Inscripcion(Integer nEntradas, ClienteRegistrado cliente) {
        this.nEntradas = nEntradas;
        this.cliente = cliente;
    }

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
