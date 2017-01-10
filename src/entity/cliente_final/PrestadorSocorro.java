package entity.cliente_final;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "prestador_socorro")
public class PrestadorSocorro {

    private int idPrestadorSocorro;
    private String telefone;
    private String nome;

    private Usuario usuario;

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

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    public Usuario getUsuario() {
        return usuario;
    }

    public void setIdPrestadorSocorro(int idPrestadorSocorro) {
        this.idPrestadorSocorro = idPrestadorSocorro;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @Column
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
