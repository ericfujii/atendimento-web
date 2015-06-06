package br.com.ericfujii.entidade;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement(name = "id")
	private Integer id;
	
	@XmlElement(name = "nome")
	private String nome;
	
	@XmlElement(name = "login")
	private String login;
	
	@XmlElement(name = "senha")
	private String senha;
	
	@Enumerated(EnumType.STRING)
	@XmlElement(name = "situacao")
	private ESituacao situacao = ESituacao.ATIVO;
	
	@Transient
	@XmlTransient
	private String confirmacaoSenha;
	
	public Usuario(Integer id){
		this.id = id;
	}
	
	public Usuario(){}
	
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
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}
	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}
	public ESituacao getSituacao() {
		return situacao;
	}
	public void setSituacao(ESituacao situacao) {
		this.situacao = situacao;
	}
}
