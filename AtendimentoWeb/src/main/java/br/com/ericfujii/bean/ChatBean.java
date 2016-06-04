package br.com.ericfujii.bean;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.com.ericfujii.entidade.Mensagem;
import br.com.ericfujii.entidade.Usuario;
import br.com.ericfujii.servico.MensagemServico;
import br.com.ericfujii.servico.UsuarioServico;
import br.com.ericfujii.util.DataUtil;


@ViewScoped
@ManagedBean
public class ChatBean {

	@EJB
	private UsuarioServico usuarioServico;
	@EJB
	private MensagemServico mensagemServico;
	
	private Usuario usuarioBalcao;
	private String textoMensagem;
	private List<SelectItem> destinatariosDisponiveis;
	private List<Integer> destinatariosSelecionados;

	private Map<String, List<Mensagem>> mensagens;

	private void makeMessage(Severity severity, String message, String title) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, title));
	}

	@PostConstruct
	public void postConstruct(){
		//Preenche lista de destinatarios
		try{
			List<Usuario> destinatarios = usuarioServico.obterTodos();

			destinatariosDisponiveis = new ArrayList<SelectItem>();
			for(Usuario u : destinatarios){
				destinatariosDisponiveis.add(new SelectItem(u.getId(), u.getNome()));		
			}

			//Pega o usuario balcao
			usuarioBalcao = usuarioServico.obterPorId(1);
			listaMensagens();
		}finally{
			this.textoMensagem = null;
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
			try{			
				Calendar dataMensagem = Calendar.getInstance();
				for(Integer u : destinatariosSelecionados){
					Mensagem mensagem = new Mensagem();
					mensagem.setMensagem(textoMensagem);
					mensagem.setDataMensagem(dataMensagem);
					mensagem.setRemetente(usuarioBalcao);
					mensagem.setDestinatario(new Usuario(u));
					mensagemServico.salvar(mensagem);
				}	
			}catch(Exception ex){
				makeMessage(FacesMessage.SEVERITY_ERROR, "Erro ao enviar mensagem!", "");
			}finally{
				this.textoMensagem = null;
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
	
	public void listaMensagens() {
		List<Mensagem> mensagemList = mensagemServico.obterPorRemetenteDestinatario(usuarioBalcao, usuarioBalcao);
		if(mensagemList!= null && !mensagemList.isEmpty()){
			mensagens = new TreeMap<String, List<Mensagem>>();
			for(Mensagem m : mensagemList){
				if(!mensagens.containsKey(DataUtil.formatarData(m.getDataMensagem()))){
					mensagens.put(DataUtil.formatarData(m.getDataMensagem()), new ArrayList<Mensagem>());
				}
				mensagens.get(DataUtil.formatarData(m.getDataMensagem())).add(m);
			}
			
		}
	}

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

	public Map<String, List<Mensagem>> getMensagens() {
		return mensagens;
	}

	public void setMensagens(Map<String, List<Mensagem>> mensagens) {
		this.mensagens = mensagens;
	}

}
