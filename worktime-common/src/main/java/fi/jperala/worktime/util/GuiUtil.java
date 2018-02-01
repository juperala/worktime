package fi.jperala.worktime.util;

import fi.jperala.worktime.jpa.domain.ContractType;
import fi.jperala.worktime.jpa.domain.Employee;
import fi.jperala.worktime.jpa.domain.LogEntry;
import fi.jperala.worktime.jpa.domain.Notification;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities for handling GUI content and formatting.
 */
public class GuiUtil {

    private static final Logger LOG = LoggerFactory.getLogger(GuiUtil.class);

    private static final int FIELD_SIZE = 11;
    private static final String ICON_PATH = "/images/logo.png";

    private static final String ABOUT_MESSAGE = "Worktime - Timecard software.";
    private static final String ABOUT_TITLE = "About";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String CELL_DIV = "|";
    public static final String ENTRY_DIV = ": ";
    public static final String LINE_BREAK = "\n";
    public static final String LINE_SEPARATOR = "===============================================================================================";
    public static final String END_OF_DOCUMENT = "End of Document";

    private GuiUtil() {
    }

    public static Integer getInt(JTextComponent c) {
        try {
            return Integer.parseInt(c.getText());
        } catch (NumberFormatException nfe) {
            LOG.warn("Failed to parse integer, input=({})", c.getText());
            return null;
        } catch (NullPointerException npe) {
            LOG.warn("Failed to parse integer, input=(null)");
            return null;
        }
    }

    public static void setInt(JTextComponent c, Integer value) {
        if (value == null) {
            c.setText(null);
        } else {
            c.setText(value.toString());
        }
    }

    public static String getString(JTextComponent c) {
        if (c.getText() != null) {
            if (c.getText().isEmpty()) {
                return null;
            }
            return c.getText();
        }
        return null;
    }

    public static void setString(JTextComponent c, String value) {
        c.setText(value);
    }

    public static String formatTime(Date date) {
        String timeString = "";
        if (date != null) {
            timeString = new SimpleDateFormat(TIME_FORMAT).format(date);
        }
        return GuiUtil.leftPadSingleSpaceRight(timeString, FIELD_SIZE);
    }

    public static String formatDate(Date date) {
        String dateString = "";
        if (date != null) {
            dateString = new SimpleDateFormat(DATE_FORMAT).format(date);
        }
        return GuiUtil.rightPad(dateString, FIELD_SIZE);
    }

    public static String formatDuration(Duration dur) {
        String durString = "";
        if (dur != null) {
            PeriodFormatter formatter = new PeriodFormatterBuilder()
                    .printZeroAlways()
                    .minimumPrintedDigits(2)
                    .appendHours()
                    .appendSuffix(":")
                    .appendMinutes()
                    .appendSuffix(":")
                    .appendSeconds()
                    .toFormatter();
            durString = formatter.print(dur.toPeriod());
        }
        return GuiUtil.leftPadSingleSpaceRight(durString, FIELD_SIZE);
    }

    public static String leftPadSingleSpaceRight(String string, int fieldSize) {
        return String.format("%1$" + (fieldSize - 1) + "s ", string);
    }

    public static String rightPad(String string, int fieldSize) {
        return String.format("%1$-" + fieldSize + "s", string);
    }

    public static String getFormattedLogEntry(LogEntry l) {
        StringBuilder builder = new StringBuilder();
        builder.append(formatDate(l.getDate())).append(CELL_DIV);
        builder.append(formatTime(l.getLogIn())).append(CELL_DIV);
        builder.append(formatTime(l.getBreakIn())).append(CELL_DIV);
        builder.append(formatTime(l.getBreakOut())).append(CELL_DIV);
        builder.append(formatTime(l.getLogOut())).append(CELL_DIV);
        builder.append(formatDuration(l.getTotal())).append(CELL_DIV);
        if (l.getEmployee().getContractType() == ContractType.MONTHLY_CONTRACT) {
            builder.append(formatDuration(l.getOt50Hours())).append(CELL_DIV);
            builder.append(formatDuration(l.getOt100Hours())).append(LINE_BREAK);
        } else {
            builder.append(LINE_BREAK);
        }
        return builder.toString();
    }

