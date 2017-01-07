package manager.crud;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import persistence.dao.DAO;
import persistence.dao.DAOClienteFinal;
import entity.cliente_final.CentralMobile;
import entity.cliente_final.ClienteFinal;
import entity.cliente_final.Equipamento;
import entity.cliente_final.PrestadorSocorro;
import entity.cliente_final.Usuario;
import java.io.IOException;
import javax.faces.component.UIViewRoot;
import com.google.common.collect.Lists;

@ManagedBean
@SessionScoped
public class ClienteFinalMB {

    private List<ClienteFinal> listaClientes = new ArrayList<>();

    private ClienteFinal clienteSelecionado, novoCliente;

    private String campoBusca;

    private List<Equipamento> listaEquipamentos = new ArrayList<>();

    private Equipamento equipamentoSelecionado, novoEquipamento;

    private List<Usuario> listaUsuarios = new ArrayList<>();

    private Usuario usuarioSelecionado, novoUsuario;

    private List<PrestadorSocorro> listaPrestadores = new ArrayList<>();

    private PrestadorSocorro prestadorSelecionado;

    DAOClienteFinal daoCliente = new DAOClienteFinal();

    DAO<Usuario> daoUsuario = new DAO<>(Usuario.class);

    DAO<PrestadorSocorro> daoPrestador = new DAO<>(PrestadorSocorro.class);

    public ClienteFinalMB() {
        carregarClientes();

        clienteSelecionado = new ClienteFinal();
        novoCliente = new ClienteFinal();
        novoUsuario = new Usuario();
        novoEquipamento = new CentralMobile();
        prestadorSelecionado = new PrestadorSocorro();
    }

    /**
     * Carrega todos os cliente para popular tabela.
     */
    public void carregarClientes() {
        try {
            listaClientes = daoCliente.listarTodos();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Erro ao carregar a lista de clientes");
        }
    }

    public void buscarClientes() {
        try {
            listaClientes = daoCliente.pesquisa(campoBusca);

            if (listaClientes.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Cliente(s) não encontrado(s)!",
                                "A sua busca não retornou nenhum resultado"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Erro ao buscar cliente por campo");
        }
    }

    public void criarCliente() throws IOException {
        try {

            daoCliente.criar(novoCliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
        novoCliente = new ClienteFinal();

        FacesContext context = FacesContext.getCurrentInstance();

//        List<String> clientIds = Lists.newArrayList(":formCadastro:nome", ":formCadastro:cnpj", ":formCadastro:inscE", ":formCadastro:telefone");
//
//        UIViewRoot view = context.getViewRoot();
//        view.resetValues(context, clientIds);
        carregarClientes();

        context.addMessage(null, new FacesMessage("Sucesso",
                "Cliente criado com sucesso."));

        context.getExternalContext().getFlash().setKeepMessages(true);

        context.getExternalContext().redirect("buscacliente.xhtml");

    }

    public String abrirCliente() throws Exception {
        ClienteFinal cf = daoCliente.listarPorID(this.clienteSelecionado
                .getIdClienteFinal());
        if (cf != null) {

            listaUsuarios = new ArrayList<>(clienteSelecionado.getUsuarios());
            listaEquipamentos = new ArrayList<>(clienteSelecionado.getEquipamentos());

            // Redirecionando
            FacesContext faces = FacesContext.getCurrentInstance();
            ExternalContext context = faces.getExternalContext();
            context.redirect("dadoscliente.xhtml");
        }

        return null;
    }

    public String excluirCliente() throws Exception {

        try {

            daoCliente.apagar(clienteSelecionado);
        } catch (Exception e) {
            e.printStackTrace();
        }

        clienteSelecionado = new ClienteFinal();
        
        carregarClientes();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",
                "Cliente excluído com sucesso."));

        context.getExternalContext().getFlash().setKeepMessages(true);

        context.getExternalContext().redirect("buscacliente.xhtml");

        return null;
    }

    public String excluirPrestador() throws Exception {
        try {

            daoPrestador.apagar(prestadorSelecionado);
            listaPrestadores = daoPrestador.listarTodos();

        } catch (Exception e) {
            e.printStackTrace();
        }

        prestadorSelecionado = new PrestadorSocorro();
        
        carregarClientes();
        
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",
                "Cliente excluído com sucesso."));

        context.getExternalContext().getFlash().setKeepMessages(true);

        context.getExternalContext().redirect("dadosusuario.xhtml");

