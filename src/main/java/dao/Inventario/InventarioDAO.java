package dao.Inventario;

import entidades.Inventarios;
import entidades.Productos;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO implements InventarioDAOInterface {

    @Override
    public List<Inventarios> mostrarTodos(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Inventarios> todos=session.createQuery("from Inventarios",Inventarios.class).list();
        session.close();

        return todos;
    }


    @Override
    public List<Inventarios> menormayorFecha() {
        List<Inventarios> filtro=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            // Consulta HQL
            Query<Inventarios> query = session.createQuery("from Inventarios ORDER by Fecha_Inscripcion ASC", Inventarios.class);
            filtro = query.list();
            session.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return filtro;
    }

    @Override
    public List<Inventarios> mayormenorFecha() {
        List<Inventarios> filtro=new ArrayList<>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            // Consulta HQL
            Query<Inventarios> query = session.createQuery("from Inventarios ORDER by Fecha_Inscripcion DESC", Inventarios.class);
            filtro = query.list();
            session.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return filtro;
    }


    @Override
    public List<Inventarios> buscarporFechas(LocalDate min_fech, LocalDate max_fech) {
        List<Inventarios> filtro=new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {


            // Consulta HQL
            Query<Inventarios> query = session.createQuery("from Inventarios p where p.fecha_inscripcion>=:min_fech and p.fecha_inscripcion<=:max_fech", Inventarios.class);
            query.setParameter("min_fech", min_fech);
            query.setParameter("max_fech", max_fech);
            filtro = query.list();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return filtro;
    }
}
