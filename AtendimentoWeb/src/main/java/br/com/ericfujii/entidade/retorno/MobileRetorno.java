package br.com.ericfujii.entidade.retorno;

import javax.xml.bind.annotation.XmlElement;

public abstract class MobileRetorno implements MobileRetornavel {

    @XmlElement(name = "mensagem")
    private String mensagem;
    @XmlElement(name = "codigo_retorno")
    private String codigoRetorno;

    @Override
    public String getMensagem() {
        return mensagem;
    }

    @Override
    public String getCodigoRetorno() {
        return codigoRetorno;
    }

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public void setCodigoRetorno(String codigoRetorno) {
		this.codigoRetorno = codigoRetorno;
	}
    
}
