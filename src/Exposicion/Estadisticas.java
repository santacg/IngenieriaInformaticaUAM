package src.Exposicion;

import java.io.Serializable;

/**
 * Clase Estadisticas.
 * Esta clase representa las estadísticas de una exposición.
 * Almacena información relacionada con los tickets vendidos y los ingresos
 * totales generados.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Estadisticas implements Serializable {
   private static Integer ticketsVendidos;
   private static Double ingresosTotales;

   /**
    * Constructor de la clase Estadísticas.
    * 
    * @param ticketsVendidos Número de tickets vendidos.
    * @param ingresosTotales Ingresos totales generados.
    */
   public Estadisticas() {
      ticketsVendidos = 0;
      ingresosTotales = (double) 0;
   }

   /**
    * Obtiene el número de tickets vendidos.
    * 
    * @return El número de tickets vendidos.
    */
   public Integer getTicketsVendidos() {
      return ticketsVendidos;
   }

   /**
    * Obtiene los ingresos totales generados.
    * 
    * @return Los ingresos totales.
    */
   public Double getIngresosTotales() {
      return ingresosTotales;
   }

   /**
    * Incrementa en uno el contador de tickets vendidos.
    */
   public void incrementarTicketsVendidos() {
      ticketsVendidos++;
   }

   /**
    * Aumenta los ingresos totales por la venta de tickets.
    *
    * @param precio El precio del ticket vendido
    */
   public void incrementarIngresosTotales(Double precio) {
      ingresosTotales += precio;
   }

}
