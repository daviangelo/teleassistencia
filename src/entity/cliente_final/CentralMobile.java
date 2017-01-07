package entity.cliente_final;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "central_mobile")
public class CentralMobile extends CentralLocal {
	private double nivelBateria;
	
	public CentralMobile(){}

	public CentralMobile(int numeroEquipamento, Date dataAlocacao,
			boolean operacional, int numeroCentral, double nivelBateria) {
		super(numeroEquipamento, dataAlocacao, operacional, numeroCentral);

		this.nivelBateria = nivelBateria;
	}

	@Column(name = "nivel_bateria")
	public double getNivelBateria() {
		return nivelBateria;
	}

	public void setNivelBateria(double nivelBateria) {
		this.nivelBateria = nivelBateria;
	}
}
