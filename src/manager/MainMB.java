package manager;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "main_mb")
public class MainMB {
	
	private String registros(){
		return "/registro.xhtml";
	}
	
	private String clientesFinais(){
		return "/buscacliente.xhtml";
	}

}
