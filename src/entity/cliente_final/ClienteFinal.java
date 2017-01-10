package entity.cliente_final;

import java.io.Serializable;
import java.util.HashSet;
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

/**
 * Presenta o cliente final.
 *
 * @author Davi
 *
 */
@Entity
@Table(name = "cliente_final")
public class ClienteFinal implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int idClienteFinal;
    private String inscricaoFederal;
    private String inscricaoEstadual;
    private String razaoSocial;
    private String telefone;

    private Set<Usuario> usuarios = new HashSet<>();
    private Set<Equipamento> equipamentos = new HashSet<>();

    /**
     * Construtor obrigatório para o Hibernate.
     */
    public ClienteFinal() {

    }

    public ClienteFinal(String inscricaoFederal, String inscricaoEstadual,
            String razaoSocial, String telefone, Set<Usuario> usuarios,
            Set<Equipamento> equipamentos) {
        super();
        this.inscricaoFederal = inscricaoFederal;
        this.inscricaoEstadual = inscricaoEstadual;
        this.razaoSocial = razaoSocial;
        this.telefone = telefone;
        this.usuarios = usuarios;
        this.equipamentos = equipamentos;
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

    @Column(name = "inscricao_estadual")
    public String getInscricaoEstadual() {
        return inscricaoEstadual;
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

    @OneToMany(mappedBy = "clienteFinal", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    public Set<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setIdClienteFinal(int idClienteFinal) {
        this.idClienteFinal = idClienteFinal;
    }

    public void setInscricaoFederal(String inscricaoFederal) {
        this.inscricaoFederal = inscricaoFederal;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
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

    public void setEquipamentos(Set<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }

}
