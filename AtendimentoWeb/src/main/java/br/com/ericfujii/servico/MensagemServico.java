package br.com.ericfujii.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.ericfujii.dao.MensagemDAO;
import br.com.ericfujii.entidade.Mensagem;
import br.com.ericfujii.entidade.Usuario;

@Stateless
public class MensagemServico extends BaseServico<Mensagem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private MensagemDAO mensagemDAO;
	
	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(mensagemDAO);
	}
	
	public List<Mensagem> obterPorRemetenteDestinatario(Usuario remetente, Usuario destinatario) {
		return mensagemDAO.consultarPorRemetenteDestinatario(remetente, destinatario);
	}
	
	public List<Mensagem> obterPorDestinatario(Usuario destinatario) {
		return mensagemDAO.consultarPorDestinatario(destinatario);
	}
}
