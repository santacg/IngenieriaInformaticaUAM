package ssii2.controlador;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import java.io.Serializable;
import jakarta.faces.context.*;

import ssii2.voto.*;
import ssii2.interaccion.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.WebServiceException;
import jakarta.ejb.EJB;
import ssii2.servicio.dao.VotoDAORemote;

//
/*
 * Managed Bean de ambito de sesion que recoge los datos de la votacion.
 */

@Named // Permite acceder al bean a traves del EL
@SessionScoped // Hace que el bean persista en la sessión

public class ControladorBean implements Serializable {

  // Referencia obtenida por inyección
  // a un bean de sesión con la información del voto

  @Inject
  private VotoBean voto;

  // Referencia obtenida por inyección
  // a un bean de sesión con la información de la interacción
  // con el modelo de negocio

  @Inject
  private InteraccionBean interaccion;

  @EJB(name = "VotoDAOBean", beanInterface = VotoDAORemote.class)
  private VotoDAORemote dao;

  public ControladorBean() {
  }

  private ssii2.servicio.VotoBean traducirVotoParaServicio(VotoBean voto) {
    ssii2.servicio.CensoBean censo_nuevo = new ssii2.servicio.CensoBean();
    ssii2.servicio.VotoBean voto_nuevo = new ssii2.servicio.VotoBean();
    voto_nuevo.setIdCircunscripcion(voto.getIdCircunscripcion());
    voto_nuevo.setIdMesaElectoral(voto.getIdMesaElectoral());
    voto_nuevo.setIdProcesoElectoral(voto.getIdProcesoElectoral());
    voto_nuevo.setNombreCandidatoVotado(voto.getNombreCandidatoVotado());
    censo_nuevo.setNumeroDNI(voto.getCenso().getNumeroDNI());
    censo_nuevo.setFechaNacimiento(voto.getCenso().getFechaNacimiento());
    censo_nuevo.setNombre(voto.getCenso().getNombre());
    censo_nuevo.setCodigoAutorizacion(voto.getCenso().getCodigoAutorizacion());
    voto_nuevo.setCenso(censo_nuevo);
    return voto_nuevo;
  }

  // Metodo que recibe la acción de enviar el voto e interactua con la lógica de
  // negocio
  // encargada de registrar el voto

  public String enviarVoto() {

    if (this.interaccion.getDebug() == true) {
      this.escribirLog("Solicitado el registro del voto.");
    }

    try {
      /* Instanciamos el objeto que presta la lógica de negocio de la aplicación */

      dao.setDirectConnection(this.interaccion.getConexionDirecta());
      dao.setDebug(this.interaccion.getDebug());
      dao.setPrepared(this.interaccion.getPreparedStatements());

      /*
       * Traduccion de la informacion del voto antes de las llamadas a los metodos de
       * registro y comprobacion
       */
      ssii2.servicio.VotoBean votoServicio = traducirVotoParaServicio(this.voto);

      /* Comprobamos que el ciudadano está en el censo */

      if (dao.compruebaCenso(votoServicio.getCenso()) == false) {

        String error_msg = "¡El ciudadano no se encuentra en el censo!";

        if (this.interaccion.getDebug() == true) {
          this.escribirLog(error_msg);
        }

        this.setMensajeError(error_msg);
        return "error";
      }

      /* Registramos el voto */

      ssii2.servicio.VotoBean votoRegistrado = dao.registraVoto(votoServicio);
      if (votoRegistrado == null) {

        String error_msg = "¡No se ha podido registrar el voto!";

        if (this.interaccion.getDebug() == true) {
          this.escribirLog(error_msg);
        }

        this.setMensajeError(error_msg);
        return "error";

      }
      /* Es necesario cambiar atributos después de llamar a registraVoto */
      this.voto.setIdVoto(votoRegistrado.getIdVoto());
      this.voto.setMarcaTiempo(votoRegistrado.getMarcaTiempo());
      this.voto.setCodigoRespuesta(votoRegistrado.getCodigoRespuesta());
      /* Todo ha ido bien. Vamos a la página de respuesta */

      if (this.interaccion.getDebug() == true) {
        this.escribirLog("¡Voto registrado correctamente!");
      }

      return "respuesta";
    } catch (Exception e) {
      e.printStackTrace();
      return "Error";
    }
  }

  // Metodo que recibe la acción de borrar los votos de un proceso electoral
  // e interactua con la lógica de negocio para llevarlo a cabo

  public String borrarVotos() {
    try {
      /* Instanciamos el objeto que presta la lógica de negocio de la aplicación */

      // Borramos los votos

      int votos_borrados = dao.delVotos(this.voto.getIdProcesoElectoral());

      // Comprobamos el nº de votos borrados

      if (votos_borrados != 0) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("numVotosBorrados",
            String.valueOf(votos_borrados));

        return "borradook";
      }

      String error_msg = "¡No se ha podido borrar ningún voto!";
      this.setMensajeError(error_msg);

      return "error";
    } catch (Exception e) {
      e.printStackTrace();
      return "Error BorrarVotos";
    }
  }

  // Metodo que recibe la acción de consultar los votos de un proceso electoral
  // e interactua con la lógica de negocio para llevarlo a cabo

  public String consultarVotos() {
    try {

      ssii2.servicio.VotoBean[] votosServicio = dao.getVotos(this.voto.getIdProcesoElectoral());

      ArrayList<VotoBean> votosList = new ArrayList<>();
      for (ssii2.servicio.VotoBean votos : votosServicio) {
        VotoBean votoBean = new VotoBean();
        votoBean.setIdVoto(votos.getIdVoto());
        votoBean.setIdCircunscripcion(votos.getIdCircunscripcion());
        votoBean.setIdMesaElectoral(votos.getIdMesaElectoral());
        votoBean.setIdProcesoElectoral(votos.getIdProcesoElectoral());
        votoBean.setNombreCandidatoVotado(votos.getNombreCandidatoVotado());
        votoBean.setMarcaTiempo(votos.getMarcaTiempo());
        votoBean.setCodigoRespuesta(votos.getCodigoRespuesta());
        votosList.add(votoBean);
      }

      VotoBean[] votosArray = votosList.toArray(new VotoBean[votosList.size()]);
      FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("votosObtenidos", votosArray);

      return "listadoVotos";
    } catch (Exception e) {
      e.printStackTrace();
      return "error";
    }
  }

  // Método que escribe en el log del servidor

  private void escribirLog(String log) {
    System.out.println("[LOG INFO]:" + log + "\n");
  }

  // Metodo que fija en contexto un mensaje de error para ser mostrado en una
  // página xhtml

  private void setMensajeError(String mensaje) {
    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("error", mensaje);
  }
}
