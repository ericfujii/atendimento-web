package br.com.ericfujii.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.ericfujii.entidade.ESituacao;
import br.com.ericfujii.entidade.ProdutoTipo;
import br.com.ericfujii.servico.ProdutoTipoServico;

@ViewScoped
@ManagedBean
public class ProdutoTipoCadastroBean {

	@EJB
	private ProdutoTipoServico produtoTipoServico;
	
	private ProdutoTipo produtoTipo;
	private List<ProdutoTipo> produtoTipos;
	
	@PostConstruct
	public void postContruct() {
		construirProdutoTipo();
		atualizarLista();
	}
	
	public void construirProdutoTipo() {
		this.produtoTipo = new ProdutoTipo();
	}

	public void salvar() {
        if (produtoTipo.getId() == null) {
        	produtoTipoServico.salvar(produtoTipo);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de produto " 
        	+ produtoTipo.getNome() 
        	+ " cadastrado com sucesso!", ""));
        } else {
        	produtoTipoServico.salvar(produtoTipo);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de produto " 
        	+ produtoTipo.getNome() 
        	+ " editado com sucesso!", ""));
        }
        produtoTipo = new ProdutoTipo();
        atualizarLista();
	}
	
	public void editar(int row) {
		produtoTipo = produtoTipos.get(row);
	}
	
	public void inativar(int row) {
		produtoTipo = produtoTipos.get(row);
		produtoTipo.setSituacao(ESituacao.INATIVO);
		produtoTipoServico.alterar(produtoTipo);
		/*session.createQuery("UPDATE Produto SET situacao = 'INATIVO' WHERE produtoTipo.id =:_produtoTipo")
			.setParameter("_produtoTipo", produtoTipo.getId())
			.executeUpdate();*/
    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de produto " 
    	+ produtoTipo.getNome() 
    	+ " inativado com sucesso!", ""));
    	construirProdutoTipo();
		atualizarLista();
	}
	
	public void reativar(int row) {
		produtoTipo = produtoTipos.get(row);
		produtoTipo.setSituacao(ESituacao.ATIVO);
		produtoTipoServico.alterar(produtoTipo);
    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de produto " 
    	+ produtoTipo.getNome() 
    	+ " reativado com sucesso!", ""));
		construirProdutoTipo();
		atualizarLista();
	}
	
	public void atualizarLista() {
		produtoTipos = produtoTipoServico.obterTodos();
	}
	

	public ProdutoTipo getProdutoTipo() {
		return produtoTipo;
	}
	
	public void setProdutoTipo(ProdutoTipo produtoTipo) {
		this.produtoTipo = produtoTipo;
	}
	
	public List<ProdutoTipo> getProdutoTipos() {
		return produtoTipos;
	}

	public void setProdutoTipos(List<ProdutoTipo> produtoTipos) {
		this.produtoTipos = produtoTipos;
	}
}
