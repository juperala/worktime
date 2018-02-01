package fi.jperala.worktime.renderer;

import fi.jperala.worktime.jpa.domain.ContractType;
import fi.jperala.worktime.jpa.domain.Department;
import fi.jperala.worktime.jpa.domain.Presence;
import fi.jperala.worktime.util.LocalizationUtil;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Table cell renderer with internationalization.
 */
public class WorktimeTableCellRenderer extends DefaultTableCellRenderer {

    private final LocalizationUtil locUtil;

    public WorktimeTableCellRenderer() {
        super();
        locUtil = LocalizationUtil.getInstance();
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Department) {
            Department dep = (Department) value;
            setText(locUtil.getDepartment(dep));
        } else if (value instanceof ContractType) {
            ContractType ct = (ContractType) value;
            setText(locUtil.getContractType(ct));
        } else if (value instanceof Presence) {
            Presence presence = (Presence) value;
            setText(locUtil.getPresence(presence));
        } else {
            super.setValue(value);
        }
    }
}
