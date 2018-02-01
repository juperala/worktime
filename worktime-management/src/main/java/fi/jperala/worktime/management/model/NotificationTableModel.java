package fi.jperala.worktime.management.model;

import fi.jperala.worktime.model.AbstractWorktimeTableModel;
import fi.jperala.worktime.jpa.domain.Department;
import fi.jperala.worktime.jpa.domain.Notification;
import fi.jperala.worktime.jpa.impl.NotificationDAO;
import java.util.Date;

/**
 * Table model for Notification table.
 */
public class NotificationTableModel extends AbstractWorktimeTableModel<Notification> {

    private final static String[] COLUMN_KEYS = {"notification_id", "notification_target", "notification_from", "notification_to", "notification_text"};
    private final static Class[] COLUMN_TYPES = {Integer.class, Department.class, Date.class, Date.class, String.class};

    public NotificationTableModel(NotificationDAO dao) {
        super(COLUMN_KEYS, COLUMN_TYPES, dao);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Notification n = data.get(rowIndex);
        if (n != null) {
            switch (columnIndex) {
                case 0:
                    return n.getNotificationId();
                case 1:
                    return n.getDepartment();
                case 2:
                    return n.getValidFrom();
                case 3:
                    return n.getValidTo();
                case 4:
                    return n.getMessage();
            }
        }
        return null;
    }

}
