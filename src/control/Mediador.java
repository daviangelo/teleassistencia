package control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.GestorBancoDados;
import persistence.dao.DAO;
import entity.Operador;
import entity.Registro;
import entity.cliente_final.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import positioning.GeographicalCoordinate;

/**
 * Singleton que guardará a instância mediando ações do sistema.
 *
 * @author Davi Lessa
 */
public class Mediador {

    private static Mediador instance;

    private int id = 1;

    private List<Registro> registros;
    private List<Usuario> usuariosAtivos;

    private Mediador() {
        registros = new ArrayList<>();
        usuariosAtivos = new ArrayList<>();
//        try {
//           Usuario usuario =  Usuario.obterUsuarioPorID(1);
//           usuario.setUltimaLocalizacao(new GeographicalCoordinate(-22.896988, -43.344143, System.currentTimeMillis()));
//           getUsuariosAtivos().add(usuario);
//           
//        } catch (Exception ex) {
//            Logger.getLogger(Mediador.class.getName()).log(Level.SEVERE, "Erro carregar usuario", ex);
//        }
    }

    /**
     * Obtém a instância salva do mediador, caso não exista ela é criada.
     *
     * @return
     */
    public static Mediador getInstance() {
        if (instance == null) {
            instance = new Mediador();
            try {
                GestorBancoDados.getInstancia().executar(false);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return instance;
    }

    public List<Registro> getRegistros() {
        List<Registro> lista = new ArrayList<>();
        try {
            lista = Registro.obterRegistros();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lista;
    }

    public void adicionarRegistro(Registro registro) {
        /*registro.setId(id);
         registros.add(registro);
         id++;*/

        try {
            Registro.criar(registro);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Obtém os usuários que estão enviando informações.
     * @return 
     */
    public List<Usuario> getUsuariosAtivos() {
        return usuariosAtivos;
    }
    /**
     * Define os usuários que estão enviando informações.
     * @param usuariosAtivos 
     */
    public void setUsuariosAtivos(List<Usuario> usuariosAtivos) {
        this.usuariosAtivos = usuariosAtivos;
    }

    public String verificaLogin(String login, String senha) {
        boolean valido = false;

        try {
            DAO<Operador> daoOperador = new DAO<>(Operador.class);
            valido = daoOperador.verificaLogin(login, senha);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return valido? "": "Usuário ou senha inválidos";
    }
}
