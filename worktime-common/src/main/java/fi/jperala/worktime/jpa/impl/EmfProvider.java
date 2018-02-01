package fi.jperala.worktime.jpa.impl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Manager for EntityManagerFactory
 */
public class EmfProvider {

    private static final String PERSISTENCE_UNIT = "worktime";
    private static EntityManagerFactory emf;

    private EmfProvider() {
    }

    public static synchronized EntityManagerFactory getEmf() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        }
        return emf;
    }

    public static synchronized void closeEmf() {
        if (emf != null) {
            emf.close();
        } else {
            throw new RuntimeException("No existing EntityManagerFactory to close.");
        }
    }
}
