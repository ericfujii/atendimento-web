package br.com.ericfujii.dao;

import java.io.Serializable;
import java.util.List;

public interface InterfaceDAO<T> extends Serializable {

	T salvar(final T t);

	T alterar(final T t);

	void deletar(final T t, final Integer id);

	List<T> consultarTodos();

	List<T> consultarTodos(String nomeColuna);

	int consultarQuantidade();

	T consultarPorId(final int id);
}