        return null;
    }

    public String excluirUsuario() throws Exception {

         try {

            daoUsuario.apagar(usuarioSelecionado);
            listaUsuarios = daoUsuario.listarTodos();
            clienteSelecionado = listaUsuarios.get(0).getClienteFinal();

        } catch (Exception e) {
            e.printStackTrace();
        }

        usuarioSelecionado = new Usuario();
        
        carregarClientes();
        
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",
                "Usuário excluído com sucesso."));

        context.getExternalContext().getFlash().setKeepMessages(true);

        context.getExternalContext().redirect("dadoscliente.xhtml");

        return null;
    }

    public String excluirEquipamento() throws Exception {

        carregarClientes();

        FacesContext faces = FacesContext.getCurrentInstance();
        ExternalContext context = faces.getExternalContext();
        context.redirect("buscacliente.xhtml");

        System.out.println("cliente excluído");

        return null;
    }

    public void alterarCliente() {
        try {
            daoCliente.atualizar(clienteSelecionado);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Sucesso",
                "A alteração foi efetuada com sucesso."));
    }

    public void alterarUsuario() {
        try {

            daoUsuario.atualizar(usuarioSelecionado);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Sucesso",
                "A alteração foi efetuada com sucesso."));
    }

    public void novoPrestador() {
        prestadorSelecionado = new PrestadorSocorro();
    }

    public void manterPrestador() {

        String mensagem;

        if (prestadorSelecionado.getIdPrestadorSocorro() != 0) {
            try {
                daoPrestador.atualizar(prestadorSelecionado);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            mensagem = "A alteração foi efetuada com sucesso.";

            novoPrestador();
        } else {

            prestadorSelecionado.setUsuario(usuarioSelecionado);
            usuarioSelecionado.getPrestadoresSocorro().add(prestadorSelecionado);
            try {

                daoUsuario.atualizar(usuarioSelecionado);
            } catch (Exception e) {
                e.printStackTrace();
            }

            mensagem = "Usuário cadastrado com sucesso.";

        }

        listaPrestadores = new ArrayList<>(usuarioSelecionado.getPrestadoresSocorro());

        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Sucesso",
                mensagem));
    }

    public String abrirUsuario() throws Exception {
        Usuario u = daoUsuario.listarPorID(this.usuarioSelecionado
                .getIdUsuario());
        if (u != null) {

            listaPrestadores = new ArrayList<>(u.getPrestadoresSocorro());

            // Redirecionando
            FacesContext faces = FacesContext.getCurrentInstance();
            ExternalContext context = faces.getExternalContext();
            context.redirect("dadosusuario.xhtml");
        } else {
            System.out.println("Usuario nao encontrado");
        }

        return null;
    }

    public void criarUsuario() {
        novoUsuario.setClienteFinal(clienteSelecionado);
        clienteSelecionado.getUsuarios().add(novoUsuario);
        try {

            daoCliente.atualizar(clienteSelecionado);
        } catch (Exception e) {
            e.printStackTrace();
        }

        listaUsuarios = Lists.newArrayList(clienteSelecionado.getUsuarios());

        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Sucesso",
                "Usuário criado com sucesso."));

        novoUsuario = new Usuario();
    }

    public List<ClienteFinal> getListaClientes() {
        return listaClientes;
    }

    public ClienteFinal getClienteSelecionado() {
        return clienteSelecionado;
    }

    public List<Equipamento> getListaEquipamentos() {
        return listaEquipamentos;
    }

    public Equipamento getEquipamentoSelecionado() {
        return equipamentoSelecionado;
    }

    public Equipamento getNovoEquipamento() {
        return novoEquipamento;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public Usuario getNovoUsuario() {
        return novoUsuario;
    }

    public List<PrestadorSocorro> getListaPrestadores() {
        return listaPrestadores;
    }

    public PrestadorSocorro getPrestadorSelecionado() {
        return prestadorSelecionado;
    }

    public String getCampoBusca() {
        return campoBusca;
    }

    public void setCampoBusca(String campoBusca) {
        this.campoBusca = campoBusca;
    }

    public void setListaClientes(List<ClienteFinal> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public void setClienteSelecionado(ClienteFinal clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }

    public void setListaEquipamentos(List<Equipamento> listaEquipamentos) {
        this.listaEquipamentos = listaEquipamentos;
    }

    public void setEquipamentoSelecionado(Equipamento equipamentoSelecionado) {
        this.equipamentoSelecionado = equipamentoSelecionado;
    }

    public void setNovoEquipamento(Equipamento novoEquipamento) {
        this.novoEquipamento = novoEquipamento;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public void setNovoUsuario(Usuario novoUsuario) {
        this.novoUsuario = novoUsuario;
    }

    public void setListaPrestadores(List<PrestadorSocorro> listaPrestadores) {
        this.listaPrestadores = listaPrestadores;
    }

    public void setPrestadorSelecionado(PrestadorSocorro prestadorSelecionado) {
        this.prestadorSelecionado = prestadorSelecionado;
    }

    public ClienteFinal getNovoCliente() {
        return novoCliente;
    }

    public void setNovoCliente(ClienteFinal novoCliente) {
        this.novoCliente = novoCliente;
    }

}
