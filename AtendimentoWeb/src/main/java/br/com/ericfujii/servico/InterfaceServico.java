package br.com.ericfujii.servico;

import java.io.Serializable;
import java.util.List;

public interface InterfaceServico<T> extends Serializable {

	T salvar(T t);
	
	T alterar(T t);
	
	void deletar(T t, Integer id);

	List<T> obterTodos();
	
	List<T> obterTodos(String nomeColuna);
	
	T obterPorId(Integer id);
	
	int obterQuantidade();
}