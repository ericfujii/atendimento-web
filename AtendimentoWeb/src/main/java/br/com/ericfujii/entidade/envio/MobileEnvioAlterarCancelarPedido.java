package br.com.ericfujii.entidade.envio;

import javax.xml.bind.annotation.XmlElement;

import br.com.ericfujii.entidade.EAcaoPedido;
import br.com.ericfujii.entidade.ItemPedido;

public class MobileEnvioAlterarCancelarPedido extends MobileEnvio {

	@XmlElement(name = "item_pedido")
	private ItemPedido itemPedido;
	
	@XmlElement(name = "acao_pedido")
	private EAcaoPedido acaoPedido;

	public ItemPedido getItemPedido() {
		return itemPedido;
	}

	public void setItemPedido(ItemPedido itemPedido) {
		this.itemPedido = itemPedido;
	}
	
	public EAcaoPedido getAcaoPedido() {
		return acaoPedido;
	}
	
	public void setAcaoPedido(EAcaoPedido acaoPedido) {
		this.acaoPedido = acaoPedido;
	}
}
