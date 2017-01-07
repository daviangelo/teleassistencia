package entity.cliente_final;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {
	private int idUsuario;
	private String nome;
	private String cpf;
	private String rg;

	private ClienteFinal clienteFinal;
	private Set<PrestadorSocorro> prestadoresSocorro = new HashSet<>();

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
	public Set<PrestadorSocorro> getPrestadoresSocorro() {
		return prestadoresSocorro;
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

}
