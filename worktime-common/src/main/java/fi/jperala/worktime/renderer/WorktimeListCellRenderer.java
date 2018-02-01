package fi.jperala.worktime.renderer;

import fi.jperala.worktime.jpa.domain.ContractType;
import fi.jperala.worktime.jpa.domain.Department;
import fi.jperala.worktime.jpa.domain.Employee;
import fi.jperala.worktime.util.LocalizationUtil;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * List cell renderer with internationalization.
 */
public class WorktimeListCellRenderer extends DefaultListCellRenderer {

    private final LocalizationUtil locUtil;

    public WorktimeListCellRenderer() {
        super();
        locUtil = LocalizationUtil.getInstance();  
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Department) {
            Department dep = (Department) value;
            setText(locUtil.getDepartment(dep));
        } else if (value instanceof ContractType) {
            ContractType ct = (ContractType) value;
            setText(locUtil.getContractType(ct));
        } else if (value instanceof Employee) {
            Employee e = (Employee) value;
            setText(e.getLastName() + ", " + e.getFirstName());
        }
        return c;
    }
}
