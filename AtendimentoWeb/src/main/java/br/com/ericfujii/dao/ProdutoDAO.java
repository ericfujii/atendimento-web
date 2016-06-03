package br.com.ericfujii.dao;

import java.util.List;

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
		sql.append("JOIN FETCH p.produtoTipo pt ");
		sql.append("WHERE pt.bebida = false ORDER BY p.ordem, ped.dataHoraCadatro ASC ");
		
		return getEm().createQuery(sql.toString(), Produto.class)
		.getResultList();
	}
}