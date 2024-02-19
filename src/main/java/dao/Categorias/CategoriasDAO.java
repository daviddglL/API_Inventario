package dao.Categorias;


import entidades.Categorias;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.PersistenceException;
import java.util.List;

public class CategoriasDAO implements CategoriasDAOInterface{

    @Override
    public Categorias crearCategoria(Categorias c){
        Session session = HibernateUtil.getSessionFactory().openSession();

        try{
            session.beginTransaction();
            session.save(c);
            session.getTransaction().commit();
        }catch (PersistenceException e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return c;
    }


    @Override
    public Categorias buscarPorId(Long id){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Categorias c = session.find(Categorias.class,id);
        session.close();

        return c;
    }


    public List<Categorias> mostrarTodos() {
        List<Categorias> todas = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            todas = session.createQuery("from Categorias", Categorias.class).list();
            for (Categorias categoria : todas) {
                // Acceder a la colección 'cat' de cada categoría para forzar la carga
                categoria.getCat().size();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return todas;
    }

}
