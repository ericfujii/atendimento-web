package br.com.ericfujii.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.ericfujii.dao.ProdutoTipoDAO;
import br.com.ericfujii.entidade.ProdutoTipo;

@Stateless
public class ProdutoTipoServico extends BaseServico<ProdutoTipo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private ProdutoTipoDAO produtoTipoDAO;
	
	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(produtoTipoDAO);
	}
	
	
	
}
