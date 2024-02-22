package dao.Almacen;

import entidades.Almacen;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlmacenDAO implements AlmacenDAOInterface{


    @Override
    public List<Almacen> mostrarTodos(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Almacen> todos=session.createQuery("from Almacen",Almacen.class).list();
        session.close();

        return todos;
    }


    @Override
    public List<Almacen> menormayorFecha() {
        List<Almacen> filtro=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            // Consulta HQL
            Query<Almacen> query = session.createQuery("from Almacen ORDER by Fecha_Inscripcion ASC", Almacen.class);
            filtro = query.list();
            session.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return filtro;
    }


    @Override
    public List<Almacen> mayormenorFecha() {
        List<Almacen> filtro=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            // Consulta HQL
            Query<Almacen> query = session.createQuery("from Almacen ORDER by Fecha_Inscripcion DESC", Almacen.class);
            filtro = query.list();
            session.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return filtro;
    }


    @Override
    public List<Almacen> buscarporFechas(LocalDate min_fech, LocalDate max_fech) {
        List<Almacen> filtro=new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {


            // Consulta HQL
            Query<Almacen> query = session.createQuery("from Almacen p where p.fecha_inscripcion>=:min_fech and p.fecha_inscripcion<=:max_fech", Almacen.class);
            query.setParameter("min_fech", min_fech);
            query.setParameter("max_fech", max_fech);
            filtro = query.list();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return filtro;
    }

}
