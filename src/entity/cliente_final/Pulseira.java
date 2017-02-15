package entity.cliente_final;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import persistence.dao.DAO;
import persistence.dao.DAOPulseira;

/**
 * Classe que representa a pulseira de monitoramento.
 *
 * @author Davi Lessa
 * @since 08/01/2017
 */
@Entity
@Table
public class Pulseira implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idPulseira;
    private String codigoIdentificador;
    private boolean liberadaUso;
    private Usuario usuario;
    private static DAOPulseira daoPulseira;

    /**
     * Construtor obrigatório para o Hibernate.
     */
    public Pulseira() {
    }

    @Id
    @SequenceGenerator(name = "pulseira_seq", sequenceName = "pulseira_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pulseira_seq")
    @Column(name = "id_pulseira")
    public int getIdPulseira() {
        return idPulseira;
    }

    public void setIdPulseira(int idPulseira) {
        this.idPulseira = idPulseira;
    }

    @Column(name = "codigo_identificador")
    public String getCodigoIdentificador() {
        return codigoIdentificador;
    }

    public void setCodigoIdentificador(String codigoIdentificador) {
        this.codigoIdentificador = codigoIdentificador;
    }

    @Column(name = "liberada_uso")
    public boolean isLiberadaUso() {
        return liberadaUso;
    }

    public void setLiberadaUso(boolean liberadaUso) {
        this.liberadaUso = liberadaUso;
    }

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Transient
    public String getStatus() {
        return isLiberadaUso() ? "Sim" : "Não";
    }

    private static DAOPulseira getDAOPulseira() {
        if (daoPulseira == null) {
            daoPulseira = new DAOPulseira();
        }
        return daoPulseira;
    }

    /**
     * Apaga uma pulseira do banco de dados.
     *
     * @param pulseira
     *
     * @throws Exception
     */
    public static void apagar(Pulseira pulseira) throws Exception {

        getDAOPulseira().apagar(pulseira);
    }

    /**
     * Atualiza os dados de uma pulseira do banco de dados.
     *
     * @param pulseira
     *
     * @throws Exception
     */
    public static void atualizar(Pulseira pulseira) throws Exception {

        getDAOPulseira().atualizar(pulseira);
    }
    
     /**
     * Obtém a lista com todos os pulseiras salvos no banco de dados.
     *
     * @return
     * @throws Exception
     */
    public static List<Pulseira> obterPulseiras() throws Exception {

        return getDAOPulseira().listarTodos();
    }
    
     /**
     * Obtém a lista com todos as pulseiras dessasociadas a usuário.
     *
     * @return
     * @throws Exception
     */
    public static List<Pulseira> obterDesassociadas() throws Exception {

        return getDAOPulseira().obterDesassociadas();
    }
    
    /**
     * Obtém a lista com todos os pulseiras salvos no banco de dados
     * correspondentes com a palavra pesquisada.
     *
     * @param palavra Palavara a ser buscada.
     * @return
     * @throws Exception
     */
    public static List<Pulseira> pesquisar(String palavra) throws Exception {

        return getDAOPulseira().pesquisa(palavra);
    }
    
    /**
     * Salva um novo pulseira no banco de dados.
     *
     * @param pulseira
     *
     * @throws Exception
     */
    public static void criar(Pulseira pulseira) throws Exception {

        getDAOPulseira().criar(pulseira);
    }

}
