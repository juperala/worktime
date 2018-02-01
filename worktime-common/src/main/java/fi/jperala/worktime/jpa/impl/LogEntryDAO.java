package fi.jperala.worktime.jpa.impl;

import fi.jperala.worktime.jpa.domain.Employee;
import fi.jperala.worktime.jpa.domain.LogEntry;
import fi.jperala.worktime.jpa.domain.Presence;
import fi.jperala.worktime.util.TimeUtil;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 * LogEntry DAO.
 */
public class LogEntryDAO extends AbstractDAO<LogEntry> {

    public LogEntryDAO() {
        super(LogEntry.class);
    }

    public List<LogEntry> findBy(Employee e, Date from, Date to) {
        initEntityManager();
        Date start = TimeUtil.getThisDate(from);
        Date stop = TimeUtil.getNextDate(to);
        Query query = em.createQuery("Select l from LogEntry l where l.employee = :employee and l.logIn >= :start and l.logIn < :stop and l.logOut is not null order by l.logId asc");
        query.setParameter("employee", e);
        query.setParameter("stop", stop);
        query.setParameter("start", start);
        return query.getResultList();
    }

    public LogEntry findLast(Employee e) {
        initEntityManager();
        Query query = em.createQuery("Select l from LogEntry l where l.employee=:employee order by l.logId desc");
        query.setParameter("employee", e);
        List<LogEntry> list = query.setMaxResults(1).getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public Presence getPresence(Employee e) {
        LogEntry l = findLast(e);
        if (l != null) {
            if (l.getLogOut() == null) {
                if (l.getBreakOut() == null) {
                    if (l.getBreakIn() == null) {
                        if (l.getLogIn() == null) {
                            // no logged events
                            return Presence.OUT;
                        }
                        // logIn was set
                        return Presence.IN;
                    }
                    // breakIn was set
                    return Presence.BREAK;
                }
                // breakOut was set
                return Presence.IN;
            }
            // logOut was set
            return Presence.OUT;
        }
        // no entry found
        return Presence.OUT;
    }
}
