package br.com.ericfujii.entidade.envio;

import javax.xml.bind.annotation.XmlElement;

import br.com.ericfujii.entidade.Pedido;

public class MobileEnvioPedido extends MobileEnvio {

    @XmlElement(name = "pedido")
    private Pedido pedido;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
