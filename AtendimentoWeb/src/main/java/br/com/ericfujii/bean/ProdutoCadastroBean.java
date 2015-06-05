package br.com.ericfujii.bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import br.com.ericfujii.entidade.ESituacao;
import br.com.ericfujii.entidade.Produto;
import br.com.ericfujii.entidade.ProdutoTipo;
import br.com.ericfujii.hibernate.HibernateUtil;

@ViewScoped
@ManagedBean
public class ProdutoCadastroBean {

	private Produto 		  produto;
	private List<ProdutoTipo> produtosTipos;
	private List<Produto>     produtos;
	
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
	
	@SuppressWarnings("unchecked")
	private void carregarProdutos() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		produtos = (List<Produto>) session
				.createQuery(selectProdutos.toString())
				.list();
		session.close();
	}

	public void construirProduto() {
		produto = new Produto();
		produto.setProdutoTipo(new ProdutoTipo());
	}

	@SuppressWarnings("unchecked")
	private void carregarProdutosTipos() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		produtosTipos = (List<ProdutoTipo>) session
				.createQuery(selectprodutosTipos.toString())
				.list();
		session.close();
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
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        if (produto.getId() == null) {
        	session.save(produto);
        	makeMessage(FacesMessage.SEVERITY_INFO, "Produto cadastrado com sucesso!", "");
        } else {
        	session.update(produto);
        	makeMessage(FacesMessage.SEVERITY_INFO, "Produto editado com sucesso!", "");
        }
        session.getTransaction().commit();
        session.close();
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
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(produto);
    	makeMessage(FacesMessage.SEVERITY_INFO, message, "");
    	session.getTransaction().commit();
    	session.close();
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
