package fi.jperala.worktime.management.model;

import fi.jperala.worktime.model.AbstractWorktimeTableModel;
import fi.jperala.worktime.jpa.domain.ContractType;
import fi.jperala.worktime.jpa.domain.Department;
import fi.jperala.worktime.jpa.domain.Employee;
import fi.jperala.worktime.jpa.impl.EmployeeDAO;

/**
 * Table model for Employee table.
 */
public class EmployeeTableModel extends AbstractWorktimeTableModel<Employee> {

    private final static String[] COLUMN_KEYS = {"employee_id", "employee_firstname", "employee_lastname", "employee_department", "employee_contract_type"};
    private final static Class[] COLUMN_TYPES = {Integer.class, String.class, String.class, Department.class, ContractType.class};

    public EmployeeTableModel(EmployeeDAO dao) {
        super(COLUMN_KEYS, COLUMN_TYPES, dao);
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
                    return e.getContractType();
            }
        }
        return null;
    }
}
