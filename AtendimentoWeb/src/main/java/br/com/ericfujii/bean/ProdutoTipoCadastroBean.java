package br.com.ericfujii.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Session;

import br.com.ericfujii.entidade.ProdutoTipo;
import br.com.ericfujii.hibernate.HibernateUtil;

@RequestScoped
@ManagedBean
public class ProdutoTipoCadastroBean {

	private ProdutoTipo produtoTipo = new ProdutoTipo();

	public ProdutoTipo getProdutoTipo() {
		return produtoTipo;
	}

	public void setProdutoTipo(ProdutoTipo produtoTipo) {
		this.produtoTipo = produtoTipo;
	}
	
	public void salvar() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		  
        session.beginTransaction();
 
        session.save(produtoTipo);
 
        session.getTransaction().commit();
        
        produtoTipo = new ProdutoTipo();
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de produto cadastrado com sucesso!", ""));
 
        
/*        Query q = session.createQuery("From ProdutoTipo ");
                 
        List<ProdutoTipo> resultList = q.list();
        System.out.println("Quantidade de Tipos:" + resultList.size());
        for (ProdutoTipo next : resultList) {
            System.out.println("Tipo: " + next.getNome());
        }*/
	}
	
}
