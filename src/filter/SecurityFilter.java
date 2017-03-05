/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import persistence.GestorBancoDados;
import session.SessionUserActive;
import session.UserSession;

/**
 *
 * @author Luciana
 * @author Davi
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = {"*.xhtml"}, servletNames = {"Faces Servlet"})
public class SecurityFilter implements Filter {

    private static final boolean debug = true;
    private FilterConfig filterConfig = null;
    private boolean autorizado;
    private String descricaoUsuario = "";
    private boolean configuracaoAplicacao;

    public SecurityFilter() {
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();
        HttpServletRequest r = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (!(configuracaoAplicacao)) {
            System.out.println("****** Configurando Aplicacao em " + new Date());
            //Executar a classe de Configuracao 
            try {
                GestorBancoDados.getInstancia();
                System.out.println("Aplicação configurada");
                configuracaoAplicacao = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if ((r.getRequestURI().contains("/servidorcentral/login.xhtml"))
                || (r.getRequestURI().contains("/servidorcentral/javax.faces"))
                || (r.getRequestURI().contains("/servidorcentral/index.jsp"))) {
            chain.doFilter(request, response);
            return;
        }

        // Captura o usuario logado na sessao 
        String usuario = (session.getAttribute("usuarioLogado") == null) ? null : (String) session.getAttribute("usuarioLogado");

        Boolean autenticado = (Boolean) session.getAttribute("AUTENTICADO");

        String identificador = r.getParameter("identificador");

        autorizado = false;

        String mobile = r.getParameter("mobile");

        if (usuario == null && mobile == null) {
            res.sendRedirect(r.getContextPath() + "/login.xhtml");
            return;

        } else {

            if (mobile != null) {
                descricaoUsuario = "Aplicativo Android";
                usuario = "app";
            } else {
                descricaoUsuario = "Login Web";
            }

            autorizado = true;

        }

        if (autorizado) {
            usuario = (identificador == null) ? usuario : identificador;
            String tipoLogon = (identificador == null) ? "web" : "aplicativo";
            if (autenticado == null) {
                //Se login efetuado com sucesso invalida a sessão adquirida no acesso ao login
                //e cria uma nova depois que o usuário foi autenticado.
                String idAntesLogin = r.getSession().getId();
                r.getSession().invalidate();
                r.getSession(true);

                //teste do antes e depois de efetuar o login na aplicação.
                r.getSession().setAttribute("idSessaoAntesLogin", idAntesLogin);
                r.getSession().setAttribute("AUTENTICADO", Boolean.TRUE);
                r.getSession().setAttribute("idSessaoDepoisLogin", r.getSession().getId());
                r.getSession().setAttribute("sessaoLogada", true);
                r.getSession().setAttribute("usuarioLogado", usuario);

                r.getSession().setAttribute("mostrarLogoff", identificador == null ? "" : "display: none;");
                System.out.println("Logon no  TeleAssistencia: " + usuario + " IP: " + r.getRemoteAddr() + " ás " + new Date());
            } else {
                r.getSession().setAttribute("usuarioLogado", usuario);
            }

            //Gravar na sessao os usuarios ativos
            UserSession user = new UserSession();
            user.setLogin(usuario);
            user.setIdSessao(r.getSession().getId());
            user.setDataEntrada(new Date());

            user.setIp(r.getRemoteAddr());
            user.setDescricao(descricaoUsuario);
            SessionUserActive.createInstance().addUser(user);
            r.getSession().setAttribute("users", SessionUserActive.createInstance().getUserAtivos());
            r.getSession().setAttribute("tipoLogon", tipoLogon);

            if (mobile != null) {
                res.sendRedirect("/servidorcentral/localizacao.xhtml");
            }

        } else {
            usuario = null;
        }

        if ((usuario == null) && (!(r.getRequestURI().contains("/servidorcentral/javax.faces")))
                && (!(r.getRequestURI().contains("/servidorcentral/login.xhtml")))
                && (!(r.getRequestURI().contains("/servidorcentral/index.jsp")))) {
            res.sendRedirect(r.getContextPath() + "/erroautenticacao.xhtml");
        } else {
            chain.doFilter(request, response);
        }

    }

    /**
     * Return the filter configuration object for this filter.
     *
     * @return
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("SecurityFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("SecurityFilter()");
        }
        StringBuilder sb = new StringBuilder("SecurityFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
