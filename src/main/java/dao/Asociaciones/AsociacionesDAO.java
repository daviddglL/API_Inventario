package dao.Asociaciones;

import entidades.Categorias;
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

    @Override
    public List<Categorias> productoCategorias(Long idProducto) {
        List<Categorias> categorias = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Categorias> query = session.createQuery("SELECT t3.Nombre\n" +
                    "FROM Inventarios t1\n" +
                    "INNER JOIN Productos t2 ON t1.id = t2.id_producto\n" +
                    "INNER JOIN producto_categoria pc ON t2.id_producto = pc.id_producto\n" +
                    "INNER JOIN Categoria t3 ON pc.id_categoria = t3.id_categoria\n" +
                    "WHERE t1.id = :id ", Categorias.class);
            query.setParameter("idProducto", idProducto);
            categorias = query.getResultList();
        } catch (NoResultException nre) {
            // Manejar la excepción si no se encuentra ningún resultado
            categorias = new ArrayList<>();
        } finally {
            session.close();
        }

        return categorias;
    }

}
