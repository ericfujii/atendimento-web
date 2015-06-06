package br.com.ericfujii.rest;

import java.util.ArrayList;
import java.util.List;
import br.com.ericfujii.entidade.ESituacao;
import br.com.ericfujii.entidade.ItemPedido;
import br.com.ericfujii.entidade.Pedido;
import br.com.ericfujii.entidade.Produto;
import br.com.ericfujii.entidade.ProdutoTipo;
import br.com.ericfujii.entidade.Usuario;

public class RestServico {

	public List<ProdutoTipo> obterProdutosTipos() {
		ProdutoTipo pt = new ProdutoTipo();
		pt.setId(1);
		pt.setBebida(false);
		pt.setNome("TESTE");
		pt.setSituacao(ESituacao.ATIVO);
		List<ProdutoTipo> produtosTipos = new ArrayList<ProdutoTipo>();
		produtosTipos.add(pt);
		return produtosTipos;
	}

	public List<Produto> obterProdutos() {
		return null;
	}

	public List<Usuario> obterUsuarios() {
		return null;
	}

	public List<Pedido> obterPedidos() {
		return null;
	}

	public List<ItemPedido> obterItensPedidos() {
		return null;
	}

}
