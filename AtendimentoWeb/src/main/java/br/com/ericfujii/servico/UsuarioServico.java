package br.com.ericfujii.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.ericfujii.dao.UsuarioDAO;
import br.com.ericfujii.entidade.Usuario;

@Stateless
public class UsuarioServico extends BaseServico<Usuario> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioDAO usuarioDao;
	
	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(usuarioDao);
	}
	
}
