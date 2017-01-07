package persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import persistence.GestorBancoDados;
import util.CastUtil;
import entity.Operador;

public class OperadorDAO extends DAO<Operador>{

	public OperadorDAO() {
		super(Operador.class);
	}
	
	
	public List<Operador> pesquisa(String campo) throws Exception {
		Session session = GestorBancoDados.getInstancia().getSession();
		Criteria criteria = session.createCriteria(Operador.class);
		criteria.add(
				Restrictions.or(Restrictions.eq("nome", campo).ignoreCase(), 
						Restrictions.eq("login",campo).ignoreCase())
		);
		System.out.println(criteria.list().size());
		List<Operador> lista = CastUtil.castList(Operador.class, criteria.list());
		
		return lista;
		
	}
	

}
