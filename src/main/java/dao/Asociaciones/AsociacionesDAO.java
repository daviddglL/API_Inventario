package dao.Asociaciones;


import entidades.Almacen;
import entidades.Productos;
import entidades.Proveedores;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import java.util.ArrayList;
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
        Proveedores proveedor=null;
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
    public ArrayList<Productos> obtenerProductosProveedor(Proveedores p) {
        ArrayList<Productos> proveedor=null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Proveedores> query = session.createQuery("select e from Proveedores e join fetch e.prod where e.id =:id_producto", Proveedores.class);
            query.setParameter("id_producto", p.getProd());
            proveedor = new ArrayList<>();

        } catch (Exception nre) {
            System.out.println(nre);
            proveedor= null;
        }

        session.close();
        return proveedor;
    }





}
