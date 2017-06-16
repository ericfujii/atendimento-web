package br.com.ericfujii.entidade.retorno;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import br.com.ericfujii.entidade.Produto;
import br.com.ericfujii.entidade.ProdutoTipo;
import br.com.ericfujii.entidade.Usuario;

public class MobileRetornoPacote extends MobileRetorno {

    @XmlElement(name = "produto_tipos")
    private List<ProdutoTipo> produtoTipos;
    @XmlElement(name = "produtos")
    private List<Produto> produtos;
    @XmlElement(name = "usuarios")
    private List<Usuario> usuarios;
    
	public List<ProdutoTipo> getProdutoTipos() {
        return produtoTipos;
    }

    public void setProdutoTipos(List<ProdutoTipo> produtoTipos) {
        this.produtoTipos = produtoTipos;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
