package br.com.ericfujii.bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import br.com.ericfujii.entidade.ESituacao;
import br.com.ericfujii.entidade.ProdutoTipo;
import br.com.ericfujii.hibernate.HibernateUtil;

@ViewScoped
@ManagedBean
public class ProdutoTipoCadastroBean {

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
		Session session = HibernateUtil.getSessionFactory().openSession();
		  
        session.beginTransaction();
 
        if (produtoTipo.getId() == null) {
        	session.save(produtoTipo);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de produto " 
        	+ produtoTipo.getNome() 
        	+ " cadastrado com sucesso!", ""));
        } else {
        	session.update(produtoTipo);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de produto " 
        	+ produtoTipo.getNome() 
        	+ " editado com sucesso!", ""));
        }
        session.getTransaction().commit();
        produtoTipo = new ProdutoTipo();
        session.close();
        atualizarLista();
	}
	
	public void editar(int row) {
		produtoTipo = produtoTipos.get(row);
	}
	
	public void inativar(int row) {
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
		produtoTipo = produtoTipos.get(row);
		produtoTipo.setSituacao(ESituacao.INATIVO);
		session.update(produtoTipo);
		session.createQuery("UPDATE Produto SET situacao = 'INATIVO' WHERE produtoTipo.id =:_produtoTipo")
			.setParameter("_produtoTipo", produtoTipo.getId())
			.executeUpdate();
		session.getTransaction().commit();
		session.close();
    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de produto " 
    	+ produtoTipo.getNome() 
    	+ " inativado com sucesso!", ""));
    	construirProdutoTipo();
		atualizarLista();
	}
	
	public void reativar(int row) {
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
		produtoTipo = produtoTipos.get(row);
		produtoTipo.setSituacao(ESituacao.ATIVO);
		session.update(produtoTipo);
		session.getTransaction().commit();
		session.close();
    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de produto " 
    	+ produtoTipo.getNome() 
    	+ " reativado com sucesso!", ""));
		construirProdutoTipo();
		atualizarLista();
	}
	
	public void atualizarLista() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("From ProdutoTipo ");
		produtoTipos = q.list();
		session.close();
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
