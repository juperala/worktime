package fi.jperala.worktime.util;

import fi.jperala.worktime.jpa.domain.ContractType;
import fi.jperala.worktime.jpa.domain.Department;
import fi.jperala.worktime.jpa.domain.Presence;
import java.util.ResourceBundle;
import javax.swing.UIManager;

/**
 * Utility for handling localized Strings.
 */
public class LocalizationUtil {

    public static final String BUNDLE_NAME = "Localization";
    public static final String EMPLOYEE_ID = "employee_id";
    public static final String EMPLOYEE_NAME = "employee_name";
    public static final String EMPLOYEE_CONTRACT_TYPE = "employee_contract_type";
    public static final String EMPLOYEE_DEPARTMENT = "employee_department";
    public static final String HOUR_HEADER = "hour_header";
    public static final String HOUR_TOTAL = "hour_total";
    public static final String NOTIFICATION_ID = "notification_id";
    public static final String NOTIFICATION_MESSAGE = "notification_text";
    public static final String NOTIFICATION_FROM = "notification_from";
    public static final String NOTIFICATION_TO = "notification_to";
    public static final String NOTIFICATION_DEPARTMENT = "notification_target";

    private static LocalizationUtil singleton;

    private final ResourceBundle bundle;

    public static synchronized LocalizationUtil getInstance() {
        if (singleton == null) {
            singleton = new LocalizationUtil();
        }
        return singleton;
    }

    private LocalizationUtil() {
        bundle = ResourceBundle.getBundle(BUNDLE_NAME);
    }

    public String getDepartment(Department dep) {
        String key = dep.getClass().getSimpleName() + "_" + dep.toString();
        return bundle.getString(key.toLowerCase());
    }

    public String getContractType(ContractType ct) {
        String key = ct.getClass().getSimpleName() + "_" + ct.toString();
        return bundle.getString(key.toLowerCase());
    }

    public String getPresence(Presence presence) {
        String key = presence.getClass().getSimpleName() + "_" + presence.toString();
        return bundle.getString(key.toLowerCase());
    }

    public String getLocalizedString(String key) {
        return bundle.getString(key);
    }

    public void initOptionPaneLocalization() {
        UIManager.put("OptionPane.yesButtonText", bundle.getString("button_yes"));
        UIManager.put("OptionPane.noButtonText", bundle.getString("button_no"));
    }

    public String[] getLogInChoiceOptions() {
        String[] options = new String[3];
        options[0] = getLocalizedString("input_opt_out");
        options[1] = getLocalizedString("input_opt_break");
        options[2] = getLocalizedString("input_opt_cancel");
        return options;
    }
}
