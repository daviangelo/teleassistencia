package entity.cliente_final;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import persistence.dao.DAOPrestadorSocorro;

@Entity
@Table(name = "prestador_socorro")
public class PrestadorSocorro implements Serializable {

    private int idPrestadorSocorro;
    private String telefone;
    private String nome;
    private String descricao;

    private Set<Usuario> usuarios;
    
    private static DAOPrestadorSocorro daoPrestador;

    public PrestadorSocorro() {
    }

    public PrestadorSocorro(String telefone, String nome) {
        super();
        this.telefone = telefone;
        this.nome = nome;
    }

    @Id
    @SequenceGenerator(name = "prestador_socorro_seq", sequenceName = "prestador_socorro_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prestador_socorro_seq")
    @Column(name = "id_prestador_socorro")
    public int getIdPrestadorSocorro() {
        return idPrestadorSocorro;
    }

    @Column
    public String getTelefone() {
        return telefone;
    }

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "prestadoresSocorro")
    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setIdPrestadorSocorro(int idPrestadorSocorro) {
        this.idPrestadorSocorro = idPrestadorSocorro;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setUsuarios (Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    @Column
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Column
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
    
    @Transient
    private static DAOPrestadorSocorro getDAOPrestador() {
        if (daoPrestador == null){
            daoPrestador = new DAOPrestadorSocorro();
        }
        return daoPrestador;
    }
    
    /**
     * Apaga um prestador de socorro do banco de dados.
     *
     * @param prestadorSocorro
     *
     * @throws Exception
     */
    public static void apagar(PrestadorSocorro prestadorSocorro) throws Exception {

       getDAOPrestador().apagar(prestadorSocorro);
    }

    /**
     * Obtém a lista com todos os prestadores de socorro salvos no banco de dados.
     *
     * @return
     * @throws Exception
     */
    public static List<PrestadorSocorro> obterPrestadores() throws Exception {
        return getDAOPrestador().listarTodos();
    }

    /**
     * Atualiza os dados de um prestador de socorro do banco de dados.
     *
     * @param prestadorSocorro
     *
     * @throws Exception
     */
    public static void atualizar(PrestadorSocorro prestadorSocorro) throws Exception {

        getDAOPrestador().atualizar(prestadorSocorro);
    }
    
    /**
     * Obtém o prestador de socorro salvo no banco de dados correspondente com o
     * idPrestadorSocorro pesquisado.
     *
     * @param idPrestadorSocorro
     * @return
     * @throws Exception
     */
    public static PrestadorSocorro obterPrestadorPorID(int idPrestadorSocorro) throws Exception {

        return getDAOPrestador().listarPorID(idPrestadorSocorro);
    }
    /**
     * Obtém os prestadores de socorro associados ao usuário buscado.
     *
     * @param idUsuario
     * @return
     * @throws Exception
     */
    public static List<PrestadorSocorro> obterDesassociados(Integer idUsuario) throws Exception {
        return getDAOPrestador().obterDesassociados(idUsuario);
    }
    
    /**
     * Obtém a lista com todos os prestadores de serviço salvos no banco de dados
     * correspondentes com a palavra pesquisada.
     *
     * @param palavra Palavara a ser buscada.
     * @return
     * @throws Exception
     */
    public static List<PrestadorSocorro> pesquisar(String palavra) throws Exception {

        return getDAOPrestador().pesquisa(palavra);
    }
    
    /**
     * Salva um novo prestador de socorro no banco de dados.
     *
     * @param prestadorSocorro
     *
     * @throws Exception
     */
    public static void criar(PrestadorSocorro prestadorSocorro) throws Exception {

        getDAOPrestador().criar(prestadorSocorro);
    }
    
    

}
