/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager.crud;

import entity.cliente_final.PrestadorSocorro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *Bean para construção da tela de gerência do prestador de socorro.
 * @author Davi Lessa
 * @since 15/01/2017
 */
@ManagedBean
@SessionScoped
public class PrestadorSocorroBean {
    private String campoBusca;
    private List<PrestadorSocorro> listaPrestadores = new ArrayList<>();
    private PrestadorSocorro prestadorSelecionado, novoPrestador;

    public PrestadorSocorroBean() {
        carregarPrestadores();
        prestadorSelecionado = new PrestadorSocorro();
        novoPrestador = new PrestadorSocorro();
    
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
     * Busca p de restadores de socorro acordo com o campo de busca. A lista de prestadores é
     * atualizada.
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
    public void alterarPrestador() {
        try {
            PrestadorSocorro.atualizar(prestadorSelecionado);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Sucesso",
                "A alteração foi efetuada com sucesso."));
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

            PrestadorSocorro.apagar(prestadorSelecionado);
        } catch (Exception e) {
            e.printStackTrace();
        }

        prestadorSelecionado = new PrestadorSocorro();

        carregarPrestadores();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",
                "Prestador excluído com sucesso."));

        context.getExternalContext().getFlash().setKeepMessages(true);

        context.getExternalContext().redirect("buscaprestador.xhtml");

        return null;
    }
    
    /**
     * Salva o novo cliente no banco de dados.
     *
     * @throws IOException
     */
    public void criarPrestador() throws IOException {
        try {

            PrestadorSocorro.criar(novoPrestador);

        } catch (Exception e) {
            e.printStackTrace();
        }
        novoPrestador = new PrestadorSocorro();

        FacesContext context = FacesContext.getCurrentInstance();

        // Após o salvamento a tabela é populada.
        carregarPrestadores();

        context.addMessage(null, new FacesMessage("Sucesso",
                "Prestador criado com sucesso."));

        context.getExternalContext().getFlash().setKeepMessages(true);

        context.getExternalContext().redirect("buscaprestador.xhtml");

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
    
    
}
