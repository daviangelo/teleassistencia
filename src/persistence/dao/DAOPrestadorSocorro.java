/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.dao;

import entity.cliente_final.PrestadorSocorro;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.xml.validation.Schema;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import persistence.GestorBancoDados;
import util.CastUtil;

/**
 *
 * @author Davi Lessa 15/1/2017
 */
public class DAOPrestadorSocorro extends DAO<PrestadorSocorro> {

    public DAOPrestadorSocorro() {
        super(PrestadorSocorro.class);
    }

    /**
     * Pesquisa para buscar prestadores de socorro que a entrada 'campo'
     * corresponnda a uma das colunas existentes.
     *
     * @param campo
     * @return
     * @throws Exception
     */
    public List<PrestadorSocorro> pesquisa(String campo) throws Exception {
        Session session = GestorBancoDados.getInstancia().getSession();
        Criteria criteria = session.createCriteria(PrestadorSocorro.class);

        criteria.add(Restrictions.or(/*Restrictions.eq("matriculaFinal", 1)
				.ignoreCase(), */Restrictions.eq("descricao", campo)
                .ignoreCase(), Restrictions.eq("nome", campo)
                .ignoreCase(), Restrictions.eq("telefone", campo).ignoreCase()));
        List<PrestadorSocorro> lista = CastUtil.castList(PrestadorSocorro.class,
                criteria.list());

        return lista;

    }

    public List<PrestadorSocorro> obterDesassociados(Integer idUsuario) throws Exception {
        Session session = GestorBancoDados.getInstancia().getSession();
        String sql = "SELECT * FROM " + GestorBancoDados.getInstancia().getSchema() + ".prestador_socorro as P \n"
                + "	WHERE P.id_prestador_socorro NOT IN \n"
                + "		(SELECT P.id_prestador_socorro FROM " + GestorBancoDados.getInstancia().getSchema()
                + ".prestador_socorro as P, " + GestorBancoDados.getInstancia().getSchema()
                + ".usuario_prestador_socorro as UP WHERE P.id_prestador_socorro = UP.id_prestador_socorro "
                + "AND UP.id_usuario = " + idUsuario + ")";

        Query query = session.createSQLQuery(sql).addEntity(PrestadorSocorro.class);
        List<PrestadorSocorro> listaP = query.list();

//        Criteria criteria = session.createCriteria(PrestadorSocorro.class);
//        criteria.createAlias("usuarios", "usuario");
//        Criterion semUsuario = Restrictions.isEmpty("usuarios");
//
//        criteria.add(Restrictions.or(Restrictions.ne("usuario.idUsuario", idUsuario), semUsuario)).createCriteria(schema);
//        System.out.println("prestadores desassociados " + criteria.list().size());
//        List<PrestadorSocorro> lista = CastUtil.castList(PrestadorSocorro.class,
//                criteria.list());
        return listaP;
    }
}
