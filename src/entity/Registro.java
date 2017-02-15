package entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import persistence.dao.DAO;

@Entity
@Table(name = "registro")
public class Registro implements Serializable {

    public enum TipoTemperatura {

        NORMAL,
        MORNO,
        QUENTE;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int id;
    private long horario;
    private TipoTemperatura tipoTemperatura;
    private static DAO<Registro> daoRegistro;

    public Registro() {

    }

    public Registro(int id, long horario, TipoTemperatura tipoTemperatura) {
        super();
        this.id = id;
        this.horario = horario;
        this.tipoTemperatura = tipoTemperatura;
    }

    @Id
    @SequenceGenerator(name = "registro_seq", sequenceName = "registro_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registro_seq")
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getHorario() {
        return horario;
    }

    public void setHorario(long horario) {
        this.horario = horario;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_temperatura")
    public TipoTemperatura getTipoTemperatura() {
        return tipoTemperatura;
    }

    public void setTipoTemperatura(TipoTemperatura tipoTemperatura) {
        this.tipoTemperatura = tipoTemperatura;
    }

    @Transient
    public TipoTemperatura[] getTipoTemperaturas() {
        return TipoTemperatura.values();
    }

    @Transient
    public String getTemperaturaNome() {
        return tipoTemperatura.name();
    }

    /**
     * Obtém a lista com todos os Registros salvos no banco de dados.
     *
     * @return
     * @throws Exception
     */
    public static List<Registro> obterRegistros() throws Exception {
        if (daoRegistro == null) {
            daoRegistro = new DAO<>(Registro.class);
        }
        return daoRegistro.listarTodos();
    }

    /**
     * Salva um novo registro no banco de dados.
     * @param registro
     * @throws Exception 
     */
    public static void criar (Registro registro) throws Exception {
        if (daoRegistro == null) {
            daoRegistro = new DAO<>(Registro.class);
        }

        daoRegistro.criar(registro);

    }

}
