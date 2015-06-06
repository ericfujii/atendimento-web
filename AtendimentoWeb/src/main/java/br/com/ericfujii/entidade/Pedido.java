package br.com.ericfujii.entidade;

import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "pedido")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement(name = "id")
	private Integer id;
	
	@XmlElement(name = "cliente")
	private String cliente;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pedido")
	@XmlElement(name = "tipo_pedido")
	private ETipoPedido tipoPedido;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "_usuario")
	@XmlElement(name = "usuario")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@XmlTransient
	private List<ItemPedido> pedidos;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public ETipoPedido getTipoPedido() {
		return tipoPedido;
	}
	public void setTipoPedido(ETipoPedido tipoPedido) {
		this.tipoPedido = tipoPedido;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<ItemPedido> getPedidos() {
		return pedidos;
	}
	public void setPedidos(List<ItemPedido> pedidos) {
		this.pedidos = pedidos;
	}
	
}
