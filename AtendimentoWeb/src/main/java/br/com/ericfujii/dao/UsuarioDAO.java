package br.com.ericfujii.dao;

import javax.persistence.NoResultException;

import br.com.ericfujii.entidade.Usuario;

public class UsuarioDAO extends BaseDAO<Usuario> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioDAO() {
		super(Usuario.class);
	}
	
	public Usuario efetuarLogin(String login, String senha) {
		try {
			StringBuilder sql = new StringBuilder("SELECT u ");
			sql.append("FROM Usuario u ");
			sql.append("WHERE u.login=:_login ");
			sql.append("AND u.senha=:_senha ");
			return getEm().createQuery(sql.toString(), Usuario.class)
					.setParameter("_login", login)
					.setParameter("_senha", senha).getSingleResult();
		} catch(NoResultException nre) {
			return null;
		}
	}
}
