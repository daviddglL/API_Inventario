package dao.Proveedores;

import entidades.Proveedores;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.PersistenceException;

public class ProveedoresDAO implements ProveedoresDAOInterface{

    @Override
    public Proveedores crearProveedor(Proveedores p){
        Session session= HibernateUtil.getSessionFactory().openSession();

        try{
            session.beginTransaction();
            session.save(p);
            session.getTransaction().commit();
        }catch (PersistenceException e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return p;
    }

    @Override
    public Proveedores buscarPorId(Long id){
        Proveedores p=null;
        Session session=HibernateUtil.getSessionFactory().openSession();
        try{
         p =session.find(Proveedores.class,id);
        }catch (Exception e){
            System.out.println(e);
        }
        session.close();
        return  p;
    }
}
