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
import java.util.HashSet;
import org.primefaces.event.DragDropEvent;
import org.primefaces.model.DualListModel;

/**
 * Bean para construção de tela de gerência do Cliente Final.
 *
 * @author Davi Lessa
 */
@ManagedBean
@SessionScoped
public class ClienteFinalBean {

    private ClienteFinal clienteSelecionado, novoCliente;

    private Usuario usuarioSelecionado, novoUsuario;

    private PrestadorSocorro prestadorSelecionado;

    private Pulseira pulseiraSelecionada;

    private String campoBusca;

    private List<ClienteFinal> listaClientes = new ArrayList<>();
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private List<PrestadorSocorro> listaPrestadoresDisponiveis = new ArrayList<>();
    private List<PrestadorSocorro> listaPrestadoresSelecionados = new ArrayList<>();
    private List<PrestadorSocorro> listaPrestadoresExibicao = new ArrayList<>();
    private List<Pulseira> listaPulseirasDisponiveis = new ArrayList<>();
    private List<Pulseira> listaPulseirasSelecionadas = new ArrayList<>();
    private List<Pulseira> listaPulseirasExibicao = new ArrayList<>();
    
    private DualListModel<PrestadorSocorro> dualListPrestador;

    private boolean clienteNovo, usuarioNovo, pulseiraNova, prestadorNovo, showDetail;

