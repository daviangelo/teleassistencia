package entity.cliente_final;

import entity.Registro;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import persistence.dao.DAO;
import positioning.GeographicalCoordinate;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private int idUsuario;
    private String nome;
    private String cpf;
    private String rg;

    private ClienteFinal clienteFinal;
    private Set<PrestadorSocorro> prestadoresSocorro = new HashSet<>();
    private Set<Pulseira> pulseiras = new HashSet<>();
    private static DAO<Usuario> daoUsuario;
    private Registro ultimoRegistro; 

    public Usuario() {
    }

    public Usuario(String nome, String cpf, String rg, Set<PrestadorSocorro> prestadoresSocorro) {
        super();
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.prestadoresSocorro = prestadoresSocorro;
    }

    @Id
    @SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @Column(name = "id_usuario")
    public int getIdUsuario() {
        return idUsuario;
    }

    @Column
    public String getNome() {
        return nome;
    }

    @Column
    public String getCpf() {
        return cpf;
    }

    @Column
    public String getRg() {
        return rg;
    }

    @ManyToOne
    @JoinColumn(name = "id_cliente_final")
    public ClienteFinal getClienteFinal() {
        return clienteFinal;
    }

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    public Set<Pulseira> getPulseiras() {
        return pulseiras;
    }
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_prestador_socorro", joinColumns = 
     {@JoinColumn(name = "id_usuario")},inverseJoinColumns =
     {@JoinColumn(name = "id_prestador_socorro")})
    public Set<PrestadorSocorro> getPrestadoresSocorro() {
        return prestadoresSocorro;
    }

    public void setPulseiras(Set<Pulseira> pulseiras) {
        this.pulseiras = pulseiras;
    }

    public void setPrestadoresSocorro(Set<PrestadorSocorro> prestadoresSocorro) {
        this.prestadoresSocorro = prestadoresSocorro;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public void setClienteFinal(ClienteFinal clienteFinal) {
        this.clienteFinal = clienteFinal;
    }

    @Transient
    public Registro getUltimoRegistro() {
        return ultimoRegistro;
    }

    public void setUltimoRegistro(Registro ultimoRegistro) {
        this.ultimoRegistro = ultimoRegistro;
    }
    

    @Transient
    public static DAO<Usuario> getDAOUsuario() {
        if (daoUsuario == null) {
            daoUsuario = new DAO<>(Usuario.class);
        }
        return daoUsuario;
    }

    /**
     * Apaga um usuário do banco de dados.
     *
     * @param usuario
     *
     * @throws Exception
     */
    public static void apagar(Usuario usuario) throws Exception {

        getDAOUsuario().apagar(usuario);
    }

    /**
     * Obtém a lista com todos os usuarios salvos no banco de dados.
     *
     * @return
     * @throws Exception
     */
    public static List<Usuario> obterUsuarios() throws Exception {

        return getDAOUsuario().listarTodos();
    }

    /**
     * Atualiza os dados de um usuario do banco de dados.
     *
     * @param usuario
     *
     * @throws Exception
     */
    public static void atualizar(Usuario usuario) throws Exception {

        getDAOUsuario().atualizar(usuario);
    }

    /**
     * Obtém o usuario salvo no banco de dados correspondente com o idUsuario
     * pesquisado.
     *
     * @param idUsuario
     * @return
     * @throws Exception
     */
    public static Usuario obterUsuarioPorID(int idUsuario) throws Exception {

        return getDAOUsuario().listarPorID(idUsuario);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.idUsuario;
        hash = 23 * hash + Objects.hashCode(this.nome);
        hash = 23 * hash + Objects.hashCode(this.cpf);
        hash = 23 * hash + Objects.hashCode(this.rg);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        if (!Objects.equals(this.rg, other.rg)) {
            return false;
        }
        return true;
    }
    
    

}
