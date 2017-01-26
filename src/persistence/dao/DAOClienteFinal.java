package persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import persistence.GestorBancoDados;
import util.CastUtil;
import entity.cliente_final.ClienteFinal;

public class DAOClienteFinal extends DAO<ClienteFinal> {

	public DAOClienteFinal() {
		super(ClienteFinal.class);
	}

	/**
	 * Pesquisa para buscar clientes finais que a entrada 'campo' corresponnda a
	 * uma das colunas existentes.
	 * 
	 * @param campo
	 * @return
	 * @throws Exception
	 */
	public List<ClienteFinal> pesquisa(String campo) throws Exception {
		Session session = GestorBancoDados.getInstancia().getSession();
		Criteria criteria = session.createCriteria(ClienteFinal.class);
		
		criteria.add(Restrictions.or(/*Restrictions.eq("matriculaFinal", 1)
				.ignoreCase(), */Restrictions.eq("inscricaoFederal", campo)
				.ignoreCase(), Restrictions.eq("razaoSocial", campo)
				.ignoreCase(), Restrictions.eq("telefone", campo).ignoreCase()));
		System.out.println(criteria.list().size());
		List<ClienteFinal> lista = CastUtil.castList(ClienteFinal.class,
				criteria.list());

		return lista;

	}

}
