package br.com.ericfujii.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.Query;
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
 
        Query q = session.createQuery("From ProdutoTipo ");
                 
        List<ProdutoTipo> resultList = q.list();
        System.out.println("Quantidade de Tipos:" + resultList.size());
        for (ProdutoTipo next : resultList) {
            System.out.println("Tipo: " + next.getNome());
        }
	}
	
}
