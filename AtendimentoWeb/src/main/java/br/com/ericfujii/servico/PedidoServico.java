package br.com.ericfujii.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.ericfujii.dao.PedidoDAO;
import br.com.ericfujii.entidade.Pedido;

@Stateless
public class PedidoServico extends BaseServico<Pedido> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private PedidoDAO pedidoDAO;
	
	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(pedidoDAO);
	}
	
	public List<Pedido> obterPedidosBebida() {
		return pedidoDAO.consultarPedidosBebidas();
	}
}
