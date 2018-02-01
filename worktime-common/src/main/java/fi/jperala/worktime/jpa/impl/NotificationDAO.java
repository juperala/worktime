package fi.jperala.worktime.jpa.impl;

import fi.jperala.worktime.jpa.domain.Department;
import fi.jperala.worktime.jpa.domain.Notification;
import fi.jperala.worktime.util.TimeUtil;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

/**
 * Notification DAO.
 */
public class NotificationDAO extends AbstractDAO<Notification> {

    public NotificationDAO() {
        super(Notification.class);
    }
    
    public List<Notification> findActiveNotifications(Department dep) {
        initEntityManager();
        Query query = em.createQuery("Select n from Notification n where n.department = :dep and n.validFrom <= :today and n.validTo >= :today order by n.validFrom desc");
        query.setParameter("dep", dep);
        query.setParameter("today", TimeUtil.getToday());
        query.setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);
        return query.getResultList();
    } 

    public int deleteOldNotifications() {
        initEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("DELETE FROM Notification n WHERE n.validTo < :validTo");
        query.setParameter("validTo", TimeUtil.getToday());
        int count = query.executeUpdate();
        em.getTransaction().commit();
        return count;
    }
}
