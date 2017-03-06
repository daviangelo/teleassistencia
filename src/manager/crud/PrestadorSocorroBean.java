/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager.crud;

import entity.cliente_final.PrestadorSocorro;
import entity.cliente_final.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Bean para construção da tela de gerência do prestador de socorro.
 *
 * @author Davi Lessa
 * @since 15/01/2017
 */
@ManagedBean
@SessionScoped
public class PrestadorSocorroBean {

    private String campoBusca;

    private List<PrestadorSocorro> listaPrestadores = new ArrayList<>();

    private PrestadorSocorro prestadorSelecionado, novoPrestador;

    private boolean novo;

    public PrestadorSocorroBean() {
        carregarPrestadores();
        prestadorSelecionado = new PrestadorSocorro();
        novoPrestador = new PrestadorSocorro();
        novo = true;
    }

    /**
     * Carrega todos os prestadores para popular tabela.
     */
    public void carregarPrestadores() {
        try {
            listaPrestadores = PrestadorSocorro.obterPrestadores();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Erro ao carregar a lista de prestadores");
        }
    }

    /**
     * Busca p de restadores de socorro acordo com o campo de busca. A lista de
     * prestadores é atualizada.
     */
    public void buscarPrestadores() {
        try {
            listaPrestadores = PrestadorSocorro.pesquisar(campoBusca);

            if (listaPrestadores.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Prestador(es) não encontrado(s)!",
                                "A sua busca não retornou nenhum resultado"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Erro ao buscar prestador por campo");
        }
    }

    //Carrega os dados referentes ao prestador selecionado (//@TODO NECESSIDADE)
    public String abrirPrestador() throws Exception {
//        PrestadorSocorro ps = PrestadorSocorro.obterPrestadorPorID(this.prestadorSelecionado
//                .getIdPrestadorSocorro());
//        if (ps != null) {
//
//            listaUsuarios = new ArrayList<>(clienteSelecionado.getUsuarios());
        novo = false;

        // Redirecionando
        FacesContext faces = FacesContext.getCurrentInstance();
        ExternalContext context = faces.getExternalContext();
        context.redirect("dadosprestador.xhtml");
//        }

        return null;
    }

    /**
     * Salva alteração de dados do prestador de socorro selecionado.
     */
    public void alterarPrestador() throws IOException {
        if (validarPrestador(prestadorSelecionado)) {
            try {
                PrestadorSocorro.atualizar(prestadorSelecionado);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            FacesContext context = FacesContext.getCurrentInstance();

            carregarPrestadores();

            context.addMessage(null, new FacesMessage("Sucesso!",
                    "A alteração foi efetuada com sucesso."));

            context.getExternalContext().getFlash().setKeepMessages(true);

            context.getExternalContext().redirect("buscaprestador.xhtml");
        }
    }

    /**
     * Exclui um cliente final e seus dados do banco.
     *
     * @return
     * @throws java.lang.Exception
     *
     */
    public String excluirPrestador() throws Exception {
        try {
            Set<Usuario> listaUsuarios = prestadorSelecionado.getUsuarios();

            for (Usuario u : listaUsuarios) {
                desassociarUsuario(u);
            }

            PrestadorSocorro.apagar(prestadorSelecionado);
        } catch (Exception e) {
            e.printStackTrace();
        }

        prestadorSelecionado = new PrestadorSocorro();

        carregarPrestadores();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso!",
                "Prestador excluído com sucesso."));

        context.getExternalContext().getFlash().setKeepMessages(true);

        context.getExternalContext().redirect("buscaprestador.xhtml");

        return null;
    }

    /**
     * Exclui a associação com o usuário recebido.
     *
     * @param u
     * @throws Exception
     */
    public void desassociarUsuario(Usuario u) throws Exception {
        try {
            u.getPrestadoresSocorro().remove(prestadorSelecionado);
            prestadorSelecionado.getUsuarios().remove(u);
            Usuario.atualizar(u);
            PrestadorSocorro.atualizar(prestadorSelecionado);
        } catch (Exception e) {
            e.printStackTrace();
        }

        prestadorSelecionado = PrestadorSocorro.obterPrestadorPorID(prestadorSelecionado.getIdPrestadorSocorro());
    }

    /**
     * Salva o novo cliente no banco de dados.
     *
     * @throws IOException
     */
    public void criarPrestador() throws IOException {
        if (validarPrestador(novoPrestador)) {
            try {
                PrestadorSocorro.criar(novoPrestador);

            } catch (Exception e) {
                e.printStackTrace();
            }
            novoPrestador = new PrestadorSocorro();

            FacesContext context = FacesContext.getCurrentInstance();

            // Após o salvamento a tabela é populada.
            carregarPrestadores();

            context.addMessage(null, new FacesMessage("Sucesso!",
                    "Prestador criado com sucesso."));

            context.getExternalContext().getFlash().setKeepMessages(true);

            context.getExternalContext().redirect("buscaprestador.xhtml");
        }
    }

    public String getCampoBusca() {
        return campoBusca;
    }

    public void setCampoBusca(String campoBusca) {
        this.campoBusca = campoBusca;
    }

    public List<PrestadorSocorro> getListaPrestadores() {
        return listaPrestadores;
    }

    public void setListaPrestadores(List<PrestadorSocorro> listaPrestadores) {
        this.listaPrestadores = listaPrestadores;
    }

    public PrestadorSocorro getPrestadorSelecionado() {
        return prestadorSelecionado;
    }

    public void setPrestadorSelecionado(PrestadorSocorro prestadorSelecionado) {
        this.prestadorSelecionado = prestadorSelecionado;
    }

    public PrestadorSocorro getNovoPrestador() {
        return novoPrestador;
    }

    public void setNovoPrestador(PrestadorSocorro novoPrestador) {
        this.novoPrestador = novoPrestador;
    }

    /**
     * Abre a página responsável pelo cadastro, alterando também o valor do
     * atributo que indica se o objeto é novo ou é uma alteração.
     *
     * @throws IOException
     */
    public void abrirNovoPrestador() throws IOException {
        novo = true;

        novoPrestador = new PrestadorSocorro();

        FacesContext context = FacesContext.getCurrentInstance();

        context.getExternalContext().redirect("dadosprestador.xhtml");
    }

    public boolean isNovo() {
        return novo;
    }

    public boolean teste() {
        return false;
    }

    /**
     * Verifica os dados do prestador
     *
     * @return true (prestador válido)/ false (prestador inválido)
     */
    private boolean validarPrestador(PrestadorSocorro p) {
        try {
            if (!PrestadorSocorro.pesquisar(p.getNome()).isEmpty()) {
                exibirMensagem("Erro!", "Já existe um prestador de socorro cadastrado com esse Nome.");
                return false;
            }

            if (!PrestadorSocorro.pesquisar(p.getTelefone()).isEmpty()) {
                exibirMensagem("Erro!", "Já existe um prestador de socorro cadastrado com esse Telefone.");
                return false;
            }
        } catch (Exception ex) {
            Logger.getLogger(ClienteFinalBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    private void exibirMensagem(String mensagem, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage(titulo, mensagem));
    }
}
