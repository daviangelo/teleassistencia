package dwr;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

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
        HAB,
        ID,
        NOME,
        NA,
        VELOC,
        RUMO,
        LONG,
        LAT,
        SRC,
        TIPO,
        LATLONG,
        URL,
        MARCACAO,
        INFO,
        IDENT,
        PREC,
        OPAC,
        TS,
        PERDIDO;

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

//        campos.add(CAMPO.LAT);
//        campos.add(CAMPO.LONG);
//        campos.add(CAMPO.NOME);
//        campos.add(CAMPO.PREC);
//        campos.add(CAMPO.ID);
//        campos.add(CAMPO.RUMO);
//        campos.add(CAMPO.OPAC);
//        campos.add(CAMPO.SRC);
//
//        CoordenadaGeografica bBoxCoordenada1 = new CoordenadaGeografica(Double.valueOf(bBox.get(1)), Double.valueOf(bBox.get(0)));
//        CoordenadaGeografica bBoxCoordenada2 = new CoordenadaGeografica(Double.valueOf(bBox.get(3)), Double.valueOf(bBox.get(2)));
//
//        JanelaGeografica janelaExibicao = new JanelaGeografica(bBoxCoordenada1, bBoxCoordenada2);
//
//        if (!Mediador.isSistemaIniciado()) {
//            return retorno;
//        }
//
//        try {
//
//            for (ObjetoDOMINIO obj : Mediador.getInstancia().getGestor().getGestorObjetos().getObjetos()) {
//
//                if (obj instanceof Acompanhamento) {
//                    Acompanhamento aco = (Acompanhamento) obj;
//
//                    if (!janelaExibicao.intercepta(aco.getPosicao().getCoordenadaGeografica())) {
//                        continue;
//                    }
//                    boolean processaItem = true;
//                    //Verifica se foi escolhido todos os acompanhamentos
//                    if (!(filtro.isEmpty())) {
//                        if ((aco.isPerdido()) && (filtro.contains("PERDIDO"))) {
//                            processaItem = false;
//                        } else if (filtro.contains("FUNDIDO")) {
//                            if ((aco.getAcompanhamentosFundidos() != null) && (!(aco.getAcompanhamentosFundidos().isEmpty()))) {
//                                processaItem = false;
//                            } else {
//                                processaItem = verificaFiltro(aco, filtro);
//                            }
//                        } else {
//                            processaItem = verificaFiltro(aco, filtro);
//                        }
//                    } else//Verifica Fundido
//                     if ((aco.getAcompanhamentosFundidos() != null) && (!(aco.getAcompanhamentosFundidos().isEmpty()))) {
//                            processaItem = false;
//                        }
//                    if (processaItem) {
//                        Map item = montaItem(aco, campos);
//                        if (item != null) {
//                            retorno.add(item);
//                        }
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            Log.gravarLogExcecao("Erro ao buscar Acompanhamentos", this, ex);
//        }
        return retorno;
    }

