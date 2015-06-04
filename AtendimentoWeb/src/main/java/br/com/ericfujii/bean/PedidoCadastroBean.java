package br.com.ericfujii.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ericfujii.entidade.Pedido;

@ViewScoped
@ManagedBean
public class PedidoCadastroBean {

	private Pedido pedido;

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	
	
}
