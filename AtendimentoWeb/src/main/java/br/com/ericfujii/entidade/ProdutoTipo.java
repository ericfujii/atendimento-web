package br.com.ericfujii.entidade;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="produto_tipo")
public class ProdutoTipo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement(name="id")
	private Integer id;
	
	@XmlElement(name="nome")
	private String nome;
	
	@XmlElement(name="bebida")
	private Boolean bebida;
	
	@Enumerated(EnumType.STRING)
	@XmlElement(name="situacao")
	private ESituacao situacao = ESituacao.ATIVO;
	
	@XmlTransient
	@OneToMany(mappedBy = "produtoTipo", fetch = FetchType.LAZY)
	private List<Produto> produtos;
	
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
	public ESituacao getSituacao() {
		return situacao;
	}
	public void setSituacao(ESituacao situacao) {
		this.situacao = situacao;
	}
	public List<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	public Boolean getBebida() {
		return bebida;
	}
	public void setBebida(Boolean bebida) {
		this.bebida = bebida;
	}
}
