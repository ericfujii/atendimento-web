package br.com.ericfujii.entidade.envio;

import javax.xml.bind.annotation.XmlElement;

import br.com.ericfujii.entidade.Mensagem;

public class MobileEnvioMensagem extends MobileEnvio {

	@XmlElement(name = "mensagem")
	private Mensagem mensagem;

	public Mensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}
	
	
}
