package br.com.ericfujii.entidade;


import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="mensagem")
public class Mensagem{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement(name = "id")
	private Integer id;

	@XmlElement(name = "mensagem")
	private String mensagem;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_mensagem")
	@XmlElement(name = "data_mensagem")
	private Calendar dataMensagem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_remetente")
	@XmlElement(name = "remetente")
	private Usuario remetente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_destinatario")
	@XmlElement(name = "destinatario")
	private Usuario destinatario;
	
	@XmlTransient
	private Boolean enviada = false;

	public Mensagem(){
	}

	public Mensagem(Integer id){
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Calendar getDataMensagem() {
		return dataMensagem;
	}

	public void setDataMensagem(Calendar dataMensagem) {
		this.dataMensagem = dataMensagem;
	}

	public Usuario getRemetente() {
		return remetente;
	}

	public void setRemetente(Usuario remetente) {
		this.remetente = remetente;
	}

	public Usuario getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
	}

	public Boolean getEnviada() {
		return enviada;
	}

	public void setEnviada(Boolean enviada) {
		this.enviada = enviada;
	}
	
}
