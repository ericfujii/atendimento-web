package br.com.ericfujii.bean;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Session;

import br.com.ericfujii.entidade.ESituacaoPedido;
import br.com.ericfujii.entidade.ItemPedido;
import br.com.ericfujii.entidade.Produto;
import br.com.ericfujii.hibernate.HibernateUtil;

@ViewScoped
@ManagedBean
public class ComidaHistoricoBean {

	private List<Produto> produtos;
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
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
    	session.update(itemPedidoEdicao);
    	session.getTransaction().commit();
    	session.close();
    	
    	atualizarTela();
	}
	
	public void cancelarEdicao() {
		editar = false;
		itemPedidoEdicao = null;
	}
	
	public void atualizarItem(Integer idProduto, Integer idItem) {
		for (Produto produto : produtos) {
			if (produto.getId().equals(idProduto)) {
				for (ItemPedido itemPedido : produto.getItensPedidos()) {
					if (itemPedido.getId().equals(idItem)) {
						itemPedido.setDataHotaUltimaSituacao(Calendar.getInstance());
						Session session = HibernateUtil.getSessionFactory().openSession();
				        session.beginTransaction();
			        	session.update(itemPedido);
			        	session.getTransaction().commit();
			        	session.close();
			        	
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
		Session session = HibernateUtil.getSessionFactory().openSession();
		produtos = (List<Produto>) session.createQuery("FROM Produto p JOIN FETCH p.produtoTipo pt WHERE pt.bebida = false ORDER BY p.ordem ").list();
		for (Produto produto : produtos) {
			Integer contadorLocal = 0;
			Integer contadorViagem = 0;
			Integer contadorTotal = 0;
			List<ItemPedido> itens = produto.getItensPedidos();
			Collections.sort(produto.getItensPedidos());
			for (ItemPedido itemPedido : itens) {
				contadorLocal += itemPedido.getQuantidadeMesa() ;
				contadorViagem += itemPedido.getQuantidadeViagem();
				contadorTotal += (itemPedido.getQuantidadeMesa() + itemPedido.getQuantidadeViagem());
			}
			produto.setPendentesLocal(contadorLocal);
			produto.setPendentesViagem(contadorViagem);
			produto.setPendentesTotal(contadorTotal);
		}
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
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
