package br.com.ericfujii.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestAtendimentoRest {
	
	@XmlElement(name = "codigo_funcao")
	private ECodigoFuncao codigoFuncao;
	private String login;
	private String senha;

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

	public ECodigoFuncao getCodigoFuncao() {
		return codigoFuncao;
	}

	public void setCodigoFuncao(ECodigoFuncao codigoFuncao) {
		this.codigoFuncao = codigoFuncao;
	}
	
}
