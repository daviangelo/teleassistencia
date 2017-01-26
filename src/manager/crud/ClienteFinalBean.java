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
import org.primefaces.event.DragDropEvent;

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

    private List<PrestadorSocorro> listaPrestadoresDisponiveis = new ArrayList<>();
    private List<PrestadorSocorro> listaPrestadoresSelecionados = new ArrayList<>();
    private List<PrestadorSocorro> listaPrestadoresExibicao = new ArrayList<>();

    private PrestadorSocorro prestadorSelecionado;

    private List<Pulseira> listaPulseirasDisponiveis = new ArrayList<>();
    private List<Pulseira> listaPulseirasSelecionadas = new ArrayList<>();
    private List<Pulseira> listaPulseirasExibicao = new ArrayList<>();

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
    public String desassociarPrestador() throws Exception {
        try {
                prestadorSelecionado.getUsuarios().remove(usuarioSelecionado);
                usuarioSelecionado.getPrestadoresSocorro().remove(prestadorSelecionado);
                PrestadorSocorro.atualizar(prestadorSelecionado);
                Usuario.atualizar(usuarioSelecionado);
                
        } catch (Exception e) {
            e.printStackTrace();
        }

        prestadorSelecionado = new PrestadorSocorro();
        
        usuarioSelecionado = Usuario.obterUsuarioPorID(usuarioSelecionado.getIdUsuario());
        
        listaPrestadoresExibicao = new ArrayList<>(usuarioSelecionado.getPrestadoresSocorro());
        
        carregarClientes();
        
        preparaDialogPrestadores();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",
                "Prestador de Socorro desassociado com sucesso."));

        context.getExternalContext().getFlash().setKeepMessages(true);

        context.getExternalContext().redirect("dadosusuario.xhtml");

        return null;
    }

    /**
     * Desassocia a pulseira selecionada.
     *
     * @return
     * @throws Exception
     */
    public String desassociarPulseira() throws Exception {
        try {
                pulseiraSelecionada.setUsuario(null);
                Pulseira.atualizar(pulseiraSelecionada);
                
        } catch (Exception e) {
            e.printStackTrace();
        }

        pulseiraSelecionada = new Pulseira();
        
        usuarioSelecionado = Usuario.obterUsuarioPorID(usuarioSelecionado.getIdUsuario());
        
        listaPulseirasExibicao = new ArrayList<>(usuarioSelecionado.getPulseiras());
        
        carregarClientes();
        
        preparaDialogPulseiras();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",
                "Pulseira desassociada com sucesso."));

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
     * Salva a associacao dos prestadores de socorro escolhidoss.
     */
    public void associarPrestador() {

       listaPrestadoresSelecionados.forEach(prestador -> ((PrestadorSocorro) prestador).getUsuarios().add(usuarioSelecionado));
            usuarioSelecionado.getPrestadoresSocorro().addAll(listaPrestadoresSelecionados);
            try {

                Usuario.atualizar(usuarioSelecionado);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String mensagem = "Prestadores de Socorro associados com sucesso.";

        

        listaPrestadoresExibicao = new ArrayList<>(usuarioSelecionado.getPrestadoresSocorro());
        
        preparaDialogPrestadores();

        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Sucesso",
                mensagem));
    }

    /**
     * Salva a associacao das pulseiras escolhidas.
     */
    public void associarPulseira() {

       
            listaPulseirasSelecionadas.forEach(pulseira -> ((Pulseira) pulseira).setUsuario(usuarioSelecionado));
            usuarioSelecionado.getPulseiras().addAll(listaPulseirasSelecionadas);
            try {

                Usuario.atualizar(usuarioSelecionado);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String mensagem = "Pulseiras associadas com sucesso.";

        

        listaPulseirasExibicao = new ArrayList<>(usuarioSelecionado.getPulseiras());
        
        preparaDialogPulseiras();

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
            listaPulseirasExibicao = new ArrayList<>(u.getPulseiras());
            listaPrestadoresExibicao = new ArrayList<>(u.getPrestadoresSocorro());

            preparaDialogPulseiras();
            preparaDialogPrestadores();

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
    
    /**
     * Evento de drag and drop da tabela de associação das pulseiras com o usuário.
     * @param ddEvent 
     */
    public void onPulseiraDrop(DragDropEvent ddEvent) {
        Pulseira pulseira = ((Pulseira) ddEvent.getData());

        listaPulseirasSelecionadas.add(pulseira);
        listaPulseirasDisponiveis.remove(pulseira);
    }
    /**
     * Evento de drag and drop da tabela de associação dos prestadores de socorro com o usuário.
     * @param ddEvent 
     */
    public void onPrestadorDrop(DragDropEvent ddEvent) {
        PrestadorSocorro prestador = ((PrestadorSocorro) ddEvent.getData());

        listaPrestadoresSelecionados.add(prestador);
        listaPrestadoresDisponiveis.remove(prestador);
    }

    /**
     * Prepara as tabelas do dialog de associação das pulseiras.
     */
    public synchronized void preparaDialogPulseiras() {
        try {
            listaPulseirasDisponiveis = Pulseira.obterDesassociadas();
            listaPulseirasSelecionadas = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao obter prestadores de socorro");
        }

    }
    
     /**
     * Prepara as tabelas do dialog de associação dos prestadores de socorro.
     */
    public synchronized void preparaDialogPrestadores() {
        try {
            listaPrestadoresDisponiveis = PrestadorSocorro.obterDesassociados(usuarioSelecionado.getIdUsuario());
            listaPrestadoresSelecionados = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao obter prestadores de socorro");
        }

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

    public List<PrestadorSocorro> getListaPrestadoresDisponiveis() {
        return listaPrestadoresDisponiveis;
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

    public void setListaPrestadoresDisponiveis(List<PrestadorSocorro> listaPrestadoresDisponiveis) {
        this.listaPrestadoresDisponiveis = listaPrestadoresDisponiveis;
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

    public List<Pulseira> getListaPulseirasDisponiveis() {
        return listaPulseirasDisponiveis;
    }

    public void setListaPulseirasDisponiveis(List<Pulseira> listaPulseirasDisponiveis) {
        this.listaPulseirasDisponiveis = listaPulseirasDisponiveis;
    }

    public List<PrestadorSocorro> getListaPrestadoresSelecionados() {
        return listaPrestadoresSelecionados;
    }

    public void setListaPrestadoresSelecionados(List<PrestadorSocorro> listaPrestadoresSelecionados) {
        this.listaPrestadoresSelecionados = listaPrestadoresSelecionados;
    }

    public List<Pulseira> getListaPulseirasSelecionadas() {
        return listaPulseirasSelecionadas;
    }

    public void setListaPulseirasSelecionadas(List<Pulseira> listaPulseirasSelecionadas) {
        this.listaPulseirasSelecionadas = listaPulseirasSelecionadas;
    }

    public List<PrestadorSocorro> getListaPrestadoresExibicao() {
        return listaPrestadoresExibicao;
    }

    public void setListaPrestadoresExibicao(List<PrestadorSocorro> listaPrestadoresExibicao) {
        this.listaPrestadoresExibicao = listaPrestadoresExibicao;
    }

    public List<Pulseira> getListaPulseirasExibicao() {
        return listaPulseirasExibicao;
    }

    public void setListaPulseirasExibicao(List<Pulseira> listaPulseirasExibicao) {
        this.listaPulseirasExibicao = listaPulseirasExibicao;
    }

}
