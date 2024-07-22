package DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAO<E> {

	private static EntityManagerFactory emf;
	public EntityManager em;
	public Class<E> classe;
	
	static {
	try {
		
		emf = Persistence.createEntityManagerFactory("nova_empresa");
		
	 }catch (Exception e) {
		
	}
}
	
	public DAO(){
		
		em = emf.createEntityManager();
	}
	public DAO(Class<E>classe){
		em = emf.createEntityManager();
		this.classe = classe;
		
	}
	
	public DAO<E> abrirTransacao(){
		em.getTransaction().begin();
		return this;
	}
	public DAO<E> fecharTransacao(){
		em.getTransaction().commit();
		return this;
	}
	public DAO<E> fecharConexao(){
		em.close();
		return this;
	}
	public DAO<E> incluir(E entidade){
		em.persist(entidade);
		return this;
	}
	public DAO<E> incluirAtomico(E entidade){
		return abrirTransacao().incluir(entidade).fecharTransacao().fecharConexao();
	}
	public DAO<E> atualizar(E entidade){
		em.merge(entidade);
		return this;
	}
	public DAO<E> remover(E entidade) {
		em.remove(entidade);
		return this;
	}
	public E obter(Object id){
		return em.find(classe, id);
		
	}
}
