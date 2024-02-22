package dao.Asociaciones;

import dto.CategoriasDTO;
import entidades.Productos;
import entidades.Proveedores;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.NoResultException;
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
    public List<CategoriasDTO> devolverNombreImagenes() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<CategoriasDTO> categorias = session.createQuery("select new dto.CategoriasDTO(m.categoria, m.inventario) from Productos m", CategoriasDTO.class).list();

        session.close();

        return categorias;
    }
//    SELECT t3.Nombre
//    Select t3.Nombre FROM entidades.Inventarios t1
//    INNER JOIN entidades.Productos t2 ON t1.id = t2.id_producto
//    INNER JOIN producto_categoria pc ON t2.id_producto = pc.id_producto
//    INNER JOIN Categoria t3 ON pc.id_categoria = t3.id_categoria
//    WHERE t1.Nombre =:nombre
}
