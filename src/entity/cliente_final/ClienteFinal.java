package entity.cliente_final;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import persistence.dao.DAOClienteFinal;

/**
 * Representa o cliente final.
 *
 * @author Davi Lessa
 *
 */
@Entity
@Table(name = "cliente_final")
public class ClienteFinal implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idClienteFinal;
    private String inscricaoFederal;
    private String razaoSocial;
    private String telefone;

    private Set<Usuario> usuarios = new HashSet<>();

    private static DAOClienteFinal daoCliente;

    /**
     * Construtor obrigatório para o Hibernate.
     */
    public ClienteFinal() {

    }

    public ClienteFinal(String inscricaoFederal,
            String razaoSocial, String telefone, Set<Usuario> usuarios) {
        super();
        this.razaoSocial = razaoSocial;
        this.telefone = telefone;
        this.usuarios = usuarios;
    }

    @Id
    @SequenceGenerator(name = "cliente_final_seq", sequenceName = "cliente_final_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_final_seq")
    @Column(name = "id_cliente_final")
    public int getIdClienteFinal() {
        return idClienteFinal;
    }

    @Column(name = "inscricao_federal")
    public String getInscricaoFederal() {
        return inscricaoFederal;
    }

    @Column(name = "razao_social")
    public String getRazaoSocial() {
        return razaoSocial;
    }

    @Column
    public String getTelefone() {
        return telefone;
    }

    @OneToMany(mappedBy = "clienteFinal", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setIdClienteFinal(int idClienteFinal) {
        this.idClienteFinal = idClienteFinal;
    }

    public void setInscricaoFederal(String inscricaoFederal) {
        this.inscricaoFederal = inscricaoFederal;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Transient
    private static DAOClienteFinal getDAOCliente() {
        if (daoCliente == null) {
            daoCliente = new DAOClienteFinal();
        }
        return daoCliente;
    }

    /**
     * Obtém a lista com todos os clientes finais salvos no banco de dados.
     *
     * @return
     * @throws Exception
     */
    public static List<ClienteFinal> obterClientes() throws Exception {

        return getDAOCliente().listarTodos();
    }

    /**
     * Obtém a lista com todos os clientes finais salvos no banco de dados
     * correspondentes com a palavra pesquisada.
     *
     * @param palavra Palavara a ser buscada.
     * @return
     * @throws Exception
     */
    public static List<ClienteFinal> pesquisar(String palavra) throws Exception {
        return getDAOCliente().pesquisa(palavra);
    }

    /**
     * Salva um novo cliente final no banco de dados.
     *
     * @param clienteFinal
     *
     * @throws Exception
     */
    public static void criar(ClienteFinal clienteFinal) throws Exception {

        getDAOCliente().criar(clienteFinal);
    }

    /**
     * Obtém o cliente final salvos no banco de dados correspondentes com o
     * idClienteFinal pesquisado.
     *
     * @param idClienteFinal
     * @return
     * @throws Exception
     */
    public static ClienteFinal obterClientePorID(int idClienteFinal) throws Exception {

        return getDAOCliente().listarPorID(idClienteFinal);
    }

    /**
     * Apaga um cliente do banco de dados.
     *
     * @param clienteFinal
     *
     * @throws Exception
     */
    public static void apagar(ClienteFinal clienteFinal) throws Exception {
        getDAOCliente().apagar(clienteFinal);
    }

    /**
     * Atualiza os dados de um cliente final do banco de dados.
     *
     * @param clienteFinal
     *
     * @throws Exception
     */
    public static void atualizar(ClienteFinal clienteFinal) throws Exception {
        getDAOCliente().atualizar(clienteFinal);
    }

}
