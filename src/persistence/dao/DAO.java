package persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import persistence.GestorBancoDados;
import util.CastUtil;

/**
 * Para utilização do DAO com Hibernate, basta fazer a instanciação dessa classe
 * passando o objeto a ser persistido para essa classe.
 *
 * @param <E>
 */
public class DAO<E> {

    private final Class<E> classe;
    protected final String schema;

    /**
     * Construtor obrigatório da classe.
     *
     * @param classe de persistência
     */
    public DAO(Class<E> classe) {
        this.classe = classe;
        schema = GestorBancoDados.getInstancia().getSchema();
    }

    public Class<E> getClasse() {
        return this.classe;
    }

    /**
     * Utilizado para persistir a criação de novos objetos no banco de dados.
     *
     * @param persistir - objeto que será persistido
     * @throws Exception
     */
    public void criar(E persistir) throws Exception {
        Session session = GestorBancoDados.getInstancia().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(persistir);
        transaction.commit();
        session.flush();
        session.close();
    }

    /**
     * Atualizar objeto do banco de dados.
     *
     * @param persistir - objeto que será atualizado
     * @throws Exception
     */
    public void atualizar(E persistir) throws Exception {
        Session session = GestorBancoDados.getInstancia().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(persistir);
        transaction.commit();
        session.flush();
        session.close();
    }

    /**
     * Apagar objeto do banco de dados.
     *
     * @param persistir - objeto que será apagado
     * @throws Exception
     */
    public void apagar(E persistir) throws Exception {
        Session session = GestorBancoDados.getInstancia().getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(persistir);
        transaction.commit();
        session.close();
    }

    /**
     * Retorna uma lista com objetos criados através de todas as tuplas da
     * tabela.
     *
     * @return todas os objetos da tabela
     * @throws Exception
     */
    public List<E> listarTodos() throws Exception {
        Session session = GestorBancoDados.getInstancia().getSession();
        List<E> lista;
        Criteria criteria = session.createCriteria(classe);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//                criteria.addOrder(Order.asc("razaoSocial"));
        lista = CastUtil.castList(classe, criteria.list());
        session.close();
        return lista;
    }

    /**
     * Retorna o objeto de acordo com o seu id.
     *
     * @param id - identificador único da tupla
     * @return objeto
     * @throws Exception
     */
    public E listarPorID(Integer id) throws Exception {
        Session session = GestorBancoDados.getInstancia().getSession();
        E objeto = getClasse().cast(session.get(classe, id));
        session.close();
        return objeto;
    }

    public String toString() {
        return "DAO: " + this.classe.getCanonicalName();
    }

    /**
     * Verifica se o login e a senha existem e batem.
     *
     * @param nome - login
     * @param senha - senha
     * @return boolean - se o login e a senha estão corretos.
     * @throws Exception
     */
    public boolean verificaLogin(String login, String senha) throws Exception {
        Session session = GestorBancoDados.getInstancia().getSession();
        Criteria criteria = session.createCriteria(classe);
        boolean resultado = false;

        if ((classe != null)
                && (criteria.add(Restrictions.eq("login", login)).uniqueResult() != null)
                && (criteria.add(Restrictions.eq("senha", senha))
                .uniqueResult() != null)) {
            resultado = true;
        }
        session.close();
        return resultado;
    }

    /**
     * Obtém a quantidade total de registros no banco de dados.
     *
     * @return long - quantidade de registros
     * @throws Exception
     */
    public long quantidadeRegistros() throws Exception {
        Session session = GestorBancoDados.getInstancia().getSession();
        Long quantRegistros = (Long) session.createCriteria(classe)
                .setProjection(Projections.rowCount()).uniqueResult();
        session.close();
        return quantRegistros;
    }

    /**
     * Pesquisa atributos pertencentes a uma grupo de entidades entrelaçados por
     * seus identificadores.
     *
     * @param campo - nome do atributo da classe
     * @param classe
     * @param obj
     * @return
     */
    public <T> List<T> pesquisa(String campo, Class<T> classe, Object obj)
            throws Exception {
        Session session = GestorBancoDados.getInstancia().getSession();
        Criteria criteria = session.createCriteria(classe);
        criteria.add(Restrictions.eq(campo, obj));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<T> lista = CastUtil.castList(classe, criteria.list());
        session.close();
        return lista;
    }

}
