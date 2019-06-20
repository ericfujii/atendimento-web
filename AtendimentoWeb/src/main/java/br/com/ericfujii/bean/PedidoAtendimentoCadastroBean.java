package br.com.ericfujii.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import br.com.ericfujii.entidade.ESituacaoPedido;
import br.com.ericfujii.entidade.ETipoPedido;
import br.com.ericfujii.entidade.ItemPedido;
import br.com.ericfujii.entidade.Pedido;
import br.com.ericfujii.entidade.Produto;
import br.com.ericfujii.entidade.ProdutoTipo;
import br.com.ericfujii.entidade.Usuario;
import br.com.ericfujii.servico.PedidoServico;
import br.com.ericfujii.servico.ProdutoServico;
import br.com.ericfujii.servico.ProdutoTipoServico;
import br.com.ericfujii.servico.UsuarioServico;

@ViewScoped
@ManagedBean
public class PedidoAtendimentoCadastroBean {

	@EJB
	private PedidoServico pedidoServico;
	@EJB
	private ProdutoServico produtoServico;
	@EJB
	private ProdutoTipoServico produtoTipoServico;
	@EJB
	private UsuarioServico usuarioServico;
	
	private Pedido pedido= new Pedido();
	private Integer idProduto;
	private List<ProdutoTipo> produtoTipos;
	private ItemPedido itemPedido = new ItemPedido();
	private List<SelectItem> tiposPedido = new ArrayList<SelectItem>();
	private ETipoPedido tipoPedido = ETipoPedido.MESA;
	private String labelCliente = "Nome Cliente :";
	private List<ItemPedido> itensAdicionados = new ArrayList<ItemPedido>();
	private Usuario usuarioBalcao;
	private boolean edicao = false;
	private boolean avancar = false;
	private Map<ProdutoTipo, List<Produto>> produtos;
	
	@PostConstruct
	public void postContruct() {
		produtoTipos = produtoTipoServico.obterTodosCompleto();
		usuarioBalcao = usuarioServico.obterPorId(1);
		produtos = new HashMap<>();
		for (ProdutoTipo produtoTipo : produtoTipos) {
			List<Produto> produtosDoTipo = produtoTipo.getProdutos();
			produtos.put(produtoTipo, produtosDoTipo);
		}
		
		for (ETipoPedido tipoPedido : ETipoPedido.values()) {
			SelectItem selectItem = new SelectItem(tipoPedido, tipoPedido.toString());
			tiposPedido.add(selectItem);
		}
		alterarTipo();
	}
	
	public void adicionarItem() {
		if (idProduto == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Selecione um produto!", ""));
		} else {
			Produto produto = produtoServico.obterPorId(idProduto);
			itemPedido.setProduto(produto);
			if (!edicao) {
				itensAdicionados.add(itemPedido);
			}
			itemPedido = new ItemPedido();
			edicao = false;
		}
	}
	
	public void editar(int i) {
		edicao = true;
		itemPedido = itensAdicionados.get(i);
	}
	
	public void excluir(int i) {
		itensAdicionados.remove(i);
	}
	
	public void fecharPedido() {
		if (pedido.getCliente() != null && !pedido.getCliente().trim().equals("")) {
			Calendar calendar = Calendar.getInstance();
			for (ItemPedido itemPedido : itensAdicionados) {
				itemPedido.setPedido(pedido);
				itemPedido.setDataHotaUltimaSituacao(calendar);
				itemPedido.setSituacaoPedido(ESituacaoPedido.NOVO);
			}
			
			pedido.setPedidos(itensAdicionados);
			pedido.setTipoPedido(tipoPedido);
			pedido.setDataHoraCadatro(calendar);
			pedido.setUsuario(usuarioBalcao);
			
	    	pedidoServico.salvar(pedido);
	    	
	    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pedido cadastrado com sucesso!", ""));
		
	    	pedido = new Pedido();
	    	itensAdicionados = new ArrayList<ItemPedido>();
	    	itemPedido = new ItemPedido();
	    	
	    	produtoTipos = produtoTipoServico.obterTodosCompleto();
			usuarioBalcao = usuarioServico.obterPorId(1);
			produtos = new HashMap<>();
			for (ProdutoTipo produtoTipo : produtoTipos) {
				List<Produto> produtosDoTipo = produtoTipo.getProdutos();
				produtos.put(produtoTipo, produtosDoTipo);
			}
	    	avancar = false;
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Insira um cliente!", ""));
		}
	}
	
	public void alterarTipo() {
		switch(tipoPedido) {
		case BALCAO:
			labelCliente = "Nome Cliente :";
			break;
		case MESA:
			labelCliente = "Num. Mesa :";
			break;
		default:
			break;
			
		}
	}
	
	public void avancarFormulario() {
		itensAdicionados = new ArrayList<>();
		for (ProdutoTipo produtoTipo : produtos.keySet()) {
			for (Produto produto : produtos.get(produtoTipo)) {
				if (produto.getQuantidadeLocal() > 0 || produto.getQuantidadeViagem() >0) {
					ItemPedido itemPedido = new ItemPedido();
					itemPedido.setProduto(produto);
					itemPedido.setQuantidadeMesa(produto.getQuantidadeLocal());
					itemPedido.setQuantidadeViagem(produto.getQuantidadeViagem());
					itemPedido.setViagem(produto.getQuantidadeViagem() > 0);
					itemPedido.setObservacao(produto.getObservacao());
					itensAdicionados.add(itemPedido);
				}
			}
		}
		if (itensAdicionados.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Adicione um produto!", ""));
		} else {
			avancar = true;
		}
	}
	
	public void voltar() {
		avancar = false;
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

	public List<ItemPedido> getItensAdicionados() {
		return itensAdicionados;
	}

	public void setItensAdicionados(List<ItemPedido> itensAdicionados) {
		this.itensAdicionados = itensAdicionados;
	}

	public boolean isEdicao() {
		return edicao;
	}

	public void setEdicao(boolean edicao) {
		this.edicao = edicao;
	}
	
	public boolean isAvancar() {
		return avancar;
	}
	
	public void setAvancar(boolean avancar) {
		this.avancar = avancar;
	}
	
	public Map<ProdutoTipo, List<Produto>> getProdutos() {
		return produtos;
	}
	public List<ProdutoTipo> getProdutoTipos() {
		return produtoTipos;
	}
}
