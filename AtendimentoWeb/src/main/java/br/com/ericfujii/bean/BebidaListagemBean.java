package br.com.ericfujii.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ericfujii.entidade.ESituacaoPedido;
import br.com.ericfujii.entidade.ItemPedido;
import br.com.ericfujii.entidade.Pedido;
import br.com.ericfujii.servico.ItemPedidoServico;
import br.com.ericfujii.servico.PedidoServico;

@ViewScoped
@ManagedBean
public class BebidaListagemBean {

	@EJB
	private ItemPedidoServico itemPedidoServico;
	@EJB
	private PedidoServico pedidoServico;
	
	private List<Pedido> pedidos;
	private ESituacaoPedido[] situacoes = ESituacaoPedido.values();
	private boolean editar = false;
	private ItemPedido itemPedidoEdicao;
	private String clienteEdicao;
	private Integer quantidadeLocalEdicao;
	private Integer quantidadeViagemEdicao;
	private String observacaoEdicao;
	
	@PostConstruct
	public void postConstruct() {
		atualizarTela();
	}
	
	public void confirmarEdicao() {
		itemPedidoEdicao.getPedido().setCliente(clienteEdicao);
		itemPedidoEdicao.setQuantidadeMesa(quantidadeLocalEdicao);
		itemPedidoEdicao.setQuantidadeViagem(quantidadeViagemEdicao);
		itemPedidoEdicao.setViagem(itemPedidoEdicao.getQuantidadeViagem() > 0);
		itemPedidoEdicao.setObservacao(observacaoEdicao);
		
		editar = false;
		
		itemPedidoServico.alterar(itemPedidoEdicao);
    	atualizarTela();
	}
	
	public void enviarTodos(Integer idPedido) {
		List<ItemPedido> itens = itemPedidoServico.obterFilaPedido(new Pedido(idPedido));
		for (ItemPedido itemPedido : itens) {
			if (itemPedido.getProduto().getProdutoTipo().getBebida()) {
				itemPedido.setSituacaoPedido(ESituacaoPedido.FINALIZADO);
				itemPedidoServico.alterar(itemPedido);
			}
		}
		atualizarTela();
	}
	
	public void cancelarEdicao() {
		editar = false;
		itemPedidoEdicao = null;
	}
	
	public void atualizarItem(Integer idPedido, Integer idItem) {
		for (Pedido pedido : pedidos) {
			if (pedido.getId().equals(idPedido)) {
				for (ItemPedido itemPedido : pedido.getPedidos()) {
					if (itemPedido.getId().equals(idItem)) {
						itemPedidoServico.alterar(itemPedido);
			        	
			        	if (itemPedido.getSituacaoPedido() == ESituacaoPedido.EDITAR) {
			        		itemPedidoEdicao = itemPedido;
			        		quantidadeLocalEdicao = itemPedido.getQuantidadeMesa();
			        		quantidadeViagemEdicao = itemPedido.getQuantidadeViagem();
			        		clienteEdicao = itemPedido.getPedido().getCliente();
			        		editar = true;
			        	}
			        	
			        	break;
					}
				}
				break;
			}
		}
		atualizarTela();
	}
	
	public void atualizarTela() {
		pedidos = new ArrayList<Pedido>();
		List<Pedido> pedidosTotal = pedidoServico.obterPedidosBebida("ASC");
		
		for (Pedido pedido : pedidosTotal) {
			Pedido pedidoTemp = new Pedido();
			pedidoTemp.setPedidos(new ArrayList<ItemPedido>());
			List<ItemPedido> itens = pedido.getPedidos();
			for (ItemPedido itemPedido : itens) {
				if (itemPedido.getProduto().getProdutoTipo().getBebida() 
						&& itemPedido.getSituacaoPedido() != ESituacaoPedido.ENVIADO
						&& itemPedido.getSituacaoPedido() != ESituacaoPedido.FINALIZADO) {
					pedidoTemp.getPedidos().add(itemPedido);
				}
			}
			
			if (pedidoTemp.getPedidos().size() > 0) {
				pedidoTemp.setCliente(pedido.getCliente());
				pedidoTemp.setId(pedido.getId());
				pedidos.add(pedidoTemp);
			}
		}
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public ESituacaoPedido[] getSituacoes() {
		return situacoes;
	}

	public void setSituacoes(ESituacaoPedido[] situacoes) {
		this.situacoes = situacoes;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public Integer getQuantidadeLocalEdicao() {
		return quantidadeLocalEdicao;
	}

	public void setQuantidadeLocalEdicao(Integer quantidadeLocalEdicao) {
		this.quantidadeLocalEdicao = quantidadeLocalEdicao;
	}

	public Integer getQuantidadeViagemEdicao() {
		return quantidadeViagemEdicao;
	}

	public void setQuantidadeViagemEdicao(Integer quantidadeViagemEdicao) {
		this.quantidadeViagemEdicao = quantidadeViagemEdicao;
	}

	public String getClienteEdicao() {
		return clienteEdicao;
	}

	public void setClienteEdicao(String clienteEdicao) {
		this.clienteEdicao = clienteEdicao;
	}

	public String getObservacaoEdicao() {
		return observacaoEdicao;
	}

	public void setObservacaoEdicao(String observacaoEdicao) {
		this.observacaoEdicao = observacaoEdicao;
	}

}
