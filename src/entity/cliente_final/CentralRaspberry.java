package entity.cliente_final;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
@Deprecated
@Entity
@Table(name = "central_raspberry")
public class CentralRaspberry extends CentralLocal {
	
	private TipoRede tipoRede;
	
	enum TipoRede{
		CABEADA,
		WIFI,
		MOVEL;
		
	}
	
	public CentralRaspberry(){}

	@Enumerated(EnumType.STRING)
    @Column(name = "tipo_rede")
	public TipoRede getTipoRede() {
		return tipoRede;
	}

	public void setTipoRede(TipoRede tipoRede) {
		this.tipoRede = tipoRede;
	}
	
	

}
