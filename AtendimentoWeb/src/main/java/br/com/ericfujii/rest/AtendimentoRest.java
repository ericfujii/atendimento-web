package br.com.ericfujii.rest;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.ericfujii.entidade.EAcaoPedido;
import br.com.ericfujii.entidade.ESituacaoPedido;
import br.com.ericfujii.entidade.ETipoPedido;
import br.com.ericfujii.entidade.ItemPedido;
import br.com.ericfujii.entidade.Mensagem;
import br.com.ericfujii.entidade.Pedido;
import br.com.ericfujii.entidade.Produto;
import br.com.ericfujii.entidade.ProdutoTipo;
import br.com.ericfujii.entidade.Usuario;
import br.com.ericfujii.entidade.envio.MobileEnvioAlterarCancelarPedido;
import br.com.ericfujii.entidade.envio.MobileEnvioFila;
import br.com.ericfujii.entidade.envio.MobileEnvioLogin;
import br.com.ericfujii.entidade.envio.MobileEnvioMensagem;
import br.com.ericfujii.entidade.envio.MobileEnvioPacote;
import br.com.ericfujii.entidade.envio.MobileEnvioPedido;
import br.com.ericfujii.entidade.envio.MobileEnvioVerificarMensagens;
import br.com.ericfujii.entidade.retorno.MobileRetornoAlterarCancelarPedido;
import br.com.ericfujii.entidade.retorno.MobileRetornoFila;
import br.com.ericfujii.entidade.retorno.MobileRetornoLogin;
import br.com.ericfujii.entidade.retorno.MobileRetornoMensagem;
import br.com.ericfujii.entidade.retorno.MobileRetornoPacote;
import br.com.ericfujii.entidade.retorno.MobileRetornoPedido;
import br.com.ericfujii.entidade.retorno.MobileRetornoVerificarMensagens;
import br.com.ericfujii.servico.ItemPedidoServico;
import br.com.ericfujii.servico.MensagemServico;
import br.com.ericfujii.servico.PedidoServico;
import br.com.ericfujii.servico.ProdutoServico;
import br.com.ericfujii.servico.ProdutoTipoServico;
import br.com.ericfujii.servico.UsuarioServico;

