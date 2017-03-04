/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dwr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

/**
 * Classe responsável por verificar a permissão de visualização do usuário.
 *
 * @author Davi
 */
public class AtualizacaoPermissao {

    private boolean autorizado;
    private Boolean celular;

    public enum ESTADO {
        APP_ERRO,
        APP_AUTORIZADO_VISUALIZACAO,
        APP_AUTORIZADO_APENAS_VISUALIZACAO,
        APP_AUTORIZADO_SEM_VISUALIZACAO,
        APP_NAO_AUTORIZADO,
        WEB_NAO_AUTORIZADO,
        WEB_AUTORIZADO;

    }

    public AtualizacaoPermissao() {

    }

//    /**
//     * Metodo para verificar se o usuario foi desabilitado depois de ser logado
//     *
//     * @param tipoLogon
//     * @param login
//     * @return
//     */
//    public String isUsuarioHabilitado(String tipoLogon, String login) {
//        String autorizacao = "";
//        switch (tipoLogon) {
//            case "aplicativo":
//                autorizacao = this.isHabilitadoAplicativo(login);
//                break;
//            case "web":
//                autorizacao = this.isHabilitadoWeb(login);
//                break;
//        }
//        return autorizacao;
//    }

//    /**
//     * Verifica se o Identificador continua com autorizado no HidraWeb
//     *
//     * @param identificador
//     */
//    private String isHabilitadoWeb(String login) {
//        //Verifica se o identificador tem autorizado de
//
//        String autorizacao = "";
//        boolean habilitado = false;
//        DAOPessoaUsuario daou = new DAOPessoaUsuario();
//        try {
//
//            habilitado = daou.verificaLoginWeb(login);
//        } catch (Exception ex) {
//            Log.gravarLogExcecao("Erro ao verificar habilitação do usuario depois de logado.", daou, ex);
//        }
//        autorizacao = habilitado ? ESTADO.WEB_AUTORIZADO.name() : ESTADO.WEB_NAO_AUTORIZADO.name();
//
//        return autorizacao;
//    }

//    /**
//     * Verifica se o usuario continua com autorizado no Hidraweb
//     *
//     * @param identificador
//     */
//    private String isHabilitadoAplicativo(String identificador) {
//        //Verifica se o identificador tem autorizado de
//
//        boolean visualizacao, autorizacao, enviarPosicao;
//        DAOAcessoExterno daou = new DAOAcessoExterno();
//        try {
//            identificador = identificador.toUpperCase();
//            autorizacao = daou.verificaAcessoExternoCelular(identificador);
//            visualizacao = daou.verificaPermissaoVisualizacaoCelular(identificador);
//            enviarPosicao = daou.verificaEnvioPosicao(identificador);
//
//            if (!autorizacao) {
//                return ESTADO.APP_NAO_AUTORIZADO.name();
//            }
//            if (autorizacao && visualizacao) {
//                return ESTADO.APP_AUTORIZADO_VISUALIZACAO.name();
//            }
//            if (autorizacao && !visualizacao) {
//                return ESTADO.APP_AUTORIZADO_SEM_VISUALIZACAO.name();
//            }
//
//            if (autorizacao && !enviarPosicao && visualizacao) {
//                return ESTADO.APP_AUTORIZADO_APENAS_VISUALIZACAO.name();
//            }
//
//        } catch (Exception ex) {
//            Log.gravarLogExcecao("Erro ao autenticar usuário.", daou, ex);
//            return ESTADO.APP_NAO_AUTORIZADO.name();
//        }
//        
//        return ESTADO.APP_NAO_AUTORIZADO.name();
//    }

    /**
     * Verifica a existência de uma sessão ativa;
     *
     * @return
     */
    public boolean isSessaoAtiva() {
        WebContext wctx = WebContextFactory.get();
        HttpServletRequest request = wctx.getHttpServletRequest();
        HttpSession session = request.getSession();
        String usuario = (String) session.getAttribute("usuarioLogado");
        return (usuario != null);
    }

//    /**
//     * Verificacao se o usuário estpa autorizado.
//     *
//     * @return
//     */
//    public String isAutorizado() {
//        WebContext wctx = WebContextFactory.get();
//        HttpServletRequest request = wctx.getHttpServletRequest();
//
//        String tipoLogon = (String) request.getSession().getAttribute("tipoLogon");
//        String usuario = (String) request.getSession().getAttribute("usuarioLogado");
//
//        if (tipoLogon != null && usuario != null) {
//            return isUsuarioHabilitado(tipoLogon, usuario);
//        }
//        if (isCelular()) {
//            return ESTADO.APP_ERRO.name();
//
//        } else {
//            return ESTADO.WEB_NAO_AUTORIZADO.name();
//        }
//    }

    /**
     * Método que identifica se a sessão é um celular para o javascript.
     *
     * @return
     */
    public boolean isCelular() {

        if (celular = null) {
            WebContext wctx = WebContextFactory.get();
            HttpServletRequest request = wctx.getHttpServletRequest();

            celular = (String) wctx.getSession().getAttribute("mostrarLogoff") != "";

        }

        return celular;
    }

}
