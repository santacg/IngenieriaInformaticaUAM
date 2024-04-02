package Exposicion;

/**
 * Clase Estadisticas.
 * Esta clase representa las estadísticas de una exposición.
 * Almacena información relacionada con los tickets vendidos y los ingresos
 * totales generados.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Estadisticas {
   private Integer ticketsVendidos;
   private Double ingresosTotales;

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Estadisticas other = (Estadisticas) obj;
      if (ticketsVendidos == null) {
         if (other.ticketsVendidos != null)
            return false;
      } else if (!ticketsVendidos.equals(other.ticketsVendidos))
         return false;
      if (ingresosTotales == null) {
         if (other.ingresosTotales != null)
            return false;
      } else if (!ingresosTotales.equals(other.ingresosTotales))
         return false;
      return true;
   }

   /**
    * Constructor de la clase Estadísticas.
    * 
    * @param ticketsVendidos Número de tickets vendidos.
    * @param ingresosTotales Ingresos totales generados.
    */
   public Estadisticas(Integer ticketsVendidos, Double ingresosTotales) {
      this.ticketsVendidos = ticketsVendidos;
      this.ingresosTotales = ingresosTotales;
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
    * Establece el número de tickets vendidos.
    * 
    * @param ticketsVendidos El nuevo número de tickets vendidos.
    */
   public void setTicketsVendidos(Integer ticketsVendidos) {
      this.ticketsVendidos = ticketsVendidos;
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
    * Establece los ingresos totales generados.
    * 
    * @param ingresosTotales Los nuevos ingresos totales.
    */
   public void setIngresosTotales(Double ingresosTotales) {
      this.ingresosTotales = ingresosTotales;
   }

}
