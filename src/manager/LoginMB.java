package manager;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import control.Mediador;
import entity.Operador;

@ManagedBean(name = "login_mb")
@ViewScoped 
public class LoginMB {	
	
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
	
	public String submeter() {
		boolean valido = Mediador.getInstance().verificaLogin(operador.getLogin(), operador.getSenha());
		
		if (!valido){
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não encontrado!",
                                "Erro no Login!"));
         return null;
		} else{
			return "/home.xhtml";
		}
		
	}
	
	
	

}