@Path("/atendimentoRest")
public class AtendimentoRest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProdutoServico produtoServico;
	@EJB
	private ProdutoTipoServico produtoTipoServico;
	@EJB
	private PedidoServico pedidoServico;
	@EJB
	private ItemPedidoServico itemPedidoServico;
	@EJB
	private UsuarioServico usuarioServico;
	@EJB
	private MensagemServico mensagemServico;
		
	@POST
	@Path("processar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response processar(RequestAtendimentoRest request) {
		ResponseAtendimentoRest response = new ResponseAtendimentoRest();
		try {
			if (request.getCodigoFuncao().equals(ECodigoFuncao.CARGA_PACOTES)) {
				response.setProdutosTipos(produtoTipoServico.obterTodos());
				response.setProdutos(produtoServico.obterTodos());
				response.setUsuarios(usuarioServico.obterTodos());
			} else if(request.getCodigoFuncao().equals(ECodigoFuncao.LOGIN)) {
				Usuario usuario = usuarioServico.efetuarLogin(request.getLogin(), request.getSenha());
				
				if (usuario == null) {
					response.setCodigoResponse(ECodigoResponse.ERROR);
					response.setMessage("Usuário/Senha incorretos!");
				} else {
					response.setCodigoResponse(ECodigoResponse.OK);
					response.setIdUsuario(usuario.getId());
					response.setLogin(usuario.getLogin());
					response.setNomeUsuario(usuario.getNome());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseAtendimentoRest(ECodigoResponse.ERROR, e.getMessage());
		}
		return Response.ok(response).build();
	}
	
	@POST
	@Path("logar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logar(MobileEnvioLogin request) {
		MobileRetornoLogin response = new MobileRetornoLogin();
		Usuario usuario = usuarioServico.efetuarLogin(request.getLogin(), request.getSenha());
		
		if (usuario == null) {
			response.setCodigoRetorno(ECodigoResponse.ERROR.name());
			response.setMensagem("Usuário/Senha incorretos!");
		} else {
			response.setCodigoRetorno(ECodigoResponse.OK.name());
			response.setIdUsuario(usuario.getId());
		}
		return Response.ok(response).build();
	}
	
	@POST
	@Path("verificarPacote")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response carregarPacotes(MobileEnvioPacote request) {
		MobileRetornoPacote response = new MobileRetornoPacote();
		response.setProdutos(produtoServico.obterTodosCompleto());
		for (Produto produto : response.getProdutos()) {
			produto.setItensPedidos(null);
		}
		response.setProdutoTipos(produtoTipoServico.obterTodos());
		for (ProdutoTipo produtoTipo : response.getProdutoTipos()) {
			produtoTipo.setProdutos(null);
		}
		response.setUsuarios(usuarioServico.obterTodos());
		response.setCodigoRetorno(ECodigoResponse.OK.name());
		return Response.ok(response).build();
	}
	
	@POST
	@Path("salvarPedido")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarPedido(MobileEnvioPedido request) {
		MobileRetornoPedido response = new MobileRetornoPedido();
		try {
			Pedido pedido = request.getPedido();
			pedido.setDataHoraCadatro(Calendar.getInstance());
			pedido.setTipoPedido(ETipoPedido.MESA);
			pedido.setUsuario(usuarioServico.obterPorId(request.getIdUsuario()));
			
			for (ItemPedido itemPedido : pedido.getPedidos()) {
				itemPedido.setPedido(pedido);
				itemPedido.setSituacaoPedido(ESituacaoPedido.NOVO);
				itemPedido.setDataHotaUltimaSituacao(Calendar.getInstance());
				itemPedido.setViagem(itemPedido.getQuantidadeViagem() != null && itemPedido.getQuantidadeViagem()> 0);
			}
			
			pedidoServico.salvar(pedido);
			
			response.setCodigoRetorno(ECodigoResponse.OK.name());
		} catch (Exception e) {
			response.setCodigoRetorno(ECodigoResponse.ERROR.name());
		}
		return Response.ok(response).build();
	}
	
	@POST
	@Path("recuperarFila")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response recuperarFila(MobileEnvioFila request) {
		MobileRetornoFila response = new MobileRetornoFila();
		try {
			List<ItemPedido> itensPedidos = itemPedidoServico.obterFilaProduto(request.getProduto());
			for (ItemPedido itemPedido : itensPedidos) {
				itemPedido.getProduto().setItensPedidos(null);
				itemPedido.getPedido().setPedidos(null);
				itemPedido.getProduto().setProdutoTipo(null);
				itemPedido.getPedido().setUsuario(null);
			}
			response.setItensPedidos(itensPedidos);
			response.setCodigoRetorno(ECodigoResponse.OK.name());
		} catch (Exception e) {
			response.setCodigoRetorno(ECodigoResponse.ERROR.name());
		}
		return Response.ok(response).build();
	}
	
	@POST
	@Path("verificarMensagens")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response verificarMensagens(MobileEnvioVerificarMensagens request) {
		MobileRetornoVerificarMensagens response = new MobileRetornoVerificarMensagens();
		try {
			response.setMensagens(mensagemServico.obterPorDestinatario(new Usuario(request.getIdUsuario())));
			for (Mensagem mensagem : response.getMensagens()) {
				mensagem.setEnviada(true);
				mensagemServico.alterar(mensagem);
			}
			response.setCodigoRetorno(ECodigoResponse.OK.name());
		} catch (Exception e) {
			response.setCodigoRetorno(ECodigoResponse.ERROR.name());
		}
		return Response.ok(response).build();
	}
	
	@POST
	@Path("enviarMensagem")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response enviarMensagem(MobileEnvioMensagem request) {
		MobileRetornoMensagem response = new MobileRetornoMensagem();
		try {
			request.getMensagem().setDataMensagem(Calendar.getInstance());
			response.setHoraMensagem(request.getMensagem().getDataMensagem());
			response.setIdMensagem(mensagemServico.salvar(request.getMensagem()).getId());
			response.setCodigoRetorno(ECodigoResponse.OK.name());
		} catch (Exception e) {
			response.setCodigoRetorno(ECodigoResponse.ERROR.name());
		}
		return Response.ok(response).build();
	}
	
	@POST
	@Path("alterarCancelarPedido")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response alterarCancelarPedido(MobileEnvioAlterarCancelarPedido request) {
		MobileRetornoAlterarCancelarPedido response = new MobileRetornoAlterarCancelarPedido();
		try {
			if (request.getAcaoPedido() == EAcaoPedido.CANCELAR) {
				ItemPedido itemPedido = itemPedidoServico.obterPorId(request.getItemPedido().getId());
				itemPedido.setSituacaoPedido(ESituacaoPedido.CANCELADO);
				itemPedidoServico.alterar(itemPedido);
			}
			response.setCodigoRetorno(ECodigoResponse.OK.name());
		} catch (Exception e) {
			response.setCodigoRetorno(ECodigoResponse.ERROR.name());
		}
		return Response.ok(response).build();
	}
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String respondAsReady() {
        return "Demo service is ready!";
    }
	
	@GET
	@Path("teste")
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
		return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
	}
}
