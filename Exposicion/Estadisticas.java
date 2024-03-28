package Exposicion;

public class Estadisticas {
   private Integer ticketsVendidos;
   private Double ingresosTotales;

   public Estadisticas(Integer ticketsVendidos, Double ingresosTotales) {
      this.ticketsVendidos = ticketsVendidos;
      this.ingresosTotales = ingresosTotales;
   }

   public Integer getTicketsVendidos() {
      return ticketsVendidos;
   }

   public void setTicketsVendidos(Integer ticketsVendidos) {
      this.ticketsVendidos = ticketsVendidos;
   }

   public Double getIngresosTotales() {
      return ingresosTotales;
   }

   public void setIngresosTotales(Double ingresosTotales) {
      this.ingresosTotales = ingresosTotales;
   }

}
