package br.com.ericfujii.dao;

import java.util.List;

import br.com.ericfujii.entidade.ProdutoTipo;

public class ProdutoTipoDAO extends BaseDAO<ProdutoTipo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProdutoTipoDAO() {
		super(ProdutoTipo.class);
	}
	
	public List<ProdutoTipo> consultarTodosCompleto() {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT pt ");
		sql.append("FROM ProdutoTipo pt ");
		sql.append("JOIN FETCH pt.produtos prod ");
		sql.append("ORDER BY pt.ordem, prod.ordem ASC ");
		
		return getEm().createQuery(sql.toString(), ProdutoTipo.class)
			.getResultList();
	}
}
