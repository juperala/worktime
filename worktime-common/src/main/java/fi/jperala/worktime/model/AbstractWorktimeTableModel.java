package fi.jperala.worktime.model;

import fi.jperala.worktime.jpa.impl.AbstractDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.table.AbstractTableModel;

/**
 * Abstract table model.
 * @param <E> Implementing type.
 */
public abstract class AbstractWorktimeTableModel<E> extends AbstractTableModel {

    private final String[] columnKeys;
    private final Class[] columnTypes;
    private final ResourceBundle bundle;
    protected List<E> data;
    protected final AbstractDAO dao;

    public AbstractWorktimeTableModel(String[] columnKeys, Class[] columnTypes, AbstractDAO dao) {
        super();
        this.data = new ArrayList();
        this.columnKeys = columnKeys;
        this.columnTypes = columnTypes;
        this.dao = dao;
        bundle = ResourceBundle.getBundle("Localization");
        refresh();
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return columnKeys.length;
    }

    @Override
    public String getColumnName(int col) {
        return bundle.getString(columnKeys[col]);
    }

    @Override
    public Class getColumnClass(int col) {
        return columnTypes[col];
    }
    
    public E getObject(int index) {
        return data.get(index);
    }
    
    public void refresh() {
        data = dao.findAll();
        fireTableDataChanged();        
    }

}
