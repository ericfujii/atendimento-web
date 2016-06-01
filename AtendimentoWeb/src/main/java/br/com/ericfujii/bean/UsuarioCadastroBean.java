package br.com.ericfujii.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.ericfujii.entidade.ESituacao;
import br.com.ericfujii.entidade.Usuario;
import br.com.ericfujii.servico.UsuarioServico;

@ViewScoped
@ManagedBean
public class UsuarioCadastroBean {
	
	@EJB
	private UsuarioServico usuarioServico;

	private Usuario usuario;
	private List<Usuario> usuarios;
	
	private StringBuilder selectUsuarios = new StringBuilder();
	{
		selectUsuarios.append("FROM Usuario u ");
		selectUsuarios.append("ORDER BY u.nome, u.id ");
	}
	
	@PostConstruct
	public void inicializar() {
		construirUsuario();
		carregarUsuarios();
	}
	
	public void construirUsuario() {
		this.usuario = new Usuario();
	}
	
	private void carregarUsuarios() {
		usuarios = usuarioServico.obterTodos();
	}

	public void salvar() {
		
		if (!validarCampos()) {
			return;
		}
		
        if (usuario.getId() == null) {
        	usuarioServico.salvar(usuario);
        	makeMessage(FacesMessage.SEVERITY_INFO, "Usu�rio " 
        											+ usuario.getNome() 
        											+ " cadastrado com sucesso!", "");
        } else {
        	usuarioServico.alterar(usuario);
        	makeMessage(FacesMessage.SEVERITY_INFO, "Usu�rio " 
        											+ usuario.getNome() 
        											+ " editado com sucesso!", "");
        }
        
        construirUsuario();
        carregarUsuarios();
	}
	
	private boolean validarCampos() {
		if ((usuario.getNome() == null) 
				|| (usuario.getNome().trim().isEmpty())) {
			makeMessage(FacesMessage.SEVERITY_WARN, "Informe o nome!", "");
			return false;
		}
		
		if ((usuario.getLogin() == null) 
				|| (usuario.getLogin().trim().isEmpty())) {
			makeMessage(FacesMessage.SEVERITY_WARN, "Informe o login!", "");
			return false;
		}
		
		if ((usuario.getSenha() == null) 
				|| (usuario.getSenha().trim().isEmpty())) {
			makeMessage(FacesMessage.SEVERITY_WARN, "Informe a senha!", "");
			return false;
		}
		
		if ((usuario.getConfirmacaoSenha() == null) 
				|| (usuario.getConfirmacaoSenha().trim().isEmpty())) {
			makeMessage(FacesMessage.SEVERITY_WARN, "Informe a confirmação da senha!", "");
			return false;
		}
		
		if (!usuario.getSenha().equals(usuario.getConfirmacaoSenha())) {
			makeMessage(FacesMessage.SEVERITY_WARN, "Confirmação de senha errada!", "");
			return false;
		}
		return true;
	}

	public void editar(Usuario usuarioSelecionado) {
		this.usuario = usuarioSelecionado;
	}
	
	public void inativar(Usuario usuarioSelecionado) {
		usuarioSelecionado.setSituacao(ESituacao.INATIVO);
		atualizar(usuarioSelecionado, "Usuário " 
									  + usuarioSelecionado.getId()
									  + " - "
									  + usuarioSelecionado.getNome() 
									  + " inativado com sucesso!");
    	carregarUsuarios();
	}
	
	public void reativar(Usuario usuarioSelecionado) {
		
		usuarioSelecionado.setSituacao(ESituacao.ATIVO);
		atualizar(usuarioSelecionado, "Usuário " 
									  + usuarioSelecionado.getId()
									  + " - "
									  + usuarioSelecionado.getNome() 
									  + " reativado com sucesso!");
    	carregarUsuarios();
	}
	
	private void atualizar(Usuario usuario, String message) {
		usuarioServico.alterar(usuario);
    	makeMessage(FacesMessage.SEVERITY_INFO, message, "");
	}
	
	private void makeMessage(Severity severity, String message, String title) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, title));
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
