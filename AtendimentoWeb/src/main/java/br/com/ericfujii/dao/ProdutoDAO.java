package br.com.ericfujii.dao;

import java.util.Calendar;
import java.util.List;

import br.com.ericfujii.entidade.ESituacaoPedido;
import br.com.ericfujii.entidade.Produto;
import br.com.ericfujii.entidade.ProdutoTipo;

public class ProdutoDAO extends BaseDAO<Produto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProdutoDAO() {
		super(Produto.class);
	}
	
	public List<Produto> consultarPorTipo(ProdutoTipo produtoTipo) {
		StringBuilder sql = new StringBuilder("SELECT p ");
		sql.append("FROM Produto p ");
		sql.append("WHERE p.produtoTipo =:_produtoTipo ");
		
		return getEm().createQuery(sql.toString(), Produto.class)
		.setParameter("_produtoTipo", produtoTipo)
		.getResultList();
	}
	
	public List<Produto> consultarComidas() {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT p ");
		sql.append("FROM Produto p ");
		sql.append("JOIN FETCH p.itensPedidos ip ");
		sql.append("JOIN FETCH ip.pedido ped ");
		sql.append("LEFT JOIN FETCH ped.usuario us ");
		sql.append("JOIN FETCH p.produtoTipo pt ");
		sql.append("WHERE pt.bebida = false AND ip.situacaoPedido !=:_situacao ORDER BY p.ordem, ped.dataHoraCadatro ASC ");
		
		return getEm().createQuery(sql.toString(), Produto.class)
				.setParameter("_situacao", ESituacaoPedido.FINALIZADO)
				.getResultList();
	}
	
	public List<Produto> consultarHistoricoComidas(Calendar periodo) {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT p ");
		sql.append("FROM Produto p ");
		sql.append("JOIN FETCH p.itensPedidos ip ");
		sql.append("JOIN FETCH ip.pedido ped ");
		sql.append("LEFT JOIN FETCH ped.usuario us ");
		sql.append("JOIN FETCH p.produtoTipo pt ");
		sql.append("WHERE pt.bebida = false ");
		sql.append("AND ped.dataHoraCadatro BETWEEN :_inicio AND :_fim ");
		sql.append("ORDER BY p.ordem, ped.dataHoraCadatro DESC ");
		
		return getEm()
				.createQuery(sql.toString(), Produto.class)
				.setParameter("_inicio", periodo)
				.setParameter("_fim", Calendar.getInstance())
				.getResultList();
	}
	
	public List<Produto> consultarTodosCompleto() {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT p ");
		sql.append("FROM Produto p ");
		sql.append("JOIN FETCH p.produtoTipo pt ");
		sql.append("ORDER BY pt.ordem, p.ordem ");
		
		return getEm().createQuery(sql.toString(), Produto.class)
		.getResultList();
	}
}
