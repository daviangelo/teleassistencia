package entity.cliente_final;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "equipamento")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Equipamento {
	
	private int idEquipamento;
	private int numeroEquipamento;
	private Date dataAlocacao;
	private boolean operacional;
	
	private ClienteFinal clienteFinal;
	
	

	public Equipamento() {
	}

	public Equipamento(int numeroEquipamento,
			Date dataAlocacao, boolean operacional) {
		this.numeroEquipamento = numeroEquipamento;
		this.dataAlocacao = dataAlocacao;
		this.operacional = operacional;
	}

	@Id
	@SequenceGenerator(name = "equipamento_seq", sequenceName = "equipamento_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipamento_seq")
	@Column(name="id_equipamento")
	public int getIdEquipamento() {
		return idEquipamento;
	}
	
	@Column(name="numero_equipamento")
	public int getNumeroEquipamento() {
		return numeroEquipamento;
	}
	
	
	@Column(name="data_alocacao")
	@Temporal(TemporalType.DATE)
	public Date getDataAlocacao() {
		return dataAlocacao;
	}

	@Column
	public boolean isOperacional() {
		return operacional;
	}

	@ManyToOne
	@JoinColumn(name = "id_cliente_final")
	public ClienteFinal getClienteFinal() {
		return clienteFinal;
	}

	public void setIdEquipamento(int idEquipamento) {
		this.idEquipamento = idEquipamento;
	}

	public void setNumeroEquipamento(int numeroEquipamento) {
		this.numeroEquipamento = numeroEquipamento;
	}

	public void setDataAlocacao(Date dataAlocacao) {
		this.dataAlocacao = dataAlocacao;
	}

	public void setOperacional(boolean operacional) {
		this.operacional = operacional;
	}

	public void setClienteFinal(ClienteFinal clienteFinal) {
		this.clienteFinal = clienteFinal;
	}

	
	

}
