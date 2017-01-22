/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.dao;

import entity.cliente_final.Pulseira;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import persistence.GestorBancoDados;
import util.CastUtil;

/**
 * @author Davi Lessa
 * 15/1/2017
 */
public class DAOPulseira extends DAO<Pulseira>{

    public DAOPulseira() {
        super(Pulseira.class);
    }
    
     /**
	 * Pesquisa para buscar prestadores de socorro que a entrada 'campo' corresponnda a
	 * uma das colunas existentes.
	 * 
	 * @param campo
	 * @return
	 * @throws Exception
	 */
	public List<Pulseira> pesquisa(String campo) throws Exception {
		Session session = GestorBancoDados.getInstancia().getSession();
		Criteria criteria = session.createCriteria(Pulseira.class);
		
		criteria.add(Restrictions.or(/*Restrictions.eq("matriculaFinal", 1)
				.ignoreCase(), */ Restrictions.eq("codigoIdentificador", campo).ignoreCase()));
		System.out.println(criteria.list().size());
		List<Pulseira> lista = CastUtil.castList(Pulseira.class,
				criteria.list());

		return lista;

	}
    
	public List<Pulseira> obterDesassociadas() throws Exception {
		Session session = GestorBancoDados.getInstancia().getSession();
		Criteria criteria = session.createCriteria(Pulseira.class);
		
		criteria.add( Restrictions.isNull("usuario"));
		System.out.println(criteria.list().size());
		List<Pulseira> lista = CastUtil.castList(Pulseira.class,
				criteria.list());

		return lista;

	}
    
}
