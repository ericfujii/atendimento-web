package br.com.ericfujii.entidade.retorno;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import br.com.ericfujii.entidade.Mensagem;

public class MobileRetornoVerificarMensagens extends MobileRetorno {
	@XmlElement(name = "mensagens")
	private List<Mensagem> mensagens;

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}
}
