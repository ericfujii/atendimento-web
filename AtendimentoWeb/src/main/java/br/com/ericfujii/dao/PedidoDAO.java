package br.com.ericfujii.dao;

import java.util.List;

import br.com.ericfujii.entidade.Pedido;

public class PedidoDAO extends BaseDAO<Pedido> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PedidoDAO() {
		super(Pedido.class);
	}
	
	public List<Pedido> consultarPedidosBebidas() {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT p ");
		sql.append("FROM Pedido p ");
		sql.append("JOIN FETCH p.pedidos ped ");
		sql.append("JOIN FETCH ped.produto prod ");
		sql.append("JOIN FETCH prod.produtoTipo pt ");
		sql.append("WHERE pt.bebida = true ORDER BY p.dataHoraCadatro ASC ");
		
		return getEm().createQuery(sql.toString(), Pedido.class)
		.getResultList();
	}
}
