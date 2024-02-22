package dao.Productos;

import dto.ProductosDTO;
import org.hibernate.Session;
import entidades.Productos;
import util.HibernateUtil;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductosDAO implements ProductosDAOInterface {


    @Override
    public List<Productos> mostrarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Productos", Productos.class).list();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }


    @Override

    public List<ProductosDTO> devolverNombreImagenes() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consulta HQL
        //  List<Map> muebles = session.createQuery("select new map(m.nombre, m.imagen) from Mueble m", Map.class).list();
        List<ProductosDTO> muebles = session.createQuery("select new dto.ProductosDTO(m.nombre, m.imagen) from Productos m", ProductosDTO.class).list();

        session.close();

        return muebles;
    }


    @Override
    public List<Productos> resumenPaginado(int pagina,int objetos_por_pagina){
        Session session=HibernateUtil.getSessionFactory().openSession();

        Query query=session.createQuery("from Productos",Productos.class);
        query.setMaxResults(objetos_por_pagina);
        query.setFirstResult((pagina -1)*objetos_por_pagina);
        List<Productos> todos=query.list();

        session.close();

        return todos;
    }

    @Override
    public Long totalMuebles() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Long contador = (Long) session.createQuery("select count(e) from Productos e").getSingleResult();

        session.close();

        return contador;
    }


    @Override
    public  List<Productos> buscarporNombre(String busqueda){
        Session session=HibernateUtil.getSessionFactory().openSession();

        Query<Productos> query=session.createQuery("from Productos p where p.nombre like :busqueda",Productos.class);
        List<Productos> filtro=query.setParameter("busqueda","%"+busqueda+"%").list();
        session.close();

        return filtro;
    }

    @Override
    public List<Productos> buscarEntrePrecios(Double min, Double max) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consulta HQL

        Query<Productos> query = session.createQuery("from Productos p where p.precio_uni>=:min and p.precio_uni<=:max", Productos.class);
        query.setParameter("min", min);
        query.setParameter("max", max);
        List<Productos> filtro = query.list();
        session.close();

        return filtro;
    }





    @Override
    public List<Productos> buscarEntrePreciosProveedor(Double min, Double max, String proveedor) {
        List<Productos> filtro=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            // Consulta HQL
            Query<Productos> query = session.createQuery("from Productos p where p.precio_uni>=:min and p.precio_uni<=:max and p.proveedor like :proveedor", Productos.class);
            query.setParameter("min", min);
            query.setParameter("max", max);
            query.setParameter("proveedor","%"+proveedor+"%").list();
            filtro = query.list();
            session.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return filtro;
    }

    @Override
    public List<Productos> buscarEntrePreciosCategorias(Double min, Double max, List<String > categorias) {
        List<Productos> filtro=new ArrayList<>();

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            // Consulta HQL
            Query<Productos> query = session.createQuery("from Productos as p where p.precio_uni>=:min and p.precio_uni<=:max and p.categoria in (:categorias)", Productos.class);
            query.setParameter("min", min);
            query.setParameter("max", max);
            query.setParameterList("categorias", categorias);
            filtro = query.list();
            session.close();
            System.out.println("Consulta HQL: " + query.getQueryString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return filtro;
    }


    @Override
    public List<Productos> mayormenorPrecios() {
        List<Productos> filtro=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            // Consulta HQL
            Query<Productos> query = session.createQuery("from Productos ORDER by Precio_Unitario DESC", Productos.class);
            filtro = query.list();
            session.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return filtro;
    }
    @Override
    public List<Productos> menormayorPrecios() {
        List<Productos> filtro=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            // Consulta HQL
            Query<Productos> query = session.createQuery("from Productos ORDER by Precio_Unitario ASC", Productos.class);
            filtro = query.list();
            session.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return filtro;
    }






    @Override
    public List<String> devolverTodasImages() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consulta HQL
        List<String> imagenes = session.createQuery("select m.imagen from Productos m", String.class).list();
        session.close();

        return imagenes;
    }





    public Double mediaPrecios() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Double> query = session.createQuery("select avg(precio_uni) from Productos", Double.class);
        Double media = (query.getSingleResult());

        session.close();

        return media;
    }


    /*
    fecha
     */





    @Override
    public Productos update(Productos productos) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.update(productos);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return productos;
    }


    @Override
    public boolean deleteById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Productos productos = this.buscarPorId(id);
            if (productos != null) {
                session.delete(productos);
            } else {
                return false;
            }
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean deleteAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            String deleteQuery = "DELETE FROM Mueble";
            Query query = session.createQuery(deleteQuery);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }

        return true;
    }


    @Override
    public Long totalProductos() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Long contador = (Long) session.createQuery("select count(e) from Productos e").getSingleResult();

        session.close();

        return contador;
    }

    @Override
    public Productos buscarPorId(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Productos employee = session.find(Productos.class, id);
        session.close();

        return employee;
    }


    @Override
    public Productos create(Productos productos) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

        try {
            session.beginTransaction();
            session.save(productos);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();

    }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return productos;
    }





}
