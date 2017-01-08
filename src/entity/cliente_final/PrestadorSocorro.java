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
	private String inscricaoEstadual;
	private String inscricaoFederal;
	private String telefone;
	
	private Usuario usuario;
	
	public PrestadorSocorro(){}
	
	

	public PrestadorSocorro(String inscricaoEstadual, String inscricaoFederal, String telefone) {
		super();
		this.inscricaoEstadual = inscricaoEstadual;
		this.inscricaoFederal = inscricaoFederal;
		this.telefone = telefone;
	
	}



	@Id
	@SequenceGenerator(name = "prestador_socorro_seq", sequenceName = "prestador_socorro_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prestador_socorro_seq")
	@Column(name="id_prestador_socorro")
	public int getIdPrestadorSocorro() {
		return idPrestadorSocorro;
	}

	@Column
	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	@Column
	public String getInscricaoFederal() {
		return inscricaoFederal;
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

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public void setInscricaoFederal(String inscricaoFederal) {
		this.inscricaoFederal = inscricaoFederal;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
