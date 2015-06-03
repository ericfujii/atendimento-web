package br.com.ericfujii.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.ericfujii.entidade.ProdutoTipo;
import br.com.ericfujii.hibernate.HibernateUtil;

@RequestScoped
@ManagedBean
public class ProdutoTipoCadastroBean {

	private ProdutoTipo produtoTipo = new ProdutoTipo();
	private List<ProdutoTipo> produtoTipos;

	public ProdutoTipo getProdutoTipo() {
		return produtoTipo;
	}
	
	@PostConstruct
	public void postContruct() {
		atualizarLista();
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
	
	public void salvar() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		  
        session.beginTransaction();
 
        if (produtoTipo.getId() == null) {
        	session.save(produtoTipo);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de produto cadastrado com sucesso!", ""));
        } else {
        	session.update(produtoTipo);
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de produto editado com sucesso!", ""));
        }
 
        session.getTransaction().commit();
        
        produtoTipo = new ProdutoTipo();
        
        atualizarLista();
        
        
 
	}
	
	public void editar(int row) {
		produtoTipo = produtoTipos.get(row);
	}
	
	public void excluir(int row) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		  
        session.beginTransaction();
        
		produtoTipo = produtoTipos.get(row);
		
		session.delete(produtoTipo);
		
		session.getTransaction().commit();
		
		atualizarLista();
	}
	
	public void atualizarLista() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("From ProdutoTipo ");
        
		produtoTipos = q.list();
	}
	
}
