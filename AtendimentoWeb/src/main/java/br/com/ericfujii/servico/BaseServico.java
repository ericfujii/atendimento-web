package br.com.ericfujii.servico;

import java.util.List;

import br.com.ericfujii.dao.InterfaceDAO;

public abstract class BaseServico<T> implements InterfaceServico<T> {

	private static final long serialVersionUID = 1L;

	private InterfaceDAO<T> dao;

	protected abstract void inicializar();

	@Override
	public int obterQuantidade(){
		return dao.consultarQuantidade();
	}

	@Override
	public T salvar(T t) {
		 T f = dao.salvar(t);
		 return f;
	}

	@Override
	public T alterar(T t) {
		return dao.alterar(t);
	}

	@Override
	public void deletar(T t, Integer id) {
		dao.deletar(t, id);
	}

	@Override
	public List<T> obterTodos() {
		return dao.consultarTodos();
	}
	
	@Override
	public List<T> obterTodos(String coluna) {
		return dao.consultarTodos(coluna);
	}

	@Override
	public T obterPorId(Integer id) {
		return dao.consultarPorId(id);
	}

	protected InterfaceDAO<T> getDao() {
		return dao;
	}

	protected void setDao(InterfaceDAO<T> dao) {
		this.dao = dao;
	}
	
}