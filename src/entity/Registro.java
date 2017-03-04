package entity;

import entity.cliente_final.Pulseira;
import entity.cliente_final.Usuario;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import persistence.dao.DAO;

@Entity
@Table(name = "registro")
public class Registro implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int id;
    private int temperatura;
    private int batimentos;
    private boolean queda;
    private boolean socorro;
    private long horario;
    private double latitude;
    private double longitude;
    private String codigoPulseira;
    private Pulseira pulseira;
    private static DAO<Registro> daoRegistro;

    public Registro() {

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

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getBatimentos() {
        return batimentos;
    }

    public void setBatimentos(int batimentos) {
        this.batimentos = batimentos;
    }

    public boolean isSocorro() {
        return socorro;
    }

    public void setSocorro(boolean socorro) {
        this.socorro = socorro;
    }

    public boolean isQueda() {
        return queda;
    }

    public void setQueda(boolean queda) {
        this.queda = queda;
    }

    public long getHorario() {
        return horario;
    }

    public void setHorario(long horario) {
        this.horario = horario;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Column(name = "codigo_pulseira")
    public String getCodigoPulseira() {
        return codigoPulseira;
    }

    public void setCodigoPulseira(String codigoPulseira) {
        this.codigoPulseira = codigoPulseira;
    }
    
    @ManyToOne
    @JoinColumn(name = "id_pulseira")
    public Pulseira getPulseira() {
        return pulseira;
    }

    public void setPulseira(Pulseira pulseira) {
        this.pulseira = pulseira;
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
     *
     * @param registro
     * @throws Exception
     */
    public static void criar(Registro registro) throws Exception {
        if (daoRegistro == null) {
            daoRegistro = new DAO<>(Registro.class);
        }

        daoRegistro.criar(registro);

    }

}
