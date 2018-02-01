package fi.jperala.worktime.tracking.model;

import fi.jperala.worktime.jpa.domain.Department;
import fi.jperala.worktime.jpa.domain.Employee;
import fi.jperala.worktime.jpa.domain.Presence;
import fi.jperala.worktime.jpa.impl.EmployeeDAO;
import fi.jperala.worktime.jpa.impl.LogEntryDAO;
import fi.jperala.worktime.model.AbstractWorktimeTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Table model to show employee presence status.
 */
public class EmployeePresenceTableModel extends AbstractWorktimeTableModel<Employee> implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeePresenceTableModel.class);

    private final static String[] COLUMN_KEYS = {"employee_id", "employee_firstname", "employee_lastname", "employee_department", "employee_presence"};
    private final static Class[] COLUMN_TYPES = {Integer.class, String.class, String.class, Department.class, Presence.class};

    private final LogEntryDAO logEntryDAO;

    public EmployeePresenceTableModel(EmployeeDAO employeeDao, LogEntryDAO logEntryDAO) {
        super(COLUMN_KEYS, COLUMN_TYPES, employeeDao);
        this.logEntryDAO = logEntryDAO;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee e = data.get(rowIndex);
        if (e != null) {
            switch (columnIndex) {
                case 0:
                    return e.getEmployeeId();
                case 1:
                    return e.getFirstName();
                case 2:
                    return e.getLastName();
                case 3:
                    return e.getDepartment();
                case 4:
                    return logEntryDAO.getPresence(e);
            }
        }
        return null;
    }

    public void run() {
        try {
            // release entity managers to sync stale data
            dao.releaseEntityManager();
            logEntryDAO.releaseEntityManager();
            refresh();
        } catch (Exception e) {
            LOG.warn("Failed to udpdate employee list: {}", e.getMessage());
        }
    }
}
