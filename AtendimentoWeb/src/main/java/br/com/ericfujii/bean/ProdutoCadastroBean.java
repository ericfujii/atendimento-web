package br.com.ericfujii.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Session;

import br.com.ericfujii.entidade.Produto;
import br.com.ericfujii.entidade.ProdutoTipo;
import br.com.ericfujii.hibernate.HibernateUtil;

@ViewScoped
@ManagedBean
public class ProdutoCadastroBean {

	private Produto produto;
	private List<ProdutoTipo> produtosTipos;
	private List<Produto> produtos;
	
	@PostConstruct
	public void inicializar() {
		construirProduto();
		carregarProdutosTipos();
		carregarProdutos();
	}
	
	private void carregarProdutos() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		produtosTipos = (List<ProdutoTipo>) session.createQuery("FROM ProdutoTipo pt ORDER BY pt.nome ").list();
	}

	private void construirProduto() {
		produto = new Produto();
		produto.setProdutoTipo(new ProdutoTipo());
	}

	@SuppressWarnings("unchecked")
	private void carregarProdutosTipos() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		produtosTipos = (List<ProdutoTipo>) session.createQuery("FROM ProdutoTipo pt ORDER BY pt.nome ").list();
	}
	
	public void salvar() {
		
		if ((produto.getProdutoTipo().getId() == null)) {
			makeMessage(FacesMessage.SEVERITY_WARN, "Selecione o Tipo!", "");
		}
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(produto);
        session.getTransaction().commit();
        
        construirProduto();
        makeMessage(FacesMessage.SEVERITY_INFO, "Produto cadastrado com sucesso!", "");
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
