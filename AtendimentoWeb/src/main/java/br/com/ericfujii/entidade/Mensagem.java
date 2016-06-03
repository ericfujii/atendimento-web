package br.com.ericfujii.entidade;


import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="mensagem")
public class Mensagem{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String mensagem;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataMensagem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_remetente")
	private Usuario remetente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_destinatario")
	private Usuario destinatario;

	/* *************************************************************** */
	public Mensagem(){

	}

	public Mensagem(Integer id){
		this.id = id;
	}
	
	
	/* *************************************************************** */
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

	public Date getDataMensagem() {
		return dataMensagem;
	}

	public void setDataMensagem(Date dataMensagem) {
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
}
