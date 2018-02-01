package fi.jperala.worktime.management.model;

import fi.jperala.worktime.model.AbstractWorktimeComboboxModel;
import fi.jperala.worktime.jpa.domain.Employee;
import fi.jperala.worktime.jpa.impl.EmployeeDAO;
import java.util.Iterator;
import java.util.List;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Combobox model for employees
 */
public class EmployeeComboboxModel  extends AbstractWorktimeComboboxModel<Employee> {

    private final EmployeeDAO dao;
    private List<Employee> data;
    
    public EmployeeComboboxModel(EmployeeDAO dao) {
        super();
        this.dao = dao;
        refresh();
    }

    public final void refresh() {
        data = dao.findAll();
        Iterator<ListDataListener> iter = listeners.iterator();

        ListDataEvent event = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, 0);
        while(iter.hasNext()) {
            iter.next().contentsChanged(event);
        }
    }
    public int getSize() {
        return data.size();
    }

    public Employee getElementAt(int index) {
        return data.get(index);
    }
}