package br.com.ericfujii.dao;

import java.util.Collections;
import java.util.List;

import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.stat.Statistics;

/* Metodos dessa classe e das DAOs de consulta devem ser prefixadas por "consulta" */

@Local(InterfaceDAO.class)
public abstract class BaseDAO<T> implements InterfaceDAO<T> {

	private static final long serialVersionUID = 1L;
	@PersistenceContext(name = PersistenceUnitConfig.NAME)
	private EntityManager entityManager;
	private Class<T> classe;

	private static Statistics statistics;

	public BaseDAO(Class<T> classe) {
		this.classe = classe;
	}

	protected StatelessSession getOpenStatelesSession() {
		return entityManager.unwrap(Session.class).getSessionFactory()
				.openStatelessSession();
	}

	public void getStatistics() {
		if (statistics == null) {
			statistics = entityManager.unwrap(Session.class).getSessionFactory().getStatistics();
			statistics.setStatisticsEnabled(true);
		}
	}
	
	@Override
	public T salvar(T t) {
		entityManager.persist(t);
		return t;
	}

	@Override
	public T alterar(T t) {
		return entityManager.merge(t);
	}

	@Override
	public void deletar(T t, Integer id) {
		entityManager.remove(entityManager.getReference(t.getClass(), id));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> consultarTodos() {
		Query query = entityManager.createQuery("SELECT obj FROM ".concat(
				this.classe.getSimpleName()).concat(" obj"));
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> consultarTodos(String orderBy) {
		Query query = entityManager.createQuery("SELECT obj FROM "
				.concat(this.classe.getSimpleName()).concat(" obj ")
				.concat("ORDER BY obj.").concat(orderBy));
		query.setHint("org.hibernate.cacheable", true);
		List<T> list = query.getResultList();
		Collections.unmodifiableCollection(list);
		return list;
	}


	@Override
	public int consultarQuantidade() {
		return ((Long) entityManager.createQuery(
				"SELECT count(obj.id) FROM ".concat(
						this.classe.getSimpleName()).concat(" obj"))
				.getSingleResult()).intValue();
	}

	@Override
	public T consultarPorId(int id) {
		return entityManager.find(classe, id);
	}
	protected EntityManager getEm() {
		return entityManager;
	}
}
