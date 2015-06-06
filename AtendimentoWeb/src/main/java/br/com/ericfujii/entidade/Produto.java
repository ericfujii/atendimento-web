package br.com.ericfujii.entidade;

import java.util.List;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement(name = "id")
	private Integer id;
	
	@XmlElement(name = "nome")
	private String nome;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "_produto_tipo")
	@XmlElement(name = "produto_tipo")
	private ProdutoTipo produtoTipo;
	
	@XmlElement(name = "ordem")
	private Integer ordem = 0;
	
	@Enumerated(EnumType.STRING)
	@XmlElement(name = "situacao")
	private ESituacao situacao = ESituacao.ATIVO;
	
	@OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
	@XmlTransient
	private List<ItemPedido> itensPedidos;
	
	@XmlTransient
	@Transient
	private Integer pendentesLocal;
	
	@XmlTransient
	@Transient
	private Integer pendentesViagem;
	
	@XmlTransient
	@Transient
	private Integer pendentesTotal;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public ProdutoTipo getProdutoTipo() {
		return produtoTipo;
	}
	public void setProdutoTipo(ProdutoTipo produtoTipo) {
		this.produtoTipo = produtoTipo;
	}
	public ESituacao getSituacao() {
		return situacao;
	}
	public void setSituacao(ESituacao situacao) {
		this.situacao = situacao;
	}
	public List<ItemPedido> getItensPedidos() {
		return itensPedidos;
	}
	public void setItensPedidos(List<ItemPedido> itensPedidos) {
		this.itensPedidos = itensPedidos;
	}
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	public Integer getPendentesLocal() {
		return pendentesLocal;
	}
	public void setPendentesLocal(Integer pendentesLocal) {
		this.pendentesLocal = pendentesLocal;
	}
	public Integer getPendentesViagem() {
		return pendentesViagem;
	}
	public void setPendentesViagem(Integer pendentesViagem) {
		this.pendentesViagem = pendentesViagem;
	}
	public Integer getPendentesTotal() {
		return pendentesTotal;
	}
	public void setPendentesTotal(Integer pendentesTotal) {
		this.pendentesTotal = pendentesTotal;
	}
}
