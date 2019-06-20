package br.com.ericfujii.servico;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.ericfujii.dao.ProdutoDAO;
import br.com.ericfujii.entidade.Produto;

@Stateless
public class ProdutoServico extends BaseServico<Produto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private ProdutoDAO produtoDAO;
	
	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(produtoDAO);
	}
	
	public List<Produto> obterComidas() {
		return produtoDAO.consultarComidas();
	}
	
	public List<Produto> obterHistoricoComidas() {
		Calendar periodo = Calendar.getInstance();
		periodo.add(Calendar.HOUR, -2);
		return produtoDAO.consultarHistoricoComidas(periodo);
	}
	
	public List<Produto> obterTodosCompleto() {
		return produtoDAO.consultarTodosCompleto();
	}
	
}
