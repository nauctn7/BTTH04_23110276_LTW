package vn.iotstar.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfigs {

    private static EntityManagerFactory factory;

    static {
        try {
            // Tên phải khớp persistence.xml
            factory = Persistence.createEntityManagerFactory("dataSource");
            System.out.println("[JPAConfigs] EntityManagerFactory initialized OK");
        } catch (Throwable ex) {
            System.err.println("[JPAConfigs] Failed to initialize EMF: " + ex);
            ex.printStackTrace(); // In ra nguyên nhân thực (sai JDBC URL, thiếu driver, sai PU name...)
        }
    }

    public static EntityManager getEntityManager() {
        if (factory == null) {
            throw new IllegalStateException(
                "EntityManagerFactory is null. Check persistence.xml location & PU name 'dataSource'. See server logs above.");
        }
        return factory.createEntityManager();
    }

    public static void close() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}
