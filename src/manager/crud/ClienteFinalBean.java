package manager.crud;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import entity.cliente_final.ClienteFinal;
import entity.cliente_final.PrestadorSocorro;
import entity.cliente_final.Usuario;
import java.io.IOException;
import com.google.common.collect.Lists;
import entity.cliente_final.Pulseira;

/**
 * Bean para construção de tela de gerência do Cliente Final.
 *
 * @author Davi Lessa
 */
@ManagedBean
@SessionScoped
public class ClienteFinalBean {

    private List<ClienteFinal> listaClientes = new ArrayList<>();

    private ClienteFinal clienteSelecionado, novoCliente;

    private String campoBusca;

    private List<Usuario> listaUsuarios = new ArrayList<>();

    private Usuario usuarioSelecionado, novoUsuario;

    private List<PrestadorSocorro> listaPrestadores = new ArrayList<>();

    private PrestadorSocorro prestadorSelecionado;

    private List<Pulseira> listaPulseiras;

    private Pulseira pulseiraSelecionada;

    public ClienteFinalBean() {
        carregarClientes();

        clienteSelecionado = new ClienteFinal();
        novoCliente = new ClienteFinal();
        novoUsuario = new Usuario();
        prestadorSelecionado = new PrestadorSocorro();
        pulseiraSelecionada = new Pulseira();

    }

    /**
     * Carrega todos os cliente para popular tabela.
     */
    public void carregarClientes() {
        try {
            listaClientes = ClienteFinal.obterClientes();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Erro ao carregar a lista de clientes");
        }
    }

    /**
     * Busca clientes de acordo com o campo de busca. A lista de clientes é
     * atualizada.
     */
    public void buscarClientes() {
        try {
            listaClientes = ClienteFinal.pesquisar(campoBusca);

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

    /**
     * Salva o novo cliente no banco de dados.
     *
     * @throws IOException
     */
    public void criarCliente() throws IOException {
        try {
            ClienteFinal.criar(novoCliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
        novoCliente = new ClienteFinal();

        FacesContext context = FacesContext.getCurrentInstance();

        // Após o salvamento a tabela é populada.
        carregarClientes();

        context.addMessage(null, new FacesMessage("Sucesso",
                "Cliente criado com sucesso."));

        context.getExternalContext().getFlash().setKeepMessages(true);

        context.getExternalContext().redirect("buscacliente.xhtml");
    }

    //Carrega os dados referentes ao cliente selecionado (//@TODO NECESSIDADE)
    public String abrirCliente() throws Exception {
//        ClienteFinal cf = ClienteFinal.obterClientePorID(this.clienteSelecionado
//                .getIdClienteFinal());
//        if (cf != null) {

            listaUsuarios = new ArrayList<>(clienteSelecionado.getUsuarios());

            // Redirecionando
            FacesContext faces = FacesContext.getCurrentInstance();
            ExternalContext context = faces.getExternalContext();
            context.redirect("dadoscliente.xhtml");
//        }

        return null;
    }

    /**
     * Exclui um cliente final e seus dados do banco.
     *
     * @return
     * @throws java.lang.Exception
     *
     */
    public String excluirCliente() throws Exception {

        try {

            ClienteFinal.apagar(clienteSelecionado);
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

    /**
     * Exclui o prestador selecionado.
     *
     * @return
     * @throws Exception
     */
    public String excluirPrestador() throws Exception {
        try {

            PrestadorSocorro.apagar(prestadorSelecionado);
            listaPrestadores = PrestadorSocorro.obterPrestadores();

        } catch (Exception e) {
            e.printStackTrace();
        }

        prestadorSelecionado = new PrestadorSocorro();

        carregarClientes();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",
                "Prestador excluído com sucesso."));

        context.getExternalContext().getFlash().setKeepMessages(true);

        context.getExternalContext().redirect("dadosusuario.xhtml");

        return null;
    }

    /**
     * Exclui a pulseiraSelecionada selecionada.
     *
     * @return
     * @throws Exception
     */
    public String excluirPulseira() throws Exception {
        try {

            Pulseira.apagar(pulseiraSelecionada);

        } catch (Exception e) {
            e.printStackTrace();
        }

        pulseiraSelecionada = new Pulseira();

        carregarClientes();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",
                "Pulseira excluída com sucesso."));

        context.getExternalContext().getFlash().setKeepMessages(true);

        context.getExternalContext().redirect("dadosusuario.xhtml");

        return null;
    }

    /**
     * Apaga um usuário e atualiza a lista de usuários para exibição.
     *
     * @return
     * @throws Exception
     */
    public String excluirUsuario() throws Exception {

        try {

            Usuario.apagar(usuarioSelecionado);
            listaUsuarios = Usuario.obterUsuarios();
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

    /**
     * Salva alteração de dados do cliente selecionado.
     */
    public void alterarCliente() {
        try {
            ClienteFinal.atualizar(clienteSelecionado);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Sucesso",
                "A alteração foi efetuada com sucesso."));
    }

    /**
     * Salva alteração de dados do usuário selecionado.
     */
    public void alterarUsuario() {
        try {

            Usuario.atualizar(usuarioSelecionado);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Sucesso",
                "A alteração foi efetuada com sucesso."));
    }

    /**
     * Substitui prestador por um limpo e completamente novo.
     */
    public void novoPrestador() {
        prestadorSelecionado = new PrestadorSocorro();
    }

    /**
     * Salva as modificações nos dados do prestador selecionado.
     */
    public void manterPrestador() {

        String mensagem;

        if (prestadorSelecionado.getIdPrestadorSocorro() != 0) {
            try {
                PrestadorSocorro.atualizar(prestadorSelecionado);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            mensagem = "A alteração foi efetuada com sucesso.";

            novoPrestador();
        } else {

            prestadorSelecionado.getUsuarios().add(usuarioSelecionado);
            usuarioSelecionado.getPrestadoresSocorro().add(prestadorSelecionado);
            try {

                Usuario.atualizar(usuarioSelecionado);
            } catch (Exception e) {
                e.printStackTrace();
            }

            mensagem = "Prestador cadastrado com sucesso.";

        }

        listaPrestadores = new ArrayList<>(usuarioSelecionado.getPrestadoresSocorro());

        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Sucesso",
                mensagem));
    }

    /**
     * Salva a alteração nos dados da pulseiraSelecionada selecionada.
     */
    public void manterPulseira() {

        String mensagem;

        if (pulseiraSelecionada.getIdPulseira() != 0) {
            try {
                Pulseira.atualizar(pulseiraSelecionada);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            mensagem = "A alteração foi efetuada com sucesso.";
        } else {

            pulseiraSelecionada.setUsuario(usuarioSelecionado);
            usuarioSelecionado.getPulseiras().add(pulseiraSelecionada);
            try {

                Usuario.atualizar(usuarioSelecionado);
            } catch (Exception e) {
                e.printStackTrace();
            }

            mensagem = "Pulseira cadastrado com sucesso.";

        }

        listaPrestadores = new ArrayList<>(usuarioSelecionado.getPrestadoresSocorro());

        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Sucesso",
                mensagem));
    }