    public ClienteFinalBean() {
        carregarClientes();

        clienteSelecionado = new ClienteFinal();
        novoCliente = new ClienteFinal();
        novoUsuario = new Usuario();
        prestadorSelecionado = new PrestadorSocorro();
        pulseiraSelecionada = new Pulseira();
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

    public boolean isClienteNovo() {
        return clienteNovo;
    }

    public boolean isUsuarioNovo() {
        return usuarioNovo;
    }

    public void setUsuarioNovo(boolean usuarioNovo) {
        this.usuarioNovo = usuarioNovo;
    }

    public boolean isPulseiraNova() {
        return pulseiraNova;
    }

    public void setPulseiraNova(boolean pulseiraNova) {
        this.pulseiraNova = pulseiraNova;
    }

    public boolean isShowDetail() {
        return showDetail;
    }

    public void setShowDetail(boolean showDetail) {
        this.showDetail = showDetail;
    }

    public boolean isPrestadorNovo() {
        return prestadorNovo;
    }

    public void setPrestadorNovo(boolean prestadorNovo) {
        this.prestadorNovo = prestadorNovo;
    }

    //-------------------------Cliente
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
     * Salva os dados de um novo cliente no banco de dados ou atualiza os dados
     * de um cliente existente.
     *
     * @throws java.io.IOException
     */
    public void salvarCliente() throws IOException {
        String mensagem;

        if (clienteNovo) {
            try {
                ClienteFinal.criar(novoCliente);
            } catch (Exception e) {
                e.printStackTrace();
            }

            novoCliente = new ClienteFinal();

            mensagem = "Cliente criado com sucesso.";
        } else {
            try {
                ClienteFinal.atualizar(clienteSelecionado);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            mensagem = "A alteração foi efetuada com sucesso.";
        }

        // Após o salvamento a tabela é populada.
        carregarClientes();

        showDetail = true;

        redirecionarComMensagem("buscacliente.xhtml", mensagem, "Sucesso!");
    }

    /**
     * Abre a página referente ao formulário de usuário.
     *
     * @param novo - define se o cliente é novo ou não
     * @return
     */
    public String abrirCliente(boolean novo) {
        if (!novo) {
            listaUsuarios = new ArrayList<>(clienteSelecionado.getUsuarios());

            clienteNovo = novo;
        } else {
            clienteNovo = novo;
        }

        showDetail = false;

        redirecionar("dadoscliente.xhtml");

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

        redirecionarComMensagem("buscacliente.xhtml", "Cliente excluído com sucesso.", "Sucesso!");

        return null;
    }

    //-------------------------Usuário
    /**
     * Abre a página referente ao formulário de usuário.
     *
     * @param novo - define se o usuário é novo ou não
     * @return
     * @throws java.lang.Exception
     */
    public String abrirUsuario(boolean novo) throws Exception {
        if (!novo) {
            usuarioNovo = novo;

            Usuario u = Usuario.obterUsuarioPorID(this.usuarioSelecionado
                    .getIdUsuario());

            if (u != null) {
                listaPulseirasExibicao = new ArrayList<>(u.getPulseiras());
                listaPrestadoresExibicao = new ArrayList<>(u.getPrestadoresSocorro());

                preparaDialogPulseiras();
                preparaDialogPrestadores();
            } else {
                System.out.println("Usuario nao encontrado");
            }

            listaUsuarios = new ArrayList<>(clienteSelecionado.getUsuarios());
        } else {
            usuarioNovo = novo;
        }

        showDetail = false;

        redirecionar("dadosusuario.xhtml");

        return null;
    }

    /**
     * Salva os dados de um novo usuário no banco de dados ou atualiza os dados
     * de um usuário existente.
     *
     * @throws java.io.IOException
     */
    public void salvarUsuario() throws IOException {
        String mensagem;

        if (usuarioNovo) {
            novoUsuario.setClienteFinal(clienteSelecionado);
            clienteSelecionado.getUsuarios().add(novoUsuario);

            try {
                ClienteFinal.atualizar(clienteSelecionado);
            } catch (Exception e) {
                e.printStackTrace();
            }

            listaUsuarios = Lists.newArrayList(clienteSelecionado.getUsuarios());

            novoUsuario = new Usuario();

            mensagem = "Usuario criado com sucesso.";
        } else {
            try {
                Usuario.atualizar(usuarioSelecionado);
            } catch (Exception e) {
                e.printStackTrace();
            }

            mensagem = "A alteração foi efetuada com sucesso.";
        }

        // Após o salvamento a tabela é populada.
        carregarClientes();

        showDetail = true;

        redirecionarComMensagem("dadoscliente.xhtml", mensagem, "Sucesso!");
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

        showDetail = true;

        redirecionarComMensagem("dadoscliente.xhtml", "Sucesso!", "Usuário excluído com sucesso.");

        return null;
    }

    //-------------------------Pulseira
    /**
     * Abre a página referente ao formulário de pulseira.
     *
     * @param nova - define se a pulseira é nova ou não
     * @return
     * @throws java.lang.Exception
     */
    public String abrirPulseira(boolean nova) throws Exception {
        if (!nova) {
            pulseiraNova = nova;
        } else {
            pulseiraNova = nova;

            pulseiraSelecionada = new Pulseira();
        }

        showDetail = false;

        redirecionar("dadospulseirausuario.xhtml");

        return null;
    }

    /**
     * Salva os dados de uma nova pulseira no banco de dados ou atualiza os
     * dados de uma pulseira existente.
     *
     * @throws java.io.IOException
     */
    public void salvarPulseira() throws IOException {
        String mensagem;

        if (pulseiraNova) {
            try {
                Pulseira.criar(pulseiraSelecionada);
            } catch (Exception e) {
                e.printStackTrace();
            }

            listaPulseirasSelecionadas.add(pulseiraSelecionada);
            associarPulseira();

            pulseiraSelecionada = new Pulseira();

            mensagem = "Pulseira criada e associada com sucesso.";
        } else {
            try {
                Pulseira.atualizar(pulseiraSelecionada);
            } catch (Exception e) {
                e.printStackTrace();
            }

            mensagem = "Pulseira alterada com sucesso.";
        }

        showDetail = true;

        redirecionarComMensagem("dadosusuario.xhtml", mensagem, "Sucesso!");
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
            usuarioSelecionado.getPulseiras().remove(pulseiraSelecionada);
            Pulseira.atualizar(pulseiraSelecionada);
        } catch (Exception e) {
            e.printStackTrace();
        }

        pulseiraSelecionada = new Pulseira();

        usuarioSelecionado = Usuario.obterUsuarioPorID(usuarioSelecionado.getIdUsuario());
        listaPulseirasExibicao = new ArrayList<>(usuarioSelecionado.getPulseiras());

        preparaDialogPulseiras();

        showDetail = true;

        redirecionarComMensagem("dadosusuario.xhtml", "Sucesso!", "Pulseira desassociada com sucesso.");

        return null;
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

        listaPulseirasExibicao = new ArrayList<>(usuarioSelecionado.getPulseiras());

        preparaDialogPulseiras();

        if (!pulseiraNova) {
            exibirMensagem("Pulseiras associadas com sucesso.");
        }
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

    //-------------------------Prestador
    public void abrirPrestador(boolean novo) throws IOException {
        if (novo) {
            prestadorNovo = novo;

            prestadorSelecionado = new PrestadorSocorro();
        } else {
            prestadorNovo = novo;
        }

        showDetail = false;

        redirecionar("dadosprestadorusuario.xhtml");
    }

    /**
     * Salva os dados de um novo prestador no banco de dados ou atualiza os
     * dados de um prestador existente.
     *
     * @throws java.io.IOException
     */
    public void salvarPrestador() throws IOException {
        String mensagem;

        if (prestadorNovo) {
            try {
                PrestadorSocorro.criar(prestadorSelecionado);
            } catch (Exception e) {
                e.printStackTrace();
            }

            listaPrestadoresSelecionados.add(prestadorSelecionado);
            associarPrestador();

            prestadorSelecionado = new PrestadorSocorro();

            showDetail = true;

            mensagem = "Prestador criado e associado com sucesso.";
        } else {
            try {
                PrestadorSocorro.atualizar(prestadorSelecionado);
            } catch (Exception e) {
                e.printStackTrace();
            }

            showDetail = true;

            mensagem = "Prestador alterado com sucesso.";
        }

        showDetail = true;

        redirecionarComMensagem("dadosusuario.xhtml", mensagem, "Sucesso!");
    }

    /**
     * Exclui a associação com o prestador selecionado.
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

        showDetail = true;

        prestadorSelecionado = new PrestadorSocorro();

        usuarioSelecionado = Usuario.obterUsuarioPorID(usuarioSelecionado.getIdUsuario());

        listaPrestadoresExibicao = new ArrayList<>(usuarioSelecionado.getPrestadoresSocorro());

        carregarClientes();

        preparaDialogPrestadores();

        redirecionarComMensagem("dadosusuario.xhtml", "Sucesso!", "Prestador de Socorro desassociado com sucesso.");

        return null;
    }

    /**
     * Salva a associacao dos prestadores de socorro escolhidoss.
     */
    public void associarPrestador() {
        listaPrestadoresSelecionados.stream().forEach((p) -> {
            if (p.getUsuarios() != null) {
                p.getUsuarios().add(usuarioSelecionado);
            } else {
                p.setUsuarios(new HashSet<>());
                p.getUsuarios().add(usuarioSelecionado);
            }
        });

        usuarioSelecionado.getPrestadoresSocorro().addAll(listaPrestadoresSelecionados);

        try {
            Usuario.atualizar(usuarioSelecionado);
        } catch (Exception e) {
            e.printStackTrace();
        }

        listaPrestadoresExibicao = new ArrayList<>(usuarioSelecionado.getPrestadoresSocorro());

        preparaDialogPrestadores();

        if (!prestadorNovo) {
            exibirMensagem("Prestadores de Socorro associados com sucesso.");
        }
    }

    /**
     * Prepara as tabelas do dialog de associação dos prestadores de socorro.
     */
    public synchronized void preparaDialogPrestadores() {
        List<PrestadorSocorro> listaPrestadorAssociados = new ArrayList<>();
        listaPrestadorAssociados.addAll(usuarioSelecionado.getPrestadoresSocorro());

        List<PrestadorSocorro> listaPrestadores = new ArrayList<>();

        try {
            listaPrestadores = PrestadorSocorro.obterPrestadores();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao obter prestadores de socorro");
        }

        for (PrestadorSocorro p : listaPrestadores) {
            if (listaPrestadorAssociados.contains(p)) {
                listaPrestadores.remove(p);
            }
        }

        listaPrestadoresDisponiveis.addAll(listaPrestadores);
        listaPrestadoresSelecionados = new ArrayList<>();
        listaPrestadoresSelecionados.addAll(listaPrestadorAssociados);
        
        dualListPrestador = new DualListModel<PrestadorSocorro>(listaPrestadoresDisponiveis, listaPrestadoresSelecionados);
    }

    //-------------------------Drop and Drag (Não utilizado no momento)
    /**
     * Evento de drag and drop da tabela de associação das pulseiras com o
     * usuário.
     *
     * @param ddEvent
     */
    public void onPulseiraDrop(DragDropEvent ddEvent) {
        Pulseira pulseira = ((Pulseira) ddEvent.getData());

        listaPulseirasSelecionadas.add(pulseira);
        listaPulseirasDisponiveis.remove(pulseira);
    }

    /**
     * Evento de drag and drop da tabela de associação dos prestadores de
     * socorro com o usuário.
     *
     * @param ddEvent
     */
    public void onPrestadorDrop(DragDropEvent ddEvent) {
        PrestadorSocorro prestador = ((PrestadorSocorro) ddEvent.getData());

        listaPrestadoresSelecionados.add(prestador);
        listaPrestadoresDisponiveis.remove(prestador);
    }

    private void exibirMensagem(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Sucesso", mensagem));
    }

    private void redirecionar(String pagina) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();

            context.getExternalContext().redirect(pagina);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao redirecionar para página " + pagina);
        }
    }

    private void redirecionarComMensagem(String pagina, String mensagem, String titulo) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage(titulo, mensagem));

            context.getExternalContext().getFlash().setKeepMessages(true);

            context.getExternalContext().redirect(pagina);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao redirecionar para página " + pagina);
        }
    }
    
    
    public DualListModel<PrestadorSocorro> getDualListPrestador() {
        return dualListPrestador;
    }
 
    public void setDualListPrestador(DualListModel<PrestadorSocorro> dualListPrestador) {
        this.dualListPrestador = dualListPrestador;
    }
}