//    /**
//     * Monta um mapa com as informações de um acompanhamento
//     *
//     * @param aco
//     * @return
//     */
//    private Map montaItem(Acompanhamento aco, List<CAMPO> campos) {
//
//        WebContext wctx = WebContextFactory.get();
//        HttpServletRequest request = wctx.getHttpServletRequest();
//        String pathImage = request.getContextPath();
//        String identificador = (String) request.getSession().getAttribute("usuarioLogado");
//
//        Map item = new HashMap();
//
//        if (campos.contains(CAMPO.HAB)) {
//            item.put(CAMPO.HAB.toString(), true);
//        }
//
//        if (campos.contains(CAMPO.ID)) {
//            item.put(CAMPO.ID.toString(), String.valueOf(aco.getId()));
//        }
//
//        if (campos.contains(CAMPO.NA)) {
//            item.put(CAMPO.NA.toString(), aco.getNa());
//        }
//
//        if (campos.contains(CAMPO.NOME)) {
//            item.put(CAMPO.NOME.toString(), (aco.getNome() == null) ? aco.getNa() : aco.getNome());
//        }
//
//        if (campos.contains(CAMPO.TS)) {
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
//            item.put(CAMPO.TS.toString(), acertaData(sdf.format(aco.getTimeStamp())));
//        }
//
//        if (campos.contains(CAMPO.PERDIDO)) {
//            item.put(CAMPO.PERDIDO.toString(), aco.isPerdido() ? " - PERDIDO" : "");
//        }
//
//        if (campos.contains(CAMPO.LAT)) {
//            item.put(CAMPO.LAT.toString(), String.valueOf(aco.getPosicao().getCoordenadaGeografica().getLatitude()));
//        }
//
//        if (campos.contains(CAMPO.LONG)) {
//            item.put(CAMPO.LONG.toString(), String.valueOf(aco.getPosicao().getCoordenadaGeografica().getLongitude()));
//        }
//
//        if (campos.contains(CAMPO.OPAC)) {
//            item.put(CAMPO.OPAC.toString(), aco.isPerdido() ? "0.40" : "0.95");
//        }
//
//        DecimalFormat df = new DecimalFormat("0.0");
//        String dx = df.format(aco.getVelocidadeFundo());
//
//        if (campos.contains(CAMPO.VELOC)) {
//            item.put(CAMPO.VELOC.toString(), String.valueOf(dx));
//        }
//
//        if (campos.contains(CAMPO.RUMO)) {
//            // Rumo em radianos
//            double rotacao = aco.getVelocidadeFundo() > 1.0 ? Math.toRadians(aco.getRumoFundo()) : 0.0;
//            item.put(CAMPO.RUMO.toString(), String.valueOf(rotacao));
//        }
//
//        df = new DecimalFormat("#00");
//        dx = df.format(aco.getRumoFundo());
//
//        if (campos.contains(CAMPO.MARCACAO)) {
//            item.put(CAMPO.MARCACAO.toString(), String.valueOf(dx));
//        }
//
//        if (campos.contains(CAMPO.SRC)) {
//            //Tratamento de Imagens conforme Tipo do Acompanhamento e Hostilidade
//            if ((aco instanceof AcompanhamentoExternoCelular)
//                    && (((AcompanhamentoExternoCelular) aco).getIdentificador().equals(identificador))) {
//                pathImage += "/imagens/objetos/veiculo.png";
//            } else {
//                pathImage += (aco instanceof AcompanhamentoExternoCelular) ? "/imagens/objetos/celular/" : "/imagens/objetos/";
//                switch (aco.getHostilidade()) {
//                    case AMIGO:
//                        pathImage += aco.getVelocidadeFundo() <= 1.0 ? "amigoParado.png" : "amigo.png";
//                        break;
//                    case DESCONHECIDO:
//                        pathImage += aco.getVelocidadeFundo() <= 1.0 ? "desconhecidoParado.png" : "desconhecido.png";
//                        break;
//                    case INIMIGO:
//                        pathImage += aco.getVelocidadeFundo() <= 1.0 ? "inimigoParado.png" : "inimigo.png";
//                        break;
//                    case NEUTRO:
//                        pathImage += aco.getVelocidadeFundo() <= 1.0 ? "neutroParado.png" : "neutro.png";
//                        break;
//                }
//            }
//
//            item.put(CAMPO.SRC.toString(), pathImage);
//        }
//
//        if (campos.contains(CAMPO.TIPO)) {
//            item.put(CAMPO.TIPO.toString(), aco.getTipoInstanciaAcompanhamento());
//        }
//
//        if (campos.contains(CAMPO.INFO)) {
//            item.put(CAMPO.INFO.toString(), this.getInformacoes(aco));
//        }
//
//        if (campos.contains(CAMPO.LATLONG)) {
//            item.put(CAMPO.LATLONG.toString(), this.getLatLongToString(aco));
//        }
//
//        if (campos.contains(CAMPO.URL)) {
//            item.put(CAMPO.URL.toString(), this.getImagem(aco));
//        }
//
//        //item.put(CAMPO.PREC, String.valueOf(ConversorUnidades.aco.getPosicao().getPrecisao()));
//        if (campos.contains(CAMPO.PREC)) {
//            item.put(CAMPO.PREC.toString(), String.valueOf(ConversorUnidades.milhasParaMetros(aco.getPosicao().getPrecisao())));
//        }
//
//        if (campos.contains(CAMPO.IDENT)) {
//            if (aco instanceof AcompanhamentoExternoCelular) {
//                item.put(CAMPO.IDENT.toString(), ((AcompanhamentoExternoCelular) aco).getIdentificador());
//            } else {
//                item.put(CAMPO.IDENT.toString(), "");
//            }
//        }
//        return item;
//    }
//
//    /**
//     * Metodo para tratar o filtro escolhido pelo usuario
//     *
//     * @param filtro Lista de String com valores dos CheCkBox : AIS / CELULAR /
//     * NAVIO / SISTRAM / MANUAL / FUNDIDO AIS /
//     * @return
//     */
//    private boolean verificaFiltro(Acompanhamento aco, List<String> filtro) {
//        boolean processa = false;
//        if (aco instanceof AcompanhamentoAIS) {
//            processa = filtro.contains("AIS");
//        } else if (aco instanceof AcompanhamentoExternoCelular) {
//            processa = filtro.contains("CELULAR");
//        } else if (aco instanceof AcompanhamentoExternoNavio) {
//            processa = filtro.contains("NAVIO");
//        } else if (aco instanceof AcompanhamentoExternoSistram) {
//            processa = filtro.contains("SISTRAM");
//        } else if (aco instanceof AcompanhamentoFundido) {
//            processa = filtro.contains("FUNDIDO");
//        } else if (aco instanceof AcompanhamentoManual) {
//            processa = filtro.contains("MANUAL");
//        }
//        return processa;
//    }
//
//    /**
//     * Metodo para busca de informacoes de uma feature especifica
//     *
//     * @param id
//     * @param resumido true para mostrar info no mouse over
//     * @return
//     */
//    public String getInfoFeatures(String id, boolean resumido) {
//        try {
//
//            WebContext wctx = WebContextFactory.get();
//            HttpServletRequest request = wctx.getHttpServletRequest();
//
//            String url = "/dicaObjeto.xhtml";
//
//            Acompanhamento acompanhamento = (Acompanhamento) Mediador.getInstancia().getGestor().getGestorObjetos().buscar(Integer.valueOf(id));
//
//            if (acompanhamento == null) {
//                return "Acompanhamento não encontrado";
//            }
//
//            List<CAMPO> camposResumido = new ArrayList<>();
//
//            camposResumido.add(CAMPO.NOME);
//            camposResumido.add(CAMPO.NA);
//            camposResumido.add(CAMPO.TIPO);
//            camposResumido.add(CAMPO.MARCACAO);
//            camposResumido.add(CAMPO.VELOC);
//            camposResumido.add(CAMPO.TS);
//
//            List<CAMPO> camposCompleto = new ArrayList<>();
//
//            camposCompleto.addAll(camposResumido);
//            camposCompleto.add(CAMPO.URL);
//            camposCompleto.add(CAMPO.PERDIDO);
//            camposCompleto.add(CAMPO.INFO);
//            camposCompleto.add(CAMPO.LATLONG);
//
//            if (resumido) {
//                Map item = montaItem(acompanhamento, camposResumido);
//
//                if (item == null) {
//                    return "Acompanhamento não encontrado";
//                }
//                url = "/labelObjeto.xhtml";
//                if (item.get(CAMPO.NOME.toString()).equals("")) {
//                    request.setAttribute("acoNome", item.get(CAMPO.NA.toString()));
//                } else {
//                    request.setAttribute("acoNome", item.get(CAMPO.NOME.toString()));
//                }
//                request.setAttribute("acoTipoAcompanhamento", item.get(CAMPO.TIPO.toString()));
//                request.setAttribute("acoRumo", item.get(CAMPO.MARCACAO.toString()));
//                request.setAttribute("acoVeloc", item.get(CAMPO.VELOC.toString()));
//                request.setAttribute("acoTimestamp", item.get(CAMPO.TS.toString()));
//            } else {
//
//                Map item = montaItem(acompanhamento, camposCompleto);
//
//                if (item == null) {
//                    return "Acompanhamento não encontrado";
//                }
//                request.setAttribute("acoImagem", item.get(CAMPO.URL.toString()));
//                request.setAttribute("acoNA", item.get(CAMPO.NA.toString()));
//                request.setAttribute("acoNome", item.get(CAMPO.NOME.toString()));
//                request.setAttribute("acoTipoAcompanhamento", item.get(CAMPO.TIPO.toString()));
//                request.setAttribute("acoLatLong", item.get(CAMPO.LATLONG.toString()));
//                request.setAttribute("acoRumo", item.get(CAMPO.MARCACAO.toString()));
//                request.setAttribute("acoVeloc", item.get(CAMPO.VELOC.toString()));
//                request.setAttribute("acoPerdido", item.get(CAMPO.PERDIDO.toString()));
//                request.setAttribute("acoTimestamp", item.get(CAMPO.TS.toString()));
//
//                request.setAttribute("acoInformacoes", item.get(CAMPO.INFO.toString()));
//            }
//
//            return wctx.forwardToString(url);
//        } catch (ServletException | IOException ex) {
//            Log.gravarLogExcecao("Erro ao Atualizar Dica do Objeto", this, ex);
//            return "ERRO";
//        }
//    }
//
//    private String acertaData(String data) {
//
//        String p[] = data.split(" ");
//        String hora = p[1];
//
//        String dataCerta = "";
//
//        if (!(new SimpleDateFormat("dd/MM/yyyy").format(Mediador.getRelogio().getHorario()).equals(p[0]))) {
//            String d[] = p[0].split("/");
//            dataCerta = " (" + d[0] + "/" + d[1] + "/" + d[2] + ")";
//        }
//
//        return hora + dataCerta;
//    }
//
//    public String getDono(Acompanhamento aco) {
//        return aco.getDono().getHostName();
//    }
//
//    public String getLatLongToString(Acompanhamento aco) {
//        return aco.getPosicao().getCoordenadaGeografica().toString();
//    }
//
//    public String getVelocidade(Acompanhamento aco) {
//        return aco.getVelocidade().getVelocidadeFundo().toString();
//    }
//
//    public String getRumo(Acompanhamento aco) {
//        return aco.getRumo().getRumoFundo().toString();
//    }
//
//    public String getAmbiente(Acompanhamento aco) {
//        String ambiente;
//        switch (aco.getAmbiente()) {
//            case AEREO:
//                ambiente = "Aéreo";
//                break;
//            case SUBMARINO:
//                ambiente = "Submarino";
//                break;
//            case SUPERFICIE:
//                ambiente = "Superfície";
//                break;
//            default:
//                ambiente = "Desconhecido";
//        }
//        return ambiente;
//    }
//
//    public String getHostilidade(Acompanhamento aco) {
//        String hostilidade;
//
//        switch (aco.getHostilidade()) {
//            case AMIGO:
//                hostilidade = "Amigo";
//                break;
//            case INIMIGO:
//                hostilidade = "Inimigo";
//                break;
//            case NEUTRO:
//                hostilidade = "Neutro";
//                break;
//            default:
//                hostilidade = "Desconhecido";
//        }
//
//        return hostilidade;
//    }
//
//    public String getImagem(Acompanhamento aco) {
//
//        WebContext wctx = WebContextFactory.get();
//        HttpServletRequest request = wctx.getHttpServletRequest();
//        String ip = request.getServerName() + ":" + String.valueOf(request.getLocalPort());
//
//        String semImagem = "/hidraweb/imagens/semFoto.png";
//
//        int mmsi = 0;
//
//        if (aco instanceof AcompanhamentoAIS) {
//            mmsi = ((AcompanhamentoAIS) aco).getMmsi();
//        }
//        if (aco instanceof AcompanhamentoExternoSistram) {
//            mmsi = ((AcompanhamentoExternoSistram) aco).getMmsi();
//        }
//        if (aco instanceof AcompanhamentoExternoNavio) {
//            mmsi = ((AcompanhamentoExternoNavio) aco).getMmsi();
//        }
//
//        if (aco instanceof AcompanhamentoFundido) {
//            
//            AcompanhamentoFundido acoFun = (AcompanhamentoFundido) aco;
//
//            for (Acompanhamento acoOrigem : acoFun.getAcompanhamentosOrigem().values()) {
//
//                int mmsiOrigem = 0;
//
//                if (acoOrigem instanceof AcompanhamentoAIS) {
//                    mmsiOrigem = ((AcompanhamentoAIS) acoOrigem).getMmsi();
//                }
//                if (acoOrigem instanceof AcompanhamentoExternoSistram) {
//                    mmsiOrigem = ((AcompanhamentoExternoSistram) acoOrigem).getMmsi();
//                }
//                if (acoOrigem instanceof AcompanhamentoExternoNavio) {
//                    mmsiOrigem = ((AcompanhamentoExternoNavio) acoOrigem).getMmsi();
//                }
//
//                if (mmsiOrigem != 0) {
//                    mmsi = mmsiOrigem;
//                    break;
//                }
//            }
//        }
//
//        if (mmsi != 0) {
//
//            try {
//
//                String path = ipqm.gsd.cisne.web.util.UtilAIS.getDiretorioImagem(mmsi);
//
//                File file;
//                if (path != null) {
//                    file = new File(path);
//                } else {
//                    return semImagem;
//                }
//
//                if (!file.exists()) {
//                    return semImagem;
//                }
//
//                return "http://" + ip + "/imagens/navios/" + path.replaceAll(Diretorios.getDiretorioImagens(), "");
//
//            } catch (Exception ex) {
//                Log.gravarLogExcecao("Erro ao capturar imagem do navio " + mmsi, this, ex);
//            }
//        }
//
//        return semImagem;
//    }
//
//    /**
//     * Processa as informacoes conforme o Tipo de Acompanhamento
//     *
//     * @param aco
//     * @return ArrayList<String> Lista com informações
//     */
//    public ArrayList<String> getInformacoes(Acompanhamento aco) {
//        ArrayList<String> retorno = new ArrayList();
//
//        retorno.add("Sobre a fonte:");
//
//        if (aco instanceof AcompanhamentoAIS) {
//            retorno.addAll(getInformacoesAIS(aco));
//        } else if (aco instanceof AcompanhamentoRadar) {
//            retorno.addAll(getInformacoesRadar(aco));
//        } else if (aco instanceof AcompanhamentoFundido) {
//            retorno.addAll(getInformacoesFundido(aco));
//        } else if (aco instanceof AcompanhamentoLink) {
//            retorno.addAll(getInformacoesLink(aco));
//        } else if (aco instanceof AcompanhamentoManual) {
//            retorno.addAll(getInformacoesManual(aco));
//        } else if (aco instanceof AcompanhamentoExternoSistram) {
//            retorno.addAll(getInformacoesExternoSistram(aco));
//        } else if (aco instanceof AcompanhamentoExternoNavio) {
//            retorno.addAll(getInformacoesExternoNavio(aco));
//        } else if (aco instanceof AcompanhamentoExternoCelular) {
//            retorno.addAll(getInformacoesExternoCelular(aco));
//        }
//
//        return retorno;
//    }
//
//    private ArrayList getInformacoesAIS(Acompanhamento aco) {
//        ArrayList informacoes = new ArrayList();
//
//        if (String.valueOf(((AcompanhamentoAIS) aco).getMmsi()) != null) {
//            informacoes.add("MMSI:" + String.valueOf(((AcompanhamentoAIS) aco).getMmsi()));
//        }
//        if (((AcompanhamentoAIS) aco).getTipoNavioDescricao() != null) {
//            informacoes.add("Tipo:" + ((AcompanhamentoAIS) aco).getTipoNavioDescricao());
//        }
//        if (((AcompanhamentoAIS) aco).getNavStatusDescricao() != null) {
//            informacoes.add("Status:" + ((AcompanhamentoAIS) aco).getNavStatusDescricao());
//        }
//        if (((AcompanhamentoAIS) aco).getEta() != null) {
//            informacoes.add("ETA:" + ((AcompanhamentoAIS) aco).getEta());
//        }
//        if (((AcompanhamentoAIS) aco).getCallSign() != null) {
//            informacoes.add("CallSign:" + ((AcompanhamentoAIS) aco).getCallSign());
//        }
//        if (((AcompanhamentoAIS) aco).getDestination() != null) {
//            informacoes.add("Destino:" + ((AcompanhamentoAIS) aco).getDestination());
//        }
//        if (String.valueOf(((AcompanhamentoAIS) aco).getWidth()) != null) {
//            informacoes.add("Comprimento:" + String.valueOf(((AcompanhamentoAIS) aco).getWidth()));
//        }
//        if (((AcompanhamentoAIS) aco).getPais() != null) {
//            informacoes.add("Pais:" + ((AcompanhamentoAIS) aco).getPais());
//        }
//
//        return informacoes;
//    }
//
//    private ArrayList getInformacoesExternoSistram(Acompanhamento aco) {
//        ArrayList informacoes = new ArrayList();
//        AcompanhamentoExternoSistram acomp = (AcompanhamentoExternoSistram) aco;
//
//        if (acomp.getMmsi() != 0) {
//            informacoes.add("MMSI: " + UtilAIS.acertaMMSI(acomp.getMmsi()));
//        }
//        if (acomp.getImo() != null) {
//            informacoes.add("IMO: " + acomp.getImo());
//        }
//        if (acomp.getIrin() != null) {
//            informacoes.add("IRIN: " + acomp.getIrin());
//        }
//        if (acomp.getTipo() != null) {
//            informacoes.add("Tipo: " + acomp.getTipo());
//        }
//        if (acomp.getPortoPartida() != null) {
//            informacoes.add("Porto de partida: " + acomp.getPortoPartida());
//        }
//        if (acomp.getPortoChegada() != null) {
//            informacoes.add("Porto de chegada: " + acomp.getPortoChegada());
//        }
//        if (acomp.getDataChegada() != null) {
//            informacoes.add("ETA: " + acomp.getDataChegada());
//        }
//        if (acomp.getEstacao() != null) {
//            informacoes.add("Estação: " + acomp.getEstacao());
//        }
//
//        if (acomp.getPais() != null) {
//            informacoes.add("Pais: " + acomp.getPais());
//        }
//
//        return informacoes;
//    }
//
//    private ArrayList getInformacoesExternoNavio(Acompanhamento aco) {
//        ArrayList informacoes = new ArrayList();
//
//        AcompanhamentoExternoNavio acomp = (AcompanhamentoExternoNavio) aco;
//
//        if (acomp.getDescricao() != null) {
//            informacoes.add("Descrição: " + acomp.getDescricao());
//        }
//
//        if (acomp.getMmsi() != 0) {
//            informacoes.add("MMSI: " + UtilAIS.acertaMMSI(acomp.getMmsi()));
//        }
//
//        if (acomp.getTipo() != null) {
//            informacoes.add("Tipo: " + acomp.getTipo());
//        }
//        if (acomp.getImo() != 0) {
//            informacoes.add("IMO: " + String.valueOf(acomp.getImo()));
//        }
//        if (acomp.getPais() != null) {
//            informacoes.add("Pais: " + acomp.getPais());
//        }
//        if (acomp.getEta() != null) {
//            informacoes.add("ETA: " + acomp.getEta());
//        }
//        if (acomp.getDestino() != null) {
//            informacoes.add("Destino: " + acomp.getDestino());
//        }
//
//        return informacoes;
//    }
//
//    private ArrayList getInformacoesExternoCelular(Acompanhamento aco) {
//        ArrayList informacoes = new ArrayList();
//        AcompanhamentoExternoCelular acomp = (AcompanhamentoExternoCelular) aco;
//
//        if (acomp.getDescricao() != null) {
//            informacoes.add("Descrição: " + acomp.getDescricao());
//        }
//        if (acomp.getPosicao() != null) {
//            DecimalFormat df = new DecimalFormat("0.000");
//            informacoes.add("Precisão: " + df.format(ConversorUnidades.milhasParaJardas(acomp.getPosicao().getPrecisao())) + " Jardas");
//        }
//
//        return informacoes;
//    }
//
//    private ArrayList getInformacoesRadar(Acompanhamento aco) {
//        ArrayList informacoes = new ArrayList();
//
//        return informacoes;
//    }
//
//    private ArrayList getInformacoesFundido(Acompanhamento aco) {
//        ArrayList informacoes = new ArrayList();
//
//        AcompanhamentoFundido acomp = (AcompanhamentoFundido) aco;
//
//        Map<Integer, Acompanhamento> listaOrigens = acomp.getAcompanhamentosOrigem();
//
//        informacoes.add("Associações: " + listaOrigens.size() + " acompanhamentos");
//        informacoes.add(" ");
//
//        for (Acompanhamento acompanhamento : listaOrigens.values()) {
//
//            informacoes.add(acompanhamento.getTipoAcompanhamento() + ":");
//
//            if (acompanhamento instanceof AcompanhamentoAIS) {
//                informacoes.addAll(getInformacoesAIS(acompanhamento));
//            } else if (acompanhamento instanceof AcompanhamentoRadar) {
//                informacoes.addAll(getInformacoesRadar(acompanhamento));
//            } else if (acompanhamento instanceof AcompanhamentoLink) {
//                informacoes.addAll(getInformacoesLink(acompanhamento));
//            } else if (acompanhamento instanceof AcompanhamentoManual) {
//                informacoes.addAll(getInformacoesManual(acompanhamento));
//            } else if (acompanhamento instanceof AcompanhamentoExternoSistram) {
//                informacoes.addAll(getInformacoesExternoSistram(acompanhamento));
//            } else if (acompanhamento instanceof AcompanhamentoExternoNavio) {
//                informacoes.addAll(getInformacoesExternoNavio(acompanhamento));
//            } else if (acompanhamento instanceof AcompanhamentoExternoCelular) {
//                informacoes.addAll(getInformacoesExternoCelular(acompanhamento));
//            }
//            informacoes.add(" ");
//        }
//
//        return informacoes;
//    }
//
//    private ArrayList getInformacoesLink(Acompanhamento aco) {
//        ArrayList informacoes = new ArrayList();
//
//        return informacoes;
//    }
//
//    private ArrayList getInformacoesManual(Acompanhamento aco) {
//        ArrayList informacoes = new ArrayList();
//
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//        informacoes.add("Criado por:" + aco.getCriadoPor());
//        informacoes.add("Criado em:" + sdf.format(aco.getCriadoEm()));
//
//        return informacoes;
//    }
//
//    /**
//     * Metodo pagar buscar latitude e Longitude do objeto para Centralização
//     *
//     * @return List<Map> Lista com Longitude e Latitude
//     */
//    public Map buscaLatitudeLongitudePrincipal() {
//
//        WebContext wctx = WebContextFactory.get();
//        HttpServletRequest request = wctx.getHttpServletRequest();
//
//        String identificador = (String) request.getSession().getAttribute("usuarioLogado");
//        Map retorno = new HashMap<>();
//
//        AcompanhamentoExternoCelular acompanhamentoExternoCelular = (AcompanhamentoExternoCelular) Mediador.getInstancia().getGestor().getGestorObjetos().buscar(identificador, AcompanhamentoExternoCelular.class);
//
//        if (acompanhamentoExternoCelular != null) {
//
//            List<CAMPO> camposLatLong = new ArrayList<CAMPO>();
//            camposLatLong.add(CAMPO.LONG);
//            camposLatLong.add(CAMPO.LAT);
//
//            Map acomp = montaItem(acompanhamentoExternoCelular, camposLatLong);
//            if (acomp != null) {
//                retorno.put("LONGITUDE", acomp.get(CAMPO.LONG.toString()));
//                retorno.put("LATITUDE", acomp.get(CAMPO.LAT.toString()));
//            }
//        }
//
//        if (!retorno.containsKey("LONGITUDE") || !retorno.containsKey("LATITUDE")) {
//            TeatroOperacao teatroReferencial = ((ContextoTatico) Mediador.getInstancia().getContextualizador().getContexto()).getTeatroOperacao();
//
//            retorno.put("LONGITUDE", teatroReferencial.getPosicaoTeatroOperacao().getCoordenadaGeografica().getLongitude());
//            retorno.put("LATITUDE", teatroReferencial.getPosicaoTeatroOperacao().getCoordenadaGeografica().getLatitude());
//        }
//
//        return retorno;
//    }
}
