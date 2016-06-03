package br.com.ericfujii.entidade;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="item_pedido")
public class ItemPedido implements Comparable<ItemPedido> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement(name = "id")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_produto")
	@XmlElement(name = "produto")
	private Produto produto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_pedido")
	@XmlElement(name = "pedido")
	private Pedido pedido;
	
	@XmlElement(name = "quantidade_mesa")
	private Integer quantidadeMesa = 0;
	
	@XmlElement(name = "viagem")
	private Boolean viagem = false;
	
	@XmlElement(name = "quantidadeViagem")
	private Integer quantidadeViagem = 0;
	
	@XmlElement(name = "observacao")
	private String observacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "situacao_pedido")
	@XmlElement(name = "situacao_pedido")
	private ESituacaoPedido situacaoPedido;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_hora_ultima_situacao")
	@XmlElement(name = "data_hora_ultima_situacao")
	private Calendar dataHotaUltimaSituacao;
	
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
	public ESituacaoPedido getSituacaoPedido() {
		return situacaoPedido;
	}
	public void setSituacaoPedido(ESituacaoPedido situacaoPedido) {
		this.situacaoPedido = situacaoPedido;
	}
	public Calendar getDataHotaUltimaSituacao() {
		return dataHotaUltimaSituacao;
	}
	public void setDataHotaUltimaSituacao(Calendar dataHotaUltimaSituacao) {
		this.dataHotaUltimaSituacao = dataHotaUltimaSituacao;
	}
	
	@Override
	public int compareTo(ItemPedido o) {
		if (this.getPedido().getDataHoraCadatro().before(o.getPedido().getDataHoraCadatro())) {
			return -1;
		} else if(this.getPedido().getDataHoraCadatro().after(o.getPedido().getDataHoraCadatro())) {
			return 1;
		}
		return 0;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
