package br.com.ericfujii.entidade.retorno;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import br.com.ericfujii.entidade.ItemPedido;

public class MobileRetornoFila extends MobileRetorno {

    @XmlElement(name = "itens_pedidos")
    private List<ItemPedido> itensPedidos;

    public List<ItemPedido> getItensPedidos() {
        return itensPedidos;
    }
    public void setItensPedidos(List<ItemPedido> itensPedidos) {
        this.itensPedidos = itensPedidos;
    }
}
