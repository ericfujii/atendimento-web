package br.com.ericfujii.dao;

import br.com.ericfujii.entidade.Usuario;

public class UsuarioDAO extends BaseDAO<Usuario> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioDAO() {
		super(Usuario.class);
	}
}
