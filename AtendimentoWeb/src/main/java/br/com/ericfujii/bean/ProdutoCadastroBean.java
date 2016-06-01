package br.com.ericfujii.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.ericfujii.entidade.ESituacao;
import br.com.ericfujii.entidade.Produto;
import br.com.ericfujii.entidade.ProdutoTipo;
import br.com.ericfujii.servico.ProdutoServico;
import br.com.ericfujii.servico.ProdutoTipoServico;

@ViewScoped
@ManagedBean
public class ProdutoCadastroBean {

	private Produto 		  produto;
	private List<ProdutoTipo> produtosTipos;
	private List<Produto>     produtos;
	@EJB
	private ProdutoServico produtoServico;
	@EJB
	private ProdutoTipoServico produtoTipoServico;
	
	private StringBuilder selectProdutos = new StringBuilder();
	{
		selectProdutos.append("FROM Produto p ");
		selectProdutos.append("JOIN FETCH p.produtoTipo pt ");
		//selectProdutos.append("WHERE p.situacao = 'ATIVO' ");
		selectProdutos.append("ORDER BY p.ordem, p.id ");
	}
	
	private StringBuilder selectprodutosTipos = new StringBuilder();
	{
		selectprodutosTipos.append("FROM ProdutoTipo pt ");
		selectprodutosTipos.append("WHERE pt.situacao = 'ATIVO' ");
		selectprodutosTipos.append("ORDER BY pt.nome, pt.id ");
	}
	 
	
	@PostConstruct
	public void inicializar() {
		construirProduto();
		carregarProdutosTipos();
		carregarProdutos();
	}
	
	private void carregarProdutos() {
		produtos = produtoServico.obterTodos();
	}

	public void construirProduto() {
		produto = new Produto();
		produto.setProdutoTipo(new ProdutoTipo());
	}

	private void carregarProdutosTipos() {
		produtosTipos = produtoTipoServico.obterTodos();
	}
	
	public void salvar() {
		if ((produto.getNome() == null) 
				|| (produto.getNome().trim().isEmpty())) {
			makeMessage(FacesMessage.SEVERITY_WARN, "Informe o nome!", "");
			return;
		}
		
		if ((produto.getProdutoTipo().getId() == null)) {
			makeMessage(FacesMessage.SEVERITY_WARN, "Selecione o Tipo!", "");
			return;
		}
		
        if (produto.getId() == null) {
        	produtoServico.salvar(produto);
        	makeMessage(FacesMessage.SEVERITY_INFO, "Produto cadastrado com sucesso!", "");
        } else {
        	produtoServico.alterar(produto);
        	makeMessage(FacesMessage.SEVERITY_INFO, "Produto editado com sucesso!", "");
        }
        construirProduto();
        carregarProdutos();
	}
	
	public void editar(Produto produtoSelecionado) {
		this.produto = produtoSelecionado;
	}
	
	public void inativar(Produto produtoSelecionado) {
		produtoSelecionado.setSituacao(ESituacao.INATIVO);
		atualizar(produtoSelecionado, "Produto " 
									  + produtoSelecionado.getId()
									  + " - "
									  + produtoSelecionado.getNome() 
									  + " inativado com sucesso!");
    	carregarProdutos();
	}
	
	public void reativar(Produto produtoSelecionado) {
		
		produtoSelecionado.setSituacao(ESituacao.ATIVO);
		atualizar(produtoSelecionado, "Produto " 
									  + produtoSelecionado.getId()
									  + " - "
									  + produtoSelecionado.getNome() 
									  + " reativado com sucesso!");
    	carregarProdutos();
	}
	
	private void atualizar(Produto produto, String message) {
		produtoServico.alterar(produto);
    	makeMessage(FacesMessage.SEVERITY_INFO, message, "");
	}

	private void makeMessage(Severity severity, String message, String title) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, title));
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<ProdutoTipo> getProdutosTipos() {
		return produtosTipos;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
}
