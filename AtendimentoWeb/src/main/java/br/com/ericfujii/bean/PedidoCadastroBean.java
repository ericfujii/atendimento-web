package br.com.ericfujii.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.persistence.criteria.CriteriaBuilder.Case;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.ericfujii.entidade.ETipoPedido;
import br.com.ericfujii.entidade.ItemPedido;
import br.com.ericfujii.entidade.Pedido;
import br.com.ericfujii.entidade.Produto;
import br.com.ericfujii.entidade.ProdutoTipo;
import br.com.ericfujii.hibernate.HibernateUtil;

@ViewScoped
@ManagedBean
public class PedidoCadastroBean {

	private Pedido pedido= new Pedido();
	private Integer idProduto;
	private ItemPedido itemPedido = new ItemPedido();
	private List<SelectItem> produtos = new ArrayList<SelectItem>();
	private List<SelectItem> tiposPedido = new ArrayList<SelectItem>();
	private ETipoPedido tipoPedido = ETipoPedido.BALCAO;
	private String labelCliente = "Nome Cliente :";
	
	@PostConstruct
	public void postContruct() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("From ProdutoTipo ");
        
		List<ProdutoTipo> produtoTipos = q.list();
		
		for (ProdutoTipo produtoTipo : produtoTipos) {
			SelectItemGroup group = new SelectItemGroup(produtoTipo.getNome());
			List<Produto> produtosDoTipo = produtoTipo.getProdutos();
			SelectItem[] itens = new SelectItem[produtosDoTipo.size()]; 
			for (int i = 0; i < produtosDoTipo.size(); i++) {
				SelectItem selectItem = new SelectItem(produtosDoTipo.get(i).getId(), produtosDoTipo.get(i).getNome());
				itens[i] = selectItem;
			}
			group.setSelectItems(itens);
			produtos.add(group);
		}
		
		for (ETipoPedido tipoPedido : ETipoPedido.values()) {
			SelectItem selectItem = new SelectItem(tipoPedido, tipoPedido.toString());
			tiposPedido.add(selectItem);
		}
	}
	
	public void alterarTipo() {
		switch(tipoPedido) {
		case BALCAO:
			labelCliente = "Nome Cliente :";
			break;
		case MESA:
			labelCliente = "N�m. Mesa :";
			break;
		default:
			break;
			
		}
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	public ItemPedido getItemPedido() {
		return itemPedido;
	}

	public void setItemPedido(ItemPedido itemPedido) {
		this.itemPedido = itemPedido;
	}

	public List<SelectItem> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<SelectItem> produtos) {
		this.produtos = produtos;
	}
	
	public List<SelectItem> getTiposPedido() {
		return tiposPedido;
	}

	public void setTiposPedido(List<SelectItem> tiposPedido) {
		this.tiposPedido = tiposPedido;
	}

	public ETipoPedido getTipoPedido() {
		return tipoPedido;
	}

	public void setTipoPedido(ETipoPedido tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	public String getLabelCliente() {
		return labelCliente;
	}

	public void setLabelCliente(String labelCliente) {
		this.labelCliente = labelCliente;
	}
	
}
