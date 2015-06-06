package br.com.ericfujii.rest;

import java.io.Serializable;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/atendimentoRest")
public class AtendimentoRest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private RestServico restServico;
		
	@POST
	@Path("processar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response processar(RequestAtendimentoRest request) {
		ResponseAtendimentoRest response = new ResponseAtendimentoRest();
		try {
			if (request.getCodigoRequest().equals(ECodigoRequest.CARGA_PACOTES)) {
				response.setProdutosTipos(restServico.obterProdutosTipos());
				response.setProdutos(restServico.obterProdutos());
				response.setUsuarios(restServico.obterUsuarios());
				response.setPedidos(restServico.obterPedidos());
				response.setItensPedidos(restServico.obterItensPedidos());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseAtendimentoRest(ECodigoResponse.ERROR, e.getMessage());
		}
		return Response.ok(response).build();
	}

	public RestServico getRestServico() {
		if (restServico == null) {
			restServico = new RestServico();
		}
		return restServico;
	}
}
