package br.com.ericfujii.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Session;

import br.com.ericfujii.entidade.ESituacao;
import br.com.ericfujii.entidade.Produto;
import br.com.ericfujii.entidade.Usuario;
import br.com.ericfujii.hibernate.HibernateUtil;

@ViewScoped
@ManagedBean
public class UsuarioCadastroBean {

	private Usuario usuario;
	private List<Usuario> usuarios;
	
	private StringBuilder selectUsuarios = new StringBuilder();
	{
		selectUsuarios.append("FROM Usuario u ");
		selectUsuarios.append("ORDER BY u.nome ");
	}
	
	@PostConstruct
	public void inicializar() {
		construirUsuario();
		carregarUsuarios();
	}
	
	private void construirUsuario() {
		this.usuario = new Usuario();
	}
	
	@SuppressWarnings("unchecked")
	private void carregarUsuarios() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		usuarios = (List<Usuario>) session
				.createQuery(selectUsuarios.toString())
				.list();
		session.close();
	}

	public void salvar() {
		
		if (!validarCampos()) {
			return;
		}
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        if (usuario.getId() == null) {
        	session.save(usuario);
        	makeMessage(FacesMessage.SEVERITY_INFO, "Usuário " 
        											+ usuario.getNome() 
        											+ " cadastrado com sucesso!", "");
        } else {
        	session.update(usuario);
        	makeMessage(FacesMessage.SEVERITY_INFO, "Usuário " 
        											+ usuario.getNome() 
        											+ " editado com sucesso!", "");
        }
        session.getTransaction().commit();
        
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
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(usuario);
    	makeMessage(FacesMessage.SEVERITY_INFO, message, "");
    	session.getTransaction().commit();
    	session.close();
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
