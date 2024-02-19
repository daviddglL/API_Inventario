package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;

public class HibernateUtil {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory(){
        if (sessionFactory==null){
            try {
                //crear el registro
                registry=new StandardServiceRegistryBuilder().configure().build();
                //crear el MetadataSources
                MetadataSources sources=new MetadataSources(registry);
                //crear el Metadata
                Metadata metadata=sources.getMetadataBuilder().build();
                //crear la SessionFactory
                sessionFactory=metadata.getSessionFactoryBuilder().build();
            }catch (Exception e ){
                e.printStackTrace();
                if (registry!=null)
                    StandardServiceRegistryBuilder.destroy(registry);
            }
        }return sessionFactory;
    }
    public static void shutdown(){
        if (registry!=null)
            StandardServiceRegistryBuilder.destroy(registry);
    }
}
