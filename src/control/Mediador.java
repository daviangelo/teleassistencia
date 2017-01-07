package control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import persistence.GestorBancoDados;
import persistence.dao.DAO;
import entity.Operador;
import entity.Registro;
import entity.cliente_final.CentralMobile;
import entity.cliente_final.ClienteFinal;
import entity.cliente_final.Equipamento;
import entity.cliente_final.PrestadorSocorro;
import entity.cliente_final.Usuario;

public class Mediador {

	private DAO<Registro> daoRegistro;
		
		private static Mediador instance;
		
		private int id = 1;
		
		private List<Registro> registros;
		
		private Mediador() {
			registros = new ArrayList<>();
			daoRegistro = new DAO<>(Registro.class);
//			criarNovoClienteFinal();
		}
		
		
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

		private void criarNovoClienteFinal(){
			
			PrestadorSocorro ps = new PrestadorSocorro(1, "11111", "222222", "3333333");
			
			Set<PrestadorSocorro> setps = new HashSet<>();
			
			setps.add(ps);
			
			Usuario u = new Usuario("usuario1", "12354", "21111", setps);
			
			
			Set<Usuario> setu = new HashSet<>();
			
			setu.add(u);
			
			Date data = new Date(System.currentTimeMillis());
			
			CentralMobile cm = new CentralMobile(12, data, true, 12, 0.9);
			
			Set<Equipamento> setcm = new HashSet<>();
//			
			setcm.add(cm);
			
			ClienteFinal cf = new ClienteFinal(1, "111111", "2222222",
					"Empresa1", "31512613", setu, setcm);
			
			ps.setUsuario(u);
			
			u.setClienteFinal(cf);
			
			cm.setClienteFinal(cf);
			
	
			
			DAO<ClienteFinal> daoCliente = new DAO<>(ClienteFinal.class);
			
			try {
				daoCliente.criar(cf);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Erro ao criar cliente final");
			}
			
		}

		public List<Registro> getRegistros() {
			List<Registro> lista = new ArrayList<>();
			try {
				lista = daoRegistro.listarTodos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lista;
		}
		
		public void adicionarRegistro(Registro registro){
			/*registro.setId(id);
			registros.add(registro);
			id++;*/
			
			try {
				daoRegistro.criar(registro);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public boolean verificaLogin(String login, String senha){
			boolean valido = false;
			
			try {
				DAO<Operador> daoOperador = new DAO<>(Operador.class);
				valido = daoOperador.verificaLogin(login, senha);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return valido;
		}
}
