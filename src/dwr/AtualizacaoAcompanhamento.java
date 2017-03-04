package dwr;

import control.Mediador;
import entity.Registro;
import entity.cliente_final.Pulseira;
import entity.cliente_final.Usuario;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.ComparatorUtils;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import positioning.BoundingBox;
import positioning.GeographicalCoordinate;

/**
 * Classe responsável por criar novo acompanhamaneto ou atualizar um já
 * existente.
 *
 * @author Luciana, Davi
 */
public class AtualizacaoAcompanhamento {

    /**
     * Identificacao dos dados da Feature
     */
    public enum CAMPO {

        ID,
        NOME,
        CODIGO,
        LONG,
        LAT,
        SRC,
        HORARIO,
        TEXT_LAT_LONG,

    }

    public AtualizacaoAcompanhamento() {
    }

    /**
     * Metodo usado em cada tempo (5 seg) para atualizar os acompanhamentos no
     * Mapa
     *
     * @param filtro
     * @param bBox array de duas longitudes e duas latitudes que formam duas
     * coordenadas geográficas que define a janela de visualização atual;
     * @return List<Map> Lista de Objetos (Features em Map)
     */
    public List<Map> desenharObjetos(List<String> filtro, List<String> bBox) {

        List<Map> retorno = new ArrayList<>();

        List<CAMPO> campos = new ArrayList<>();

        campos.add(CAMPO.LAT);
        campos.add(CAMPO.LONG);
        campos.add(CAMPO.NOME);
        campos.add(CAMPO.ID);
        campos.add(CAMPO.SRC);
        campos.add(CAMPO.HORARIO);
        campos.add(CAMPO.CODIGO);

        GeographicalCoordinate bBoxCoordenada1 = new GeographicalCoordinate(Double.valueOf(bBox.get(1)), Double.valueOf(bBox.get(0)));
        GeographicalCoordinate bBoxCoordenada2 = new GeographicalCoordinate(Double.valueOf(bBox.get(3)), Double.valueOf(bBox.get(2)));

        BoundingBox janelaExibicao = new BoundingBox(bBoxCoordenada1, bBoxCoordenada2);

        WebContext wctx = WebContextFactory.get();
        HttpServletRequest request = wctx.getHttpServletRequest();
        String pathImage = request.getContextPath();

        try {

            for (Usuario usuario : Mediador.getInstance().getUsuariosAtivos()) {
                Map item = new HashMap();
//                if (!janelaExibicao.intercepta(usuario.getUltimaLocalizacao())) {
//                    continue;
//                }
                if (campos.contains(CAMPO.ID)) {
                    item.put(CAMPO.ID.toString(), String.valueOf(usuario.getIdUsuario()));
                }

                if (campos.contains(CAMPO.NOME)) {
                    item.put(CAMPO.NOME.toString(), usuario.getNome());
                }

                if (campos.contains(CAMPO.LAT)) {
                    item.put(CAMPO.LAT.toString(), String.valueOf(usuario.getUltimoRegistro().getLatitude()));
                }

                if (campos.contains(CAMPO.LONG)) {
                    item.put(CAMPO.LONG.toString(), String.valueOf(usuario.getUltimoRegistro().getLongitude()));
                }

                if (campos.contains(CAMPO.SRC)) {
                    for (Pulseira pulseira : usuario.getPulseiras()) {
                        if (pulseira.isLiberadaUso()) {
                            if (usuario.getUltimoRegistro().isQueda() || usuario.getUltimoRegistro().isSocorro()){
                                item.put(CAMPO.SRC.toString(), pathImage += "/images/usuarioAlerta.png");
                            } else{
                                item.put(CAMPO.SRC.toString(), pathImage += "/images/usuarioNormal.png");
                            }
                        }
                        
                    }
                    

                }

                if (campos.contains(CAMPO.CODIGO)) {
                    for (Pulseira pulseira : usuario.getPulseiras()) {
                        if (pulseira.isLiberadaUso()) {

                            item.put(CAMPO.CODIGO.toString(), pulseira.getCodigoIdentificador());
                        }
                    }
                }

                if (!item.isEmpty()) {
                    retorno.add(item);
                }

            }

        } catch (Exception ex) {
             Logger.getLogger(AtualizacaoAcompanhamento.class.getName()).log(Level.SEVERE, "Erro ao atualizar acompanhamentos", ex);
        }

        return retorno;
    }

    /**
     * Metodo para busca de informacoes de uma feature especifica
     *
     * @param id
     * @param resumido true para mostrar info no mouse over
     * @return
     */
    public String getInfoFeatures(String id, boolean resumido) {
        try {

            WebContext wctx = WebContextFactory.get();
            HttpServletRequest request = wctx.getHttpServletRequest();

            String url = "/dicaobjeto.xhtml";

            for (Usuario usuario : Mediador.getInstance().getUsuariosAtivos()) {

                if (usuario.getIdUsuario() == Integer.valueOf(id)) {

                    usuario.getPulseiras().stream().filter((pulseira) -> (pulseira.isLiberadaUso())).forEach((pulseira) -> {
                        request.setAttribute("codigoPulseira", pulseira.getCodigoIdentificador());

                        

                        request.setAttribute("temperatura", usuario.getUltimoRegistro().getTemperatura());
                        request.setAttribute("batimentos", usuario.getUltimoRegistro().getBatimentos());
                        request.setAttribute("queda", usuario.getUltimoRegistro().isQueda() ? "Sim" : "Não");
                        request.setAttribute("socorro", usuario.getUltimoRegistro().isSocorro() ? "Sim" : "Não");

                    });

                    request.setAttribute("nomeUsuario", usuario.getNome());

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
                    request.setAttribute("horarioAtualizacao", sdf.format(usuario.getUltimoRegistro().getHorario()));

                    request.setAttribute("coordenadaGeografica", getLatLongToString(usuario) );

                }
            }

            return wctx.forwardToString(url);
        } catch (ServletException | IOException ex) {
            ex.printStackTrace();
            return "ERRO";
        }
    }

    public String getLatLongToString(Usuario usuario) {
       GeographicalCoordinate gc = new GeographicalCoordinate(usuario.getUltimoRegistro().getLatitude(), usuario.getUltimoRegistro().getLongitude());
        return gc.toString();
    }

    /**
     * Metodo pagar buscar latitude e Longitude do objeto para Centralização
     *
     * @return List<Map> Lista com Longitude e Latitude
     */
    public Map buscaLatitudeLongitudePrincipal() {

        Map retorno = new HashMap<>();

        if (Mediador.getInstance().getUsuariosAtivos().size() == 1) {
            Usuario usuario = (Usuario) Mediador.getInstance().getUsuariosAtivos().get(0);

            retorno.put("LONGITUDE", String.valueOf(usuario.getUltimoRegistro().getLongitude()));
            retorno.put("LATITUDE", String.valueOf(usuario.getUltimoRegistro().getLatitude()));
        }

        return retorno;
    }
}
