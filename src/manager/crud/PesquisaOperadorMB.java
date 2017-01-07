package manager.crud;


import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import control.Mediador;
import persistence.dao.OperadorDAO;
import entity.Operador;

@ManagedBean(name = "pesq_op_mb")
@ViewScoped 
public class PesquisaOperadorMB {

	private OperadorDAO dao = new OperadorDAO();;
	private String campo;
	private Operador operadorSelecionado;

	private List<Operador> lista = new ArrayList<>();;
	{
		Mediador.getInstance();
	}
	public String teste() {
		System.out.println("imprimindos");
		return "/login.xhtml";
	}
	
	public String alterarOperador() throws Exception{
		Operador op = dao.listarPorID(this.operadorSelecionado.getId());
	    if(op != null){
	    	
	    	//Redirecionando
			FacesContext faces = FacesContext.getCurrentInstance();  
			ExternalContext context = faces.getExternalContext();  
			context.redirect("home.xhtml");
		}
	    
		return null;		
	}


	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public void buscarOperador() {
		try {

			lista = dao.pesquisa(campo);
			
			if (lista.isEmpty()){
				FacesContext.getCurrentInstance().addMessage(
	                    null,
	                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Operador(es) não encontrado(s)!",
	                                "A sua busca não retornou nenhum resultado"));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Operador> getLista() {
		return lista;
	}

	public void setLista(List<Operador> lista) {
		this.lista = lista;
	}

	public Operador getOperadorSelecionado() {
		return operadorSelecionado;
	}

	public void setOperadorSelecionado(Operador operadorSelecionado) {
		this.operadorSelecionado = operadorSelecionado;
	}
	
	public String excluirOperador(){
		System.out.println("operador excluído");
		return "/buscaoperador.xhtml";
	}
}
