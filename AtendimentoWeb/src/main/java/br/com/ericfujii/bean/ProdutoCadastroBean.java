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

	private Produto produto;
	private List<ProdutoTipo> produtosTipos;
	private List<Produto> produtos;
	
	@PostConstruct
	public void inicializar() {
		construirProduto();
		carregarProdutosTipos();
		carregarProdutos();
	}
	
	@SuppressWarnings("unchecked")
	private void carregarProdutos() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		produtos = (List<Produto>) session.createQuery("FROM Produto p JOIN FETCH p.produtoTipo pt WHERE p.situacao = 'ATIVO' ORDER BY p.nome ").list();
	}

	private void construirProduto() {
		produto = new Produto();
		produto.setProdutoTipo(new ProdutoTipo());
	}

	@SuppressWarnings("unchecked")
	private void carregarProdutosTipos() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		produtosTipos = (List<ProdutoTipo>) session.createQuery("FROM ProdutoTipo pt WHERE pt.situacao = 'ATIVO' ORDER BY pt.nome").list();
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
        
        construirProduto();
        carregarProdutos();
	}
	
	public void editar(Produto produtoSelecionado) {
		this.produto = produtoSelecionado;
	}
	
	public void excluir(Produto produtoSelecionado) {
		produtoSelecionado.setSituacao(ESituacao.INATIVO);
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(produtoSelecionado);
    	makeMessage(FacesMessage.SEVERITY_INFO, "Produto inativado com sucesso!", "");
    	session.getTransaction().commit();
    	carregarProdutos();
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
