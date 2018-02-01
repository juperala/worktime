package fi.jperala.worktime.management.model;

import fi.jperala.worktime.model.AbstractWorktimeComboboxModel;
import fi.jperala.worktime.jpa.domain.Department;

/**
 * Combobox model for Department enum.
 */
public class DepartmentComboboxModel extends AbstractWorktimeComboboxModel<Department> {

    public int getSize() {
        return Department.values().length;
    }

    public Department getElementAt(int index) {
        return Department.values()[index];
    }
}
