package dao.Asociaciones;

import entidades.Productos;
import entidades.Proveedores;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.NoResultException;

public class AsociacionesDAO implements AsociacionesDAOInterface{

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


}
