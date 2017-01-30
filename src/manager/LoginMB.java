package manager;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import control.Mediador;
import entity.Operador;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "login_mb")
@ViewScoped
public class LoginMB implements Serializable {

    private Operador operador;

    public LoginMB() {

        operador = new Operador();

    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public Operador getOperador() {
        return operador;
    }

    public String submeter() throws IOException {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();

        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        String validacao = Mediador.getInstance().verificaLogin(operador.getLogin(), operador.getSenha());
        boolean autorizado = ((validacao != null) && (validacao.isEmpty())) ? true : false;;
        if (autorizado) {
            session.setAttribute("usuarioLogado", operador.getLogin());
            fc.getExternalContext().redirect("/servidorcentral/home.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, validacao, "Erro no Login!"));
            context.addCallbackParam("autorizado", autorizado);
        }
        return null;
    }

}
