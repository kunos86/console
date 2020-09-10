package pl.sda;

import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmployeeEntityManager {
    private static final EmployeeEntityManager INSTANCE = new EmployeeEntityManager();
    @Getter
    private static EntityManagerFactory entityManagerFactory ;

    private EmployeeEntityManager() {

    }

    public static EmployeeEntityManager getInstance(){
        return INSTANCE;
    }

    public static void close(){
        getEntityManagerFactory().close();
    }

    public static void open() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa.workers");
    }
}
