package manager;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import persistence.dao.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import control.Mediador;
import entity.Registro;
import entity.cliente_final.ClienteFinal;

@ManagedBean(name = "registro_mb")
@RequestScoped
public class RegistroMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Registro> lista;
	private Registro registro;

	public RegistroMB() {
		registro = new Registro();
	}

	public void gravarRegistro() {
		if (registro != null) {
			registro.setHorario(System.currentTimeMillis());
			Mediador.getInstance().adicionarRegistro(registro);
		}
		registro = new Registro();

		DAO<ClienteFinal> daoCliente = new DAO<>(ClienteFinal.class);

		try {
			ClienteFinal cf = daoCliente.listarTodos().get(0);

			System.out.println("\nClienteFinal:" + cf.toString());
			System.out.println("\nUsuarios:" + cf.getUsuarios().size());
			System.out.println("\nPrestadores:" + cf.getUsuarios().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void consultar() {

		try {

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpcliente = new HttpGet(
					"http://localhost:8080/webservice/regitros/listar");
			httpcliente.setHeader("Content-type", "application/json");
			HttpResponse resp = client.execute(httpcliente);
			byte[] bytes = new byte[1024];
			ByteArrayOutputStream byaos = new ByteArrayOutputStream();
			int lidos;
			while ((lidos = resp.getEntity().getContent().read(bytes)) > 0) {
				byaos.write(bytes, 0, lidos);
			}
			String dados = new String(byaos.toString());
			lista = new Gson().fromJson(dados, new TypeToken<List<Registro>>() {
			}.getType());
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Usuário não encontrado!", "Erro no Login!"));
			System.out.println(lista);
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Usuário não encontrado!", "Erro no Login!"));
		}

	}

	public List<Registro> getLista() {

		lista = Mediador.getInstance().getRegistros();

		return lista;
	}

	public void setLista(List<Registro> lista) {
		this.lista = lista;
	}

	public Registro getRegistro() {
		return registro;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
