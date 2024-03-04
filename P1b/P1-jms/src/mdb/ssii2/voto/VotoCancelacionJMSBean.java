/**
 * Pr&aacute;ctricas de Sistemas Inform&aacute;ticos II
 * VisaCancelacionJMSBean.java
 */

package ssii2.voto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.ejb.EJBException;
import jakarta.ejb.MessageDriven;
import jakarta.ejb.MessageDrivenContext;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.jms.MessageListener;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import jakarta.jms.JMSException;
import jakarta.annotation.Resource;
import java.util.logging.Logger;

/**
 * @author jaime y daniel
 */
@MessageDriven(mappedName = "jms/VotosQueue")
public class VotoCancelacionJMSBean extends DBTester implements MessageListener {
  static final Logger logger = Logger.getLogger("VotoCancelacionJMSBean");
  @Resource
  private MessageDrivenContext mdc;

  private static final String UPDATE_CANCELA_QRY = null;
   // TODO : Definir UPDATE sobre la tabla votos para poner
   // codRespuesta a 999 dado un código de idVoto


  public VotoCancelacionJMSBean() {
  }

  // TODO : Método onMessage de ejemplo
  // Modificarlo para ejecutar el UPDATE definido más arriba,
  // asignando el idVoto a lo recibido por el mensaje
  // Para ello conecte a la BD, prepareStatement() y ejecute correctamente
  // la actualización
  public void onMessage(Message inMessage) {
      TextMessage msg = null;

      try {
          if (inMessage instanceof TextMessage) {
              msg = (TextMessage) inMessage;
              logger.info("MESSAGE BEAN: Message received: " + msg.getText());
          } else {
              logger.warning(
                      "Message of wrong type: "
                      + inMessage.getClass().getName());
          }
      } catch (JMSException e) {
          e.printStackTrace();
          mdc.setRollbackOnly();
      } catch (Throwable te) {
          te.printStackTrace();
      }
  }


}
