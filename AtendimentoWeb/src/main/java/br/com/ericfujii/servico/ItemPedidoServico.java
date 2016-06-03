package br.com.ericfujii.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.ericfujii.dao.ItemPedidoDAO;
import br.com.ericfujii.entidade.ItemPedido;
import br.com.ericfujii.entidade.Produto;

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

	public List<ItemPedido> obterFilaProduto(Produto produto) {
		return itemPedidoDAO.consultarFilaProduto(produto);
	}
}
