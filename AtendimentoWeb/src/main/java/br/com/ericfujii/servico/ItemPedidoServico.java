package br.com.ericfujii.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.ericfujii.dao.ItemPedidoDAO;
import br.com.ericfujii.entidade.ItemPedido;

@Stateless
public class ItemPedidoServico extends BaseServico<ItemPedido> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private ItemPedidoDAO itemPedidoDAO;
	
	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(itemPedidoDAO);
	}
	
}
