package br.com.ericfujii.dao;

import java.util.List;

import br.com.ericfujii.entidade.Mensagem;
import br.com.ericfujii.entidade.Usuario;

public class MensagemDAO extends BaseDAO<Mensagem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MensagemDAO() {
		super(Mensagem.class);
	}
	
	public List<Mensagem> consultarPorRemetenteDestinatario(Usuario remetente, Usuario destinatario) {
		StringBuilder sql = new StringBuilder("SELECT m ");
		sql.append("FROM Mensagem m ");
		sql.append("JOIN FETCH m.remetente r ");
		sql.append("JOIN FETCH m.destinatario d ");
		sql.append("WHERE r=:_remetente ");
		sql.append("OR d=:_destinatario ");
		return getEm().createQuery(sql.toString(), Mensagem.class)
				.setParameter("_remetente", remetente)
				.setParameter("_destinatario", destinatario).getResultList();
	}
	
	public List<Mensagem> consultarPorDestinatario(Usuario destinatario) {
		StringBuilder sql = new StringBuilder("SELECT m ");
		sql.append("FROM Mensagem m ");
		sql.append("JOIN FETCH m.remetente r ");
		sql.append("JOIN FETCH m.destinatario d ");
		sql.append("WHERE m.enviada = false AND d=:_destinatario ");
		return getEm().createQuery(sql.toString(), Mensagem.class)
				.setParameter("_destinatario", destinatario).getResultList();
	}
}
