package fi.jperala.worktime.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 * Abstract combobox model.
 *
 * @param <E> Implementing type.
 */
public abstract class AbstractWorktimeComboboxModel<E> implements ComboBoxModel<E> {

    protected final List<ListDataListener> listeners = new ArrayList();
    protected E selected;

    public void setSelectedItem(Object item) {
        selected = (E) item;
    }

    public Object getSelectedItem() {
        return selected;
    }

    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

}
