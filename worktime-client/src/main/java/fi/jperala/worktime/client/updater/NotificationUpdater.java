package fi.jperala.worktime.client.updater;

import fi.jperala.worktime.jpa.domain.Department;
import fi.jperala.worktime.jpa.domain.Notification;
import fi.jperala.worktime.jpa.impl.NotificationDAO;
import fi.jperala.worktime.util.GuiUtil;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Runnable handling updating notifications.
 */
public class NotificationUpdater implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationUpdater.class);

    private final JTextArea textArea;
    private final NotificationDAO dao;
    private final Department department;

    public NotificationUpdater(NotificationDAO dao, Department department, JTextArea textArea) {
        this.textArea = textArea;
        this.department = department;
        this.dao = dao;
    }

    public void run() {
        try {
            // release entity managers to sync stale data
            dao.releaseEntityManager();
            List<Notification> notifications = dao.findActiveNotifications(department);
            textArea.setText(null);
            Iterator<Notification> iter = notifications.iterator();
            while (iter.hasNext()) {
                Notification n = iter.next();
                StringBuilder builder = new StringBuilder();
                builder.append("[ ").append(GuiUtil.formatDate(n.getValidFrom()));
                builder.append("- ");
                builder.append(GuiUtil.formatDate(n.getValidTo())).append("] ");
                builder.append(n.getMessage()).append(GuiUtil.LINE_BREAK);
                textArea.append(builder.toString());
            }
        } catch (Exception e) {
            LOG.warn("Failed to udpdate employee list: {}", e.getMessage());
        }
    }

}
