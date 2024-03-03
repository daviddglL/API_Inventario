package dao.Asociaciones;


import entidades.Almacen;
import entidades.Productos;
import entidades.Proveedores;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AsociacionesDAO implements AsociacionesDAOInterface{





    @Override
    public Double mediaPreciosProveedor(String proveedor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Double media = null;

        try {
            Query<Double> query = session.createQuery(
                    "SELECT AVG(e.precio_uni) FROM Productos e " +
                            "INNER JOIN e.prod p " +
                            "WHERE p.nombre = :proveedor",
                    Double.class
            );
            query.setParameter("proveedor", proveedor);
            media = query.getSingleResult();
        } catch (NoResultException e) {
            // Manejar la excepción si no se encuentra ningún resultado
            e.printStackTrace();
        } finally {
            session.close();
        }

        return media;
    }

    @Override
    public Long almacencantidad(Long almacen){
        List<Almacen> productos;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Almacen> query = session.createQuery("select a from Almacen a join fetch a.producto where a.id_almacen = :id",
                    Almacen.class
            );
            query.setParameter("id", almacen);
            productos = query.list();
        }catch (NoResultException e) {
        // Manejar la excepción si no se encuentra ningún resultado
         e.printStackTrace();
         productos = new ArrayList<>();
         } finally {
                session.close();
         }
        return (long) productos.size();
    }



    @Override
    public Proveedores obtenerProvedorProducto(Productos p) {
        Proveedores proveedor;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Productos> query = session.createQuery("select e from Productos e join fetch e.prod where e.id =:id", Productos.class);
            query.setParameter("id", p.getId_producto());
            proveedor = query.getSingleResult().getProd();

        } catch (NoResultException nre) {
            proveedor=null;
        }

        session.close();
        return proveedor;
    }


    @Override
    public List<Productos> obtenerProductosProveedor(Proveedores p) {
        List<Productos> lista_productos;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Proveedores> query = session.createQuery("select e from Proveedores e join fetch e.prod where e.id =:id", Proveedores.class);
            query.setParameter("id", p.getId());
            lista_productos = query.getSingleResult().getProd();


        }catch (NoResultException nre){
            lista_productos=new ArrayList<>();
        }catch (NullPointerException npe){
            lista_productos=null;
        }
        finally {
            session.close();
        }


        return lista_productos;
    }




    @Override
    public List<Productos> almacenesconProductos(Almacen c) {
        List<Productos> lista_productos = null;

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Almacen> query = session.createQuery("select c from Almacen c join fetch c.producto where c.id =:id", Almacen.class);
            query.setParameter("id", c.getId_almacen());
            lista_productos = query.getSingleResult().getProducto();
        } catch (NoResultException nre) {
            lista_productos = new ArrayList<>();
        }

        session.close();
        return lista_productos;
    }


    @Override
    public List<Almacen> almacendelproducto(Productos p) {
        List<Almacen> lista_almacenes;

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Productos> query = session.createQuery("select c from Productos c join fetch c.alma where c.id =:id", Productos.class);
            query.setParameter("id", p.getId_producto());
            lista_almacenes = query.getSingleResult().getAlma();
        } catch (NoResultException nre) {
            lista_almacenes = new ArrayList<>();
        }

        session.close();
        return lista_almacenes;
    }


    @Override
    public boolean nuevoProductoAlmacen(Productos p, Almacen a) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {

            List<Productos> lista_productos = almacenesconProductos(a);
            lista_productos.add(p);
            a.setProducto(lista_productos);

            List<Almacen> lista_almacenes = almacendelproducto(p);
            lista_almacenes.add(a);
            p.setAlma(lista_almacenes);

            session.beginTransaction();

            session.update(p);
            session.update(a);
            session.getTransaction().commit();

        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }

        session.close();
        return true;
    }


}
