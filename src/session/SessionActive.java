/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;


import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Brener
 */
public class SessionActive implements HttpSessionListener {

    public static int sessionActive = 0;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        sessionActive++;
        se.getSession().setAttribute("usuarioAutenticado", "");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        try {
            sessionActive--;
            Boolean sessaoLogada = (Boolean) se.getSession().getAttribute("AUTENTICADO");
            //retirar da lista de usuariosAtivos e gravar no xml de usuarios
            SessionUserActive.createInstance().removerUser(se.getSession().getId());
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }

    public static int getSessionsActive() {
        return sessionActive;
    }

    public static void addUsuario(String usuario) {

    }

}
