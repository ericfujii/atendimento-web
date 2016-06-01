package br.com.ericfujii.rest;

public class RestServico {
	
	private StringBuilder selectProdutos = new StringBuilder();
	{
		selectProdutos.append("FROM Produto p ");
		selectProdutos.append("JOIN FETCH p.produtoTipo pt ");
		selectProdutos.append("ORDER BY p.ordem, p.id ");
	}
	
	private StringBuilder selectProdutosTipos = new StringBuilder();
	{
		selectProdutosTipos.append("FROM ProdutoTipo pt ");
	}
	
	private StringBuilder selectUsuarios = new StringBuilder();
	{
		selectUsuarios.append("FROM Usuario u ");
	}
	
	private StringBuilder selectPedidos = new StringBuilder();
	{
		selectUsuarios.append("FROM Pedido p ");
	}
	
	private StringBuilder selectItensPedidos = new StringBuilder();
	{
		selectUsuarios.append("FROM ItemPedido ip ");
	}

	/*@SuppressWarnings("unchecked")
	public List<ProdutoTipo> obterProdutosTipos() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.createQuery(selectProdutosTipos.toString()).list();
	}

	@SuppressWarnings("unchecked")
	public List<Produto> obterProdutos() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.createQuery(selectProdutos.toString()).list();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> obterUsuarios() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.createQuery(selectUsuarios.toString()).list();
	}

	@SuppressWarnings("unchecked")
	public List<Pedido> obterPedidos() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.createQuery(selectPedidos.toString()).list();
	}

	@SuppressWarnings("unchecked")
	public List<ItemPedido> obterItensPedidos() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.createQuery(selectItensPedidos.toString()).list();
	}*/
}
