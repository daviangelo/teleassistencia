/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Luciana
 */
public class UserSession implements Serializable{

    private String login = "";
    private String idSessao = "";
    private String ip = "";
    private String descricao = "";
    private Date dataEntrada;
    private Date dataSaida;

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the idSessao
     */
    public String getIdSessao() {
        return idSessao;
    }

    /**
     * @param idSessao the idSessao to set
     */
    public void setIdSessao(String idSessao) {
        this.idSessao = idSessao;
    }

    /**
     * @return the dataEntrada
     */
    public Date getDataEntrada() {
        return dataEntrada;
    }

    /**
     * @param dataEntrada the dataEntrada to set
     */
    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    /**
     * @return the dataSaida
     */
    public Date getDataSaida() {
        return dataSaida;
    }

    /**
     * @param dataSaida the dataSaida to set
     */
    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getHoraEntrada() {
        String hora = "";
        if (this.dataEntrada != null) {
//            hora = UtilWeb.getHoraByData(this.dataEntrada);
        }
        return hora;
    }

    public String getHoraSaida() {
        String hora = "";
        return hora;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
