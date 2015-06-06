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
		
		@POST
		@Path(D2DRecurso.PROCESSAR)
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response processar(RequestAtendimentoRest request) throws BaseServicoException {
			try {
				if (requestD2D.getOperacao().equals(ECodigoRequest.VERIFICAR_CONEXAO)) {
					responseD2D.setMensagem("Conexão OK!");
				} else {
				} 
				
				return Response.ok(responseD2D).build();
			} catch (Exception e) {
				e.printStackTrace();
				ResponseAtendimentoRest response = new ResponseAtendimentoRest(ECodigoResponse.ERROR);
				response.setMensagem(e.getMessage());
				return Response.ok(response).build();
			}
		}
	}
