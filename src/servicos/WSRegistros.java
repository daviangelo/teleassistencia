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

import com.google.gson.Gson;

import control.Mediador;
import entity.Registro;
import entity.cliente_final.Pulseira;
import entity.cliente_final.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import positioning.GeographicalCoordinate;

@Path("/registros")
public class WSRegistros {
    
    @Path("/listar")
    @GET
    @Produces("application/json")
    public String listar() {
        try {
            List<Registro> lista = Mediador.getInstance().getRegistros();
            
            return new Gson().toJson(lista);
        } catch (Exception ex) {
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
            if (Mediador.getInstance().getUsuariosAtivos().isEmpty()) {
                Usuario usuario = Usuario.obterUsuarioPorID(1);
                Mediador.getInstance().getUsuariosAtivos().add(usuario);
            }
            
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                crunchifyBuilder.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
        }
        Gson gson = new Gson();
        Registro registro = gson.fromJson(crunchifyBuilder.toString(), Registro.class);
        
        for (Usuario usuario : Mediador.getInstance().getUsuariosAtivos()) {
            for (Pulseira pulseira : usuario.getPulseiras()) {
                if (registro.getCodigoPulseira().equals(pulseira.getCodigoIdentificador())) {
                    if (pulseira.isLiberadaUso()) {
                        pulseira.getRegistros().add(registro);
                        registro.setPulseira(pulseira);
                        AtualizarPulseira atualizar = new AtualizarPulseira(registro);
                        atualizar.start();
                        usuario.setUltimoRegistro(registro);
                        
                    }
                }
            }
        }

        // return HTTP response 200 in case of success
        return Response.status(200).entity(crunchifyBuilder.toString()).build();
    }
    
    private class AtualizarPulseira extends Thread implements Runnable {
        
        private final Registro registro;
        
        public AtualizarPulseira(Registro registro) {
            this.registro = registro;
        }
        
        @Override
        public void run() {
            try {
                Registro.criar(registro);
            } catch (Exception ex) {
                Logger.getLogger(WSRegistros.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
