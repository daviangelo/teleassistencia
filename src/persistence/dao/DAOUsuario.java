/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.dao;

import entity.cliente_final.Usuario;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import persistence.GestorBancoDados;
import util.CastUtil;

/**
 *
 * @author Camila Siqueira
 */
public class DAOUsuario extends DAO<Usuario> {

    public DAOUsuario() {
        super(Usuario.class);
    }

    /**
     * Pesquisa para buscar usuário que a entrada 'campo' corresponda a uma das
     * colunas existentes.
     *
     * @param campo
     * @return
     * @throws Exception
     */
    public List<Usuario> pesquisa(String campo) throws Exception {
        Session session = GestorBancoDados.getInstancia().getSession();
        Criteria criteria = session.createCriteria(Usuario.class);

        criteria.add(Restrictions.or(Restrictions.eq("nome", campo)
                .ignoreCase(), Restrictions.eq("cpf", campo)
                .ignoreCase(), Restrictions.eq("rg", campo).ignoreCase()));
        List<Usuario> lista = CastUtil.castList(Usuario.class, criteria.list());

        return lista;
    }
}
