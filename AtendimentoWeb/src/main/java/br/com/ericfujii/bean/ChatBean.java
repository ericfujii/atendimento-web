package br.com.ericfujii.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.ericfujii.entidade.Mensagem;
import br.com.ericfujii.entidade.Usuario;
import br.com.ericfujii.hibernate.HibernateUtil;


@ViewScoped
@ManagedBean
public class ChatBean {

	private Usuario usuarioBalcao;
	private List<SelectItem> destinatariosDisponiveis;

	private Mensagem mensagem;
	private List<Mensagem> mensagens;

	private void makeMessage(Severity severity, String message, String title) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, title));
	}

	@PostConstruct
	public void postConstruct(){
		//Preenche lista de destinatarios
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("FROM Usuario u WHERE u.id != 1");
		List<Usuario> destinatarios =  q.list();

		destinatariosDisponiveis = new ArrayList<SelectItem>();
		for(Usuario u : destinatarios){
			destinatariosDisponiveis.add(new SelectItem(u.getId(), u.getNome()));		
		}

		//Pega o usuario balcao
		q = session.createQuery("FROM Usuario u WHERE u.id = 1");
		usuarioBalcao = (Usuario) q.uniqueResult();

		//Instancia nova mensagem
		mensagem = new Mensagem();
	}

	public boolean validaMensagem(){
		boolean retorno = true;
		if(mensagem.getDataMensagem() == null || mensagem.getMensagem().isEmpty()){
			//TODO : mensagem erro
			retorno = false;
		}
		if(mensagem.getDestinatarios() == null || mensagem.getDestinatarios().isEmpty()){
			//TODO: mensagem erro
			retorno = false;
		}
		return retorno;
	}

	public void enviaMensagem(){
		try{
			if(validaMensagem()){
				mensagem.setDataMensagem(new Date(System.currentTimeMillis()));
				mensagem.setRemetente(usuarioBalcao);
				Session session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
				session.persist(mensagem);
				session.getTransaction().commit();
			}
		}catch(Exception ex){
			makeMessage(FacesMessage.SEVERITY_ERROR, "Erro ao enviar mensagem!", "");
		}finally{
			this.mensagem = new Mensagem();
		}
	}
	/* ***************** Getters & Setters *************************/

	public Usuario getUsuarioBalcao() {
		return usuarioBalcao;
	}

	public void setUsuarioBalcao(Usuario usuarioBalcao) {
		this.usuarioBalcao = usuarioBalcao;
	}

	public List<SelectItem> getDestinatariosDisponiveis() {
		return destinatariosDisponiveis;
	}

	public void setDestinatariosDisponiveis(
			List<SelectItem> destinatariosDisponiveis) {
		this.destinatariosDisponiveis = destinatariosDisponiveis;
	}

	public Mensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}
	
	
	
}
