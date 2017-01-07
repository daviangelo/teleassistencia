package servicos;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import persistence.dao.DAO;

import com.google.gson.Gson;

import control.Mediador;
import entity.Registro;

@Path("/registros")
public class WSRegistros {
	
	@Path("/listar")
	@GET
	@Produces("application/json")
	public String listar(){
		try{
			List<Registro> lista = Mediador.getInstance().getRegistros();
			
			return new Gson().toJson(lista);
		}catch(Exception ex){
			ex.printStackTrace();
			return "Error :" + ex.getMessage();
		}
	}
	
	@POST
	@Path("/enviarRegistro")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response enviarRegistroREST(InputStream incomingData) {
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		Gson gson = new Gson();
		Registro r = gson.fromJson(crunchifyBuilder.toString(), Registro.class);
		Mediador.getInstance().adicionarRegistro(r);
		
		
		// return HTTP response 200 in case of success
		return Response.status(200).entity(crunchifyBuilder.toString()).build();
	}
	

	
	
}
