package br.com.ericfujii.rest;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.ericfujii.entidade.Usuario;
import br.com.ericfujii.entidade.envio.MobileEnvioLogin;
import br.com.ericfujii.entidade.retorno.MobileRetornoLogin;
import br.com.ericfujii.servico.ItemPedidoServico;
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
