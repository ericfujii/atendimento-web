package br.com.ericfujii.entidade;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="item_pedido")
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "_produto")
	private Produto produto;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "_pedido")
	private Pedido pedido;
	private Integer quantidadeMesa;
	private Boolean viagem;
	private Integer quantidadeViagem;
	private String observacao;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public Integer getQuantidadeMesa() {
		return quantidadeMesa;
	}
	public void setQuantidadeMesa(Integer quantidadeMesa) {
		this.quantidadeMesa = quantidadeMesa;
	}
	public Integer getQuantidadeViagem() {
		return quantidadeViagem;
	}
	public void setQuantidadeViagem(Integer quantidadeViagem) {
		this.quantidadeViagem = quantidadeViagem;
	}
	public Boolean getViagem() {
		return viagem;
	}
	public void setViagem(Boolean viagem) {
		this.viagem = viagem;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
}
