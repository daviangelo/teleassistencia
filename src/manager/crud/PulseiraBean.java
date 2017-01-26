/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager.crud;

import entity.cliente_final.Pulseira;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *Bean para construção da tela de gerência da pulseira.
 * @author Davi Lessa
 * @since 15/01/2017
 */
@ManagedBean
@SessionScoped
public class PulseiraBean {
    
    private String campoBusca;
    private List<Pulseira> listaPulseiras = new ArrayList<>();
    private Pulseira pulseiraSelecionada, novaPulseira;

    public PulseiraBean() {
        carregarPulseiras();
        pulseiraSelecionada = new Pulseira();
        novaPulseira = new Pulseira();
    
    }
    
    /**
     * Carrega todos as pulseiras para popular tabela.
     */
    public void carregarPulseiras() {
        try {
            listaPulseiras = Pulseira.obterPulseiras();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Erro ao carregar a lista de pulseiras");
        }
    }
    
    /**
     * Busca pulseiras com o campo de busca. A lista de pulseiras é
     * atualizada.
     */
    public void buscarPulseiras() {
        try {
            listaPulseiras = Pulseira.pesquisar(campoBusca);

            if (listaPulseiras.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Pulseira(s) não encontrada(s)!",
                                "A sua busca não retornou nenhum resultado"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Erro ao buscar pulseira por campo");
        }
    }
    
    //Carrega os dados referentes ao pulseira selecionado (//@TODO NECESSIDADE)
    public String abrirPulseira() throws Exception {
//        Pulseira ps = Pulseira.obterPulseiraPorID(this.pulseiraSelecionado
//                .getIdPulseira());
//        if (ps != null) {
//
//            listaUsuarios = new ArrayList<>(clienteSelecionado.getUsuarios());

            // Redirecionando
            FacesContext faces = FacesContext.getCurrentInstance();
            ExternalContext context = faces.getExternalContext();
            context.redirect("dadospulseira.xhtml");
//        }

        return null;
    }
    
    /**
     * Salva alteração de dados do pulseira selecionado.
     */
    public void alterarPulseira() {
        try {
            Pulseira.atualizar(pulseiraSelecionada);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Sucesso",
                "A alteração foi efetuada com sucesso."));
    }
    
    /**
     * Exclui uma pulseira e seus dados do banco.
     *
     * @return
     * @throws java.lang.Exception
     *
     */
    public String excluirPulseira() throws Exception {

        try {

            Pulseira.apagar(pulseiraSelecionada);
        } catch (Exception e) {
            e.printStackTrace();
        }

        pulseiraSelecionada = new Pulseira();

        carregarPulseiras();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",
                "Pulseira excluído com sucesso."));

        context.getExternalContext().getFlash().setKeepMessages(true);

        context.getExternalContext().redirect("buscapulseira.xhtml");

        return null;
    }
    
    /**
     * Salva o nova pulseira no banco de dados.
     *
     * @throws IOException
     */
    public void criarPulseira() throws IOException {
        try {

            Pulseira.criar(novaPulseira);

        } catch (Exception e) {
            e.printStackTrace();
        }
        novaPulseira = new Pulseira();

        FacesContext context = FacesContext.getCurrentInstance();

        // Após o salvamento a tabela é populada.
        carregarPulseiras();

        context.addMessage(null, new FacesMessage("Sucesso",
                "Pulseira criado com sucesso."));

        context.getExternalContext().getFlash().setKeepMessages(true);

        context.getExternalContext().redirect("buscapulseira.xhtml");

    }

    public String getCampoBusca() {
        return campoBusca;
    }

    public void setCampoBusca(String campoBusca) {
        this.campoBusca = campoBusca;
    }

    public List<Pulseira> getListaPulseiras() {
        return listaPulseiras;
    }

    public void setListaPulseiras(List<Pulseira> listaPulseiras) {
        this.listaPulseiras = listaPulseiras;
    }

    public Pulseira getPulseiraSelecionada() {
        return pulseiraSelecionada;
    }

    public void setPulseiraSelecionada(Pulseira pulseiraSelecionada) {
        this.pulseiraSelecionada = pulseiraSelecionada;
    }

    public Pulseira getNovaPulseira() {
        return novaPulseira;
    }

    public void setNovaPulseira(Pulseira novaPulseira) {
        this.novaPulseira = novaPulseira;
    }
    
    
    
}