    public static String getEmployeeHeader(Employee e) {
        LocalizationUtil lu = LocalizationUtil.getInstance();
        StringBuilder builder = new StringBuilder();
        builder.append(getEmployeeInfo(e));
        builder.append(LINE_BREAK);
        builder.append(lu.getLocalizedString(LocalizationUtil.HOUR_HEADER));
        builder.append(LINE_SEPARATOR).append(LINE_BREAK);
        return builder.toString();
    }

    public static String getEmployeeInfo(Employee e) {
        LocalizationUtil lu = LocalizationUtil.getInstance();
        StringBuilder builder = new StringBuilder();
        if (e.getEmployeeId() != null) {
            builder.append(lu.getLocalizedString(LocalizationUtil.EMPLOYEE_ID)).append(ENTRY_DIV).append(e.getEmployeeId()).append(LINE_BREAK);
        }
        builder.append(lu.getLocalizedString(LocalizationUtil.EMPLOYEE_NAME)).append(ENTRY_DIV).append(e.getLastName()).append(", ").append(e.getFirstName()).append(LINE_BREAK);
        builder.append(lu.getLocalizedString(LocalizationUtil.EMPLOYEE_CONTRACT_TYPE)).append(ENTRY_DIV).append(lu.getContractType(e.getContractType())).append(LINE_BREAK);
        builder.append(lu.getLocalizedString(LocalizationUtil.EMPLOYEE_DEPARTMENT)).append(ENTRY_DIV).append(lu.getDepartment(e.getDepartment())).append(LINE_BREAK);
        return builder.toString();
    }

    public static String getNotificationInfo(Notification n) {
        LocalizationUtil lu = LocalizationUtil.getInstance();
        StringBuilder builder = new StringBuilder();
        if (n.getNotificationId() != null) {
            builder.append(lu.getLocalizedString(LocalizationUtil.NOTIFICATION_ID)).append(ENTRY_DIV).append(n.getNotificationId()).append(LINE_BREAK);
        }
        builder.append(lu.getLocalizedString(LocalizationUtil.NOTIFICATION_MESSAGE)).append(ENTRY_DIV).append(n.getMessage()).append(LINE_BREAK);
        builder.append(lu.getLocalizedString(LocalizationUtil.NOTIFICATION_DEPARTMENT)).append(ENTRY_DIV).append(lu.getDepartment(n.getDepartment())).append(LINE_BREAK);
        builder.append(lu.getLocalizedString(LocalizationUtil.NOTIFICATION_FROM)).append(ENTRY_DIV).append(formatDate(n.getValidFrom())).append(LINE_BREAK);
        builder.append(lu.getLocalizedString(LocalizationUtil.NOTIFICATION_TO)).append(ENTRY_DIV).append(formatDate(n.getValidTo())).append(LINE_BREAK);
        return builder.toString();
    }

    public static String getTotalHeader(Duration dur) {
        LocalizationUtil lu = LocalizationUtil.getInstance();
        StringBuilder builder = new StringBuilder();
        builder.append(LINE_SEPARATOR).append(LINE_BREAK);
        builder.append(rightPad(lu.getLocalizedString(LocalizationUtil.HOUR_TOTAL) + ":", FIELD_SIZE * 5 + 4)).append(CELL_DIV);
        builder.append(formatDuration(dur)).append(CELL_DIV);
        builder.append(LINE_BREAK).append(LINE_BREAK).append(LINE_BREAK);
        return builder.toString();
    }

    public static boolean confirmDialog(Component parent, String title, String message, boolean warning) {
        int selection;
        if (warning) {
            selection = JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        } else {
            selection = JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        }
        return selection == JOptionPane.YES_OPTION;
    }

    public static int optionDialog(Component parent, String title, String message, String[] options) {
        return JOptionPane.showOptionDialog(parent, message, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    public static void errorDialog(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void aboutDialog(Component parent) {
        JOptionPane.showMessageDialog(parent, ABOUT_MESSAGE, ABOUT_TITLE, JOptionPane.PLAIN_MESSAGE);
    }

    public static void messageDialog(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    public static BufferedImage getIcon() {
        try {
            return ImageIO.read(GuiUtil.class.getResource(ICON_PATH));
        } catch (IOException ex) {
            LOG.warn("Failed to load icon image: {}", ex.getMessage());
            return null;
        }
    }
}
