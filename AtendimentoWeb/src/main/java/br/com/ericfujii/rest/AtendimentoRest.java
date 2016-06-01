package br.com.ericfujii.rest;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//@Path("/atendimentoRest")
public class AtendimentoRest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private RestServico restServico;
		
	/*@POST
	@Path("processar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response processar(RequestAtendimentoRest request) {
		ResponseAtendimentoRest response = new ResponseAtendimentoRest();
		try {
			if (request.getCodigoFuncao().equals(ECodigoFuncao.CARGA_PACOTES)) {
				response.setProdutosTipos(restServico.obterProdutosTipos());
				response.setProdutos(restServico.obterProdutos());
				response.setUsuarios(restServico.obterUsuarios());
				response.setPedidos(restServico.obterPedidos());
				response.setItensPedidos(restServico.obterItensPedidos());
			} else if(request.getCodigoFuncao().equals(ECodigoFuncao.LOGIN)) {
				Session session = HibernateUtil.getSessionFactory().openSession();
				Query q = session.createQuery("From Usuario u WHERE u.login = '" + request.getLogin() + "' AND u.senha = '" + request.getSenha() + "'");
				List<Usuario> usuarios = q.list();
				
				if (usuarios == null || usuarios.size() == 0) {
					response.setCodigoResponse(ECodigoResponse.ERROR);
					response.setMessage("Usuário/Senha incorretos!");
				} else {
					response.setCodigoResponse(ECodigoResponse.OK);
					response.setIdUsuario(usuarios.get(0).getId());
					response.setLogin(usuarios.get(0).getLogin());
					response.setNomeUsuario(usuarios.get(0).getNome());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseAtendimentoRest(ECodigoResponse.ERROR, e.getMessage());
		}
		return Response.ok(response).build();
	}*/
	
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

	public RestServico getRestServico() {
		if (restServico == null) {
			restServico = new RestServico();
		}
		return restServico;
	}
}