    /**
     * Carrega os dados específicos do usuário que foi solicitada a gerência de
     * dados.
     *
     * @return
     * @throws Exception
     */
    public String abrirUsuario() throws Exception {
        Usuario u = Usuario.obterUsuarioPorID(this.usuarioSelecionado
                .getIdUsuario());
        if (u != null) {

            listaPrestadores = new ArrayList<>(u.getPrestadoresSocorro());

            listaPulseiras = new ArrayList<>(u.getPulseiras());

            // Redirecionando
            FacesContext faces = FacesContext.getCurrentInstance();
            ExternalContext context = faces.getExternalContext();
            context.redirect("dadosusuario.xhtml");
        } else {
            System.out.println("Usuario nao encontrado");
        }

        return null;
    }

    /**
     * Salva os dados de um novo usuário no banco de dados.
     */
    public void criarUsuario() {
        novoUsuario.setClienteFinal(clienteSelecionado);
        clienteSelecionado.getUsuarios().add(novoUsuario);
        
        try {
            ClienteFinal.atualizar(clienteSelecionado);
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

    public Pulseira getPulseiraSelecionada() {
        return pulseiraSelecionada;
    }

    public void setPulseiraSelecionada(Pulseira pulseiraSelecionada) {
        this.pulseiraSelecionada = pulseiraSelecionada;
    }

    public List<Pulseira> getListaPulseiras() {
        return listaPulseiras;
    }

    public void setListaPulseiras(List<Pulseira> listaPulseiras) {
        this.listaPulseiras = listaPulseiras;
    }

}
