package br.com.ericfujii.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.hibernate.Session;

import br.com.ericfujii.entidade.ESituacaoPedido;
import br.com.ericfujii.entidade.ItemPedido;
import br.com.ericfujii.entidade.Produto;
import br.com.ericfujii.hibernate.HibernateUtil;

@ViewScoped
@ManagedBean
public class ComidaListagemBean {

	private List<Produto> produtos;
	private ESituacaoPedido[] situacoes = ESituacaoPedido.values();
	
	@PostConstruct
	public void postConstruct() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		produtos = (List<Produto>) session.createQuery("FROM Produto p JOIN FETCH p.produtoTipo pt WHERE pt.bebida = false ORDER BY p.nome ").list();
	}
	
	public void atualizarItem(Integer idProduto, Integer idItem) {
		for (Produto produto : produtos) {
			if (produto.getId().equals(idProduto)) {
				for (ItemPedido itemPedido : produto.getItensPedidos()) {
					if (itemPedido.getId().equals(idItem)) {
						Session session = HibernateUtil.getSessionFactory().openSession();
				        session.beginTransaction();
			        	session.update(itemPedido);
			        	session.getTransaction().commit();
			        	session.close();
			        	break;
					}
				}
				break;
			}
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
	
}
