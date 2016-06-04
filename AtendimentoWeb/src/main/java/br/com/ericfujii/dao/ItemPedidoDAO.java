package br.com.ericfujii.dao;

import java.util.List;

import br.com.ericfujii.entidade.ESituacaoPedido;
import br.com.ericfujii.entidade.ItemPedido;
import br.com.ericfujii.entidade.Pedido;
import br.com.ericfujii.entidade.Produto;

public class ItemPedidoDAO extends BaseDAO<ItemPedido> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ItemPedidoDAO() {
		super(ItemPedido.class);
	}
	
	public List<ItemPedido> consultarFilaProduto(Produto produto) {
		StringBuilder sql = new StringBuilder("SELECT ip ");
		sql.append("FROM ItemPedido ip ");
		sql.append("JOIN FETCH ip.produto prod ");
		sql.append("JOIN FETCH ip.pedido ped ");
		sql.append("WHERE prod=:_produto ");
		sql.append("AND ip.situacaoPedido !=:_situacaoFinalizado ");
		sql.append("AND ip.situacaoPedido !=:_situacaoCancelado ");
		sql.append("ORDER BY ped.dataHoraCadatro ");
		
		return getEm().createQuery(sql.toString(), ItemPedido.class)
			.setParameter("_produto", produto)
			.setParameter("_situacaoFinalizado", ESituacaoPedido.FINALIZADO)
			.setParameter("_situacaoCancelado", ESituacaoPedido.CANCELADO)
			.getResultList();
	}
	
	public List<ItemPedido> consultarPorPedido(Pedido pedido) {
		StringBuilder sql = new StringBuilder("SELECT ip ");
		sql.append("FROM ItemPedido ip ");
		sql.append("JOIN FETCH ip.produto prod ");
		sql.append("JOIN FETCH prod.produtoTipo pt ");
		sql.append("WHERE ip.pedido=:_pedido ");
		
		return getEm().createQuery(sql.toString(), ItemPedido.class)
			.setParameter("_pedido", pedido)
			.getResultList();
	}
}
