package br.com.ericfujii.entidade.envio;

import javax.xml.bind.annotation.XmlElement;

import br.com.ericfujii.entidade.Produto;

public class MobileEnvioFila extends MobileEnvio {

    @XmlElement(name = "produto")
    private Produto produto;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
