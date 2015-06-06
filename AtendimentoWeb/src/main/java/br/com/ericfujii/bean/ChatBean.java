package br.com.ericfujii.bean;
import br.com.ericfujii.entidade.Mensagem;
import br.com.ericfujii.entidade.Usuario;
import br.com.ericfujii.hibernate.HibernateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.Query;
import org.hibernate.Session;


@ViewScoped
@ManagedBean
public class ChatBean {

	private Usuario usuarioBalcao;
	private String textoMensagem;
	private List<SelectItem> destinatariosDisponiveis;
	private List<Integer> destinatariosSelecionados;

	private Map<Date, List<Mensagem>> mensagens;

	private void makeMessage(Severity severity, String message, String title) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, title));
	}

	@PostConstruct
	public void postConstruct(){
		//Preenche lista de destinatarios
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			Query q = session.createQuery("FROM Usuario u WHERE u.id != 1");
			List<Usuario> destinatarios =  q.list();

			destinatariosDisponiveis = new ArrayList<SelectItem>();
			for(Usuario u : destinatarios){
				destinatariosDisponiveis.add(new SelectItem(u.getId(), u.getNome()));		
			}

			//Pega o usuario balcao
			q = session.createQuery("FROM Usuario u WHERE u.id = 1");
			usuarioBalcao = (Usuario) q.uniqueResult();
		}finally{
			this.textoMensagem = null;
			session.close();
		}
	}

	public boolean validaMensagem(){
		boolean retorno = true;
		if(textoMensagem == null || textoMensagem.isEmpty()){
			//TODO : mensagem erro
			retorno = false;
		}
		if(destinatariosSelecionados == null || destinatariosSelecionados.isEmpty()){
			//TODO: mensagem erro
			retorno = false;
		}
		return retorno;
	}

	public void enviaMensagem(){
		if(validaMensagem()){
			Session session = HibernateUtil.getSessionFactory().openSession();		
			try{			
				session.beginTransaction();
				Date dataMensagem = new Date(System.currentTimeMillis());
				for(Integer u : destinatariosSelecionados){
					Mensagem mensagem = new Mensagem();
					mensagem.setMensagem(textoMensagem);
					mensagem.setDataMensagem(dataMensagem);
					mensagem.setRemetente(usuarioBalcao);
					mensagem.setDestinatario(new Usuario(u));
					session.save(mensagem);
				}	
				session.getTransaction().commit();
			}catch(Exception ex){
				makeMessage(FacesMessage.SEVERITY_ERROR, "Erro ao enviar mensagem!", "");
				session.getTransaction().rollback();
			}finally{
				this.textoMensagem = null;
				session.close();
				listaMensagens();
			}
		}
	}
	
	public String retornaDestinoMensagens(List<Mensagem> mensagemList){
		StringBuilder sb = new StringBuilder();
		for(Mensagem m: mensagemList){
			sb.append(m.getDestinatario().getNome());
			if(mensagemList.indexOf(m) == (mensagemList.size() - 1)){
				sb.append(". ");
			}else{
				sb.append(", ");
			}
		}
		return sb.toString();
	}
	
	public void listaMensagens(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			Query q = session.createQuery("FROM Mensagem m WHERE m.destinatario = :_destinatario OR m.remetente = :_remetente ORDER BY m.dataMensagem");
			q.setParameter("_destinatario", usuarioBalcao);
			q.setParameter("_remetente", usuarioBalcao);
			List<Mensagem> mensagemList = q.list();
			if(mensagemList!= null && !mensagemList.isEmpty()){
				mensagens = new TreeMap<Date, List<Mensagem>>();
				for(Mensagem m : mensagemList){
					if(!mensagens.containsKey(m.getDataMensagem())){
						mensagens.put(m.getDataMensagem(), new ArrayList<Mensagem>());
					}
					mensagens.get(m.getDataMensagem()).add(m);
				}
				
			}
		}finally{
			session.close();
		}
	}
	/* ***************** Getters & Setters *************************/

	public Usuario getUsuarioBalcao() {
		return usuarioBalcao;
	}

	public void setUsuarioBalcao(Usuario usuarioBalcao) {
		this.usuarioBalcao = usuarioBalcao;
	}

	public String getTextoMensagem() {
		return textoMensagem;
	}

	public void setTextoMensagem(String textoMensagem) {
		this.textoMensagem = textoMensagem;
	}

	public List<SelectItem> getDestinatariosDisponiveis() {
		return destinatariosDisponiveis;
	}

	public void setDestinatariosDisponiveis(
			List<SelectItem> destinatariosDisponiveis) {
		this.destinatariosDisponiveis = destinatariosDisponiveis;
	}

	public List<Integer> getDestinatariosSelecionados() {
		return destinatariosSelecionados;
	}

	public void setDestinatariosSelecionados(List<Integer> destinatariosSelecionados) {
		this.destinatariosSelecionados = destinatariosSelecionados;
	}

	public Map<Date, List<Mensagem>> getMensagens() {
		return mensagens;
	}

	public void setMensagens(Map<Date, List<Mensagem>> mensagens) {
		this.mensagens = mensagens;
	}

}
