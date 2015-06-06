package br.com.ericfujii.rest;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import br.com.ericfujii.entidade.ItemPedido;
import br.com.ericfujii.entidade.Pedido;
import br.com.ericfujii.entidade.Produto;
import br.com.ericfujii.entidade.ProdutoTipo;
import br.com.ericfujii.entidade.Usuario;

@XmlRootElement
public class ResponseAtendimentoRest {
	
	@XmlElement(name = "codigo_response")
	private ECodigoResponse codigoResponse;
	
	@XmlElement(name = "message")
	private String message;
	
	@XmlElement(name = "produtos_tipos")
	private List<ProdutoTipo> produtosTipos;
	
	@XmlElement(name = "usuarios")
	private List<Usuario> usuarios;
	
	@XmlElement(name = "produtos")
	private List<Produto> produtos;
	
	@XmlElement(name = "pedidos")
	private List<Pedido> pedidos;
	
	@XmlElement(name = "itens_pedidos")
	private List<ItemPedido> itensPedidos;

	public ResponseAtendimentoRest() {
		
	}

	public ResponseAtendimentoRest(ECodigoResponse codigoResponse, String message) {
		this.codigoResponse = codigoResponse;
		this.message 		= message;
	}

	public ECodigoResponse getCodigoResponse() {
		return codigoResponse;
	}

	public void setCodigoResponse(ECodigoResponse codigoResponse) {
		this.codigoResponse = codigoResponse;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ProdutoTipo> getProdutosTipos() {
		return produtosTipos;
	}

	public void setProdutosTipos(List<ProdutoTipo> produtosTipos) {
		this.produtosTipos = produtosTipos;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public List<ItemPedido> getItensPedidos() {
		return itensPedidos;
	}

	public void setItensPedidos(List<ItemPedido> itensPedidos) {
		this.itensPedidos = itensPedidos;
	}
}
