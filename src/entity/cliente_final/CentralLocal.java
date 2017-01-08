package entity.cliente_final;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Classe abstrata que representa a central local
 * @author Davi
 *
 */
@Deprecated
@Entity
public abstract class CentralLocal extends Equipamento {
	
	private int numeroCentral;
	
	public CentralLocal(){
		
	}
	
	public CentralLocal(int numeroEquipamento,
			Date dataAlocacao, boolean operacional, int numeroCentral) {
		super(numeroEquipamento, dataAlocacao, operacional);
		
		this.numeroCentral = numeroCentral;
	}

	

	@Column(name = "numero_central")
	public int getNumeroCentral() {
		return numeroCentral;
	}

	public void setNumeroCentral(int numeroCentral) {
		this.numeroCentral = numeroCentral;
	}
	
}
