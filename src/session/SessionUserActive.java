/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Luciana
 */
public class SessionUserActive {

    private static SessionUserActive instance;
    private List<UserSession> userAtivos;

    public static SessionUserActive createInstance() {

        if (getInstance() == null) {
            setInstance(new SessionUserActive());
        }
        return getInstance();
    }

    /**
     * @return the instance
     */
    public static SessionUserActive getInstance() {
        return instance;
    }

    /**
     * @param aInstance the instance to set
     */
    public static void setInstance(SessionUserActive aInstance) {
        instance = aInstance;
    }

    /**
     * @return the userAtivos
     */
    public List<UserSession> getUserAtivos() {
        return userAtivos;
    }

    /**
     * @param userAtivos the userAtivos to set
     */
    public void setUserAtivos(List<UserSession> userAtivos) {
        this.userAtivos = userAtivos;
    }

    public void addUser(UserSession user) {

        if (this.userAtivos == null) {
            this.setUserAtivos(new ArrayList<UserSession>());
        }
        Boolean achou = false;
        for (Iterator<UserSession> it = userAtivos.iterator(); it.hasNext();) {
            UserSession u = it.next();
            if (u.getIdSessao().equals(user.getIdSessao())) {
                achou = true;
            }

        }
        if (!(achou)) {
            this.getUserAtivos().add(user);
        }

    }

    public void removerUser(UserSession user) {

        if (this.userAtivos != null) {
            for (Iterator<UserSession> it = this.userAtivos.iterator(); it.hasNext();) {
                UserSession u = it.next();
                if (u.getLogin().equals(user.getLogin())) {
                    it.remove();
                }
            }
        }

    }


    public void removerUser(String idSessao) {

        if (this.userAtivos != null) {
            for (Iterator<UserSession> it = this.userAtivos.iterator(); it.hasNext();) {
                UserSession u = it.next();
                if (idSessao.equals(u.getIdSessao())) {
                    it.remove();
                }
            }
        }

    }

    public Boolean getUser(UserSession user) {


        if (this.userAtivos != null) {
            for (Iterator<UserSession> it = this.userAtivos.iterator(); it.hasNext();) {
                UserSession u = it.next();
                if (u.getLogin().equals(user.getLogin())) {
                    return true;
                }
            }
        }
        return false;

    }
}
