package fi.jperala.worktime.management;

import fi.jperala.worktime.jpa.domain.ContractType;
import fi.jperala.worktime.jpa.domain.Department;
import fi.jperala.worktime.jpa.domain.Employee;
import fi.jperala.worktime.jpa.domain.LogEntry;
import fi.jperala.worktime.jpa.domain.Notification;
import fi.jperala.worktime.management.model.NotificationTableModel;
import fi.jperala.worktime.jpa.impl.EmployeeDAO;
import fi.jperala.worktime.jpa.impl.LogEntryDAO;
import fi.jperala.worktime.jpa.impl.NotificationDAO;
import fi.jperala.worktime.management.model.ContractTypeComboboxModel;
import fi.jperala.worktime.management.model.DepartmentComboboxModel;
import fi.jperala.worktime.management.model.EmployeeComboboxModel;
import fi.jperala.worktime.management.model.EmployeeTableModel;
import fi.jperala.worktime.renderer.WorktimeListCellRenderer;
import fi.jperala.worktime.renderer.WorktimeTableCellRenderer;
import fi.jperala.worktime.util.TimeUtil;
import fi.jperala.worktime.util.GuiUtil;
import fi.jperala.worktime.util.LocalizationUtil;
import java.awt.Component;
import java.awt.print.PrinterException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Worktime management main class.
 */
public class WorktimeManagement extends javax.swing.JFrame {

    private static final Logger LOG = LoggerFactory.getLogger(WorktimeManagement.class);

    private final EmployeeDAO employeeDAO;
    private final LogEntryDAO logEntryDAO;
    private final NotificationDAO notificationDAO;

    private final EmployeeComboboxModel employeeComboboxModel;
    private final EmployeeTableModel employeeTableModel;
    private final NotificationTableModel notificationTableModel;

    /**
     * Creates new form WorktimeManagement
     */
    public WorktimeManagement() {

        // dao objects
        employeeDAO = new EmployeeDAO();
        logEntryDAO = new LogEntryDAO();
        notificationDAO = new NotificationDAO();

        // models
        employeeComboboxModel = new EmployeeComboboxModel(employeeDAO);
        employeeTableModel = new EmployeeTableModel(employeeDAO);
        notificationTableModel = new NotificationTableModel(notificationDAO);

        // init default button localization
        LocalizationUtil.getInstance().initOptionPaneLocalization();

        initComponents();

        // Set localized cell renderers.
        employeeTable.getColumnModel().getColumn(3).setCellRenderer(new WorktimeTableCellRenderer());
        employeeTable.getColumnModel().getColumn(4).setCellRenderer(new WorktimeTableCellRenderer());
        notificationTable.getColumnModel().getColumn(1).setCellRenderer(new WorktimeTableCellRenderer());

        // set initial dates
        hourEmplFromDatePicker.setDate(TimeUtil.getFirstDateOfMonth());
        hourEmplToDatePicker.setDate(TimeUtil.getLastDateOfMonth());
        hourCtFromDatePicker.setDate(TimeUtil.getFirstDateOfMonth());
        hourCtToDatePicker.setDate(TimeUtil.getLastDateOfMonth());
        notificationFromDatePicker.setDate(TimeUtil.getToday());
        notificationToDatePicker.setDate(TimeUtil.getLastDateOfMonth());

        // handle employee table selections
        employeeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (employeeTable.getSelectedRow() != -1) {
                        // set selected data to fields
                        int index = employeeTable.convertRowIndexToModel(employeeTable.getSelectedRow());
                        Employee empl = employeeTableModel.getObject(index);
                        GuiUtil.setString(employeeFirstNameTextField, empl.getFirstName());
                        GuiUtil.setString(employeeLastNameTextField, empl.getLastName());
                        employeeDepartmentComboBox.setSelectedItem(empl.getDepartment());
                        employeeDepartmentComboBox.repaint();
                        employeeContractComboBox.setSelectedItem(empl.getContractType());
                        employeeContractComboBox.repaint();
                        // enable buttons
                        employeeUpdateButton.setEnabled(true);
                        employeeDeleteButton.setEnabled(true);
                    } else {
                        GuiUtil.setString(employeeFirstNameTextField, null);
                        GuiUtil.setString(employeeLastNameTextField, null);
                        employeeDepartmentComboBox.setSelectedItem(Department.OFFICE);
                        employeeDepartmentComboBox.repaint();
                        employeeContractComboBox.setSelectedItem(ContractType.MONTHLY_CONTRACT);
                        employeeContractComboBox.repaint();
                        // enable buttons
                        employeeUpdateButton.setEnabled(false);
                        employeeDeleteButton.setEnabled(false);
                    }
                }
            }
        });

        // handle notification table selections
        notificationTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (notificationTable.getSelectedRow() != -1) {
                        // set selected data to fields
                        int index = notificationTable.convertRowIndexToModel(notificationTable.getSelectedRow());
                        Notification notification = notificationTableModel.getObject(index);
                        GuiUtil.setString(notificationTextField, notification.getMessage());
                        notificationFromDatePicker.setDate(notification.getValidFrom());
                        notificationToDatePicker.setDate(notification.getValidTo());
                        notificationTargetComboBox.setSelectedItem(notification.getDepartment());
                        notificationTargetComboBox.repaint();
                        // enable buttons
                        notificationDelSelectedButton.setEnabled(true);
                        notificationUpdateButton.setEnabled(true);
                    } else {
                        GuiUtil.setString(notificationTextField, null);
                        notificationFromDatePicker.setDate(TimeUtil.getToday());
                        notificationToDatePicker.setDate(TimeUtil.getLastDateOfMonth());
                        notificationTargetComboBox.setSelectedItem(Department.OFFICE);
                        notificationTargetComboBox.repaint();
                        // enable buttons
                        notificationDelSelectedButton.setEnabled(false);
                        notificationUpdateButton.setEnabled(false);
                    }
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainTabbedPane = new javax.swing.JTabbedPane();
        employeeSplitPane = new javax.swing.JSplitPane();
        employeeEditPanel = new javax.swing.JPanel();
        employeeFirstNameTextField = new javax.swing.JTextField();
        employeeLastNameTextField = new javax.swing.JTextField();
        employeeFirstNameLabel = new javax.swing.JLabel();
        employeeLastNameLabel = new javax.swing.JLabel();
        employeeDepartmentLabel = new javax.swing.JLabel();
        employeeContractLabel = new javax.swing.JLabel();
        employeeDepartmentComboBox = new javax.swing.JComboBox();
        employeeContractComboBox = new javax.swing.JComboBox();
        employeeAddButton = new javax.swing.JButton();
        employeeUpdateButton = new javax.swing.JButton();
        employeeDeleteButton = new javax.swing.JButton();
        employeeTableScrollPane = new javax.swing.JScrollPane();
        employeeTable = new javax.swing.JTable();
        hourSplitPane = new javax.swing.JSplitPane();
        hourViewPanel = new javax.swing.JPanel();
        hourTextAreaScrollPane = new javax.swing.JScrollPane();
        hourTextArea = new javax.swing.JTextArea();
        hourPrintPanel = new javax.swing.JPanel();
        hourClearButton = new javax.swing.JButton();
        hourPrintButton = new javax.swing.JButton();
        hourTabbedPane = new javax.swing.JTabbedPane();
        hourEmplSearchPanel = new javax.swing.JPanel();
        hourEmployeeComboBox = new javax.swing.JComboBox();
        hourEmplFromDatePicker = new org.jdesktop.swingx.JXDatePicker();
        hourEmployeeLabel = new javax.swing.JLabel();
        hourEmplFromLabel = new javax.swing.JLabel();
        hourEmplToLabel = new javax.swing.JLabel();
        hourEmplToDatePicker = new org.jdesktop.swingx.JXDatePicker();
        hourEmplSearchButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        hourContractLabel = new javax.swing.JLabel();
        hourContractComboBox = new javax.swing.JComboBox();
        hourCtFromLabel = new javax.swing.JLabel();
        hourCtToLabel = new javax.swing.JLabel();
        hourCtSearchButton = new javax.swing.JButton();
        hourCtFromDatePicker = new org.jdesktop.swingx.JXDatePicker();
        hourCtToDatePicker = new org.jdesktop.swingx.JXDatePicker();
        notificationSplitPane = new javax.swing.JSplitPane();
        notificationEditPanel = new javax.swing.JPanel();
        notificationTextLabel = new javax.swing.JLabel();
        notificationTextField = new javax.swing.JTextField();
        notificationFromLabel = new javax.swing.JLabel();
        notificationFromDatePicker = new org.jdesktop.swingx.JXDatePicker();
        notificationToLabel = new javax.swing.JLabel();
        notificationToDatePicker = new org.jdesktop.swingx.JXDatePicker();
        notificationTargetLabel = new javax.swing.JLabel();
        notificationTargetComboBox = new javax.swing.JComboBox();
        notificationAddButton = new javax.swing.JButton();
        notificationDelOldButton = new javax.swing.JButton();
        notificationDelSelectedButton = new javax.swing.JButton();
        notificationUpdateButton = new javax.swing.JButton();
        notificationTableScrollPane = new javax.swing.JScrollPane();
        notificationTable = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Localization"); // NOI18N
        setTitle(bundle.getString("title")); // NOI18N
        setIconImage(GuiUtil.getIcon());

        mainTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mainTabbedPaneStateChanged(evt);
            }
        });

        employeeSplitPane.setDividerLocation(350);
        employeeSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        employeeEditPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("border_employee_edit"))); // NOI18N

        employeeFirstNameLabel.setText(bundle.getString("employee_firstname")); // NOI18N

        employeeLastNameLabel.setText(bundle.getString("employee_lastname")); // NOI18N

        employeeDepartmentLabel.setText(bundle.getString("employee_department")); // NOI18N

        employeeContractLabel.setText(bundle.getString("employee_contract_type")); // NOI18N

        employeeDepartmentComboBox.setModel(new DepartmentComboboxModel());
        employeeDepartmentComboBox.setRenderer(new WorktimeListCellRenderer());

        employeeContractComboBox.setModel(new ContractTypeComboboxModel());
        employeeContractComboBox.setRenderer(new WorktimeListCellRenderer());

        employeeAddButton.setText(bundle.getString("button_employee_add")); // NOI18N
        employeeAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeAddButtonActionPerformed(evt);
            }
        });

        employeeUpdateButton.setText(bundle.getString("button_employee_update")); // NOI18N
        employeeUpdateButton.setEnabled(false);
        employeeUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeUpdateButtonActionPerformed(evt);
            }
        });

        employeeDeleteButton.setText(bundle.getString("button_employee_delete")); // NOI18N
        employeeDeleteButton.setEnabled(false);
        employeeDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeDeleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout employeeEditPanelLayout = new javax.swing.GroupLayout(employeeEditPanel);
        employeeEditPanel.setLayout(employeeEditPanelLayout);
        employeeEditPanelLayout.setHorizontalGroup(
            employeeEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeEditPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(employeeEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(employeeEditPanelLayout.createSequentialGroup()
                        .addGroup(employeeEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(employeeLastNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(employeeFirstNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(employeeEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(employeeEditPanelLayout.createSequentialGroup()
                                .addComponent(employeeFirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 269, Short.MAX_VALUE)
                                .addComponent(employeeAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, employeeEditPanelLayout.createSequentialGroup()
                                .addComponent(employeeLastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(employeeUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(employeeEditPanelLayout.createSequentialGroup()
                        .addComponent(employeeContractLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(employeeContractComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(employeeEditPanelLayout.createSequentialGroup()
                        .addComponent(employeeDepartmentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(employeeDepartmentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(employeeDeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        employeeEditPanelLayout.setVerticalGroup(
            employeeEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeEditPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(employeeEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeFirstNameLabel)
                    .addComponent(employeeFirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeAddButton))
                .addGap(18, 18, 18)
                .addGroup(employeeEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeLastNameLabel)
                    .addComponent(employeeLastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeUpdateButton))
                .addGap(18, 18, 18)
                .addGroup(employeeEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeDepartmentLabel)
                    .addComponent(employeeDepartmentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeDeleteButton))
                .addGap(18, 18, 18)
                .addGroup(employeeEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeContractLabel)
                    .addComponent(employeeContractComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(149, Short.MAX_VALUE))
        );

        employeeSplitPane.setRightComponent(employeeEditPanel);

        employeeTableScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("border_employees_list"))); // NOI18N

        employeeTable.setAutoCreateRowSorter(true);
        employeeTable.setModel(employeeTableModel);
        employeeTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        employeeTableScrollPane.setViewportView(employeeTable);

        employeeSplitPane.setLeftComponent(employeeTableScrollPane);

        mainTabbedPane.addTab(bundle.getString("title_employees"), employeeSplitPane); // NOI18N

        hourSplitPane.setDividerLocation(350);
        hourSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        hourViewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("border_hour_list"))); // NOI18N

        hourTextArea.setEditable(false);
        hourTextArea.setColumns(20);
        hourTextArea.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        hourTextArea.setRows(5);
        hourTextAreaScrollPane.setViewportView(hourTextArea);

        hourPrintPanel.setLayout(new java.awt.GridBagLayout());

        hourClearButton.setText(bundle.getString("button_hour_clear")); // NOI18N
        hourClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hourClearButtonActionPerformed(evt);
            }
        });
        hourPrintPanel.add(hourClearButton, new java.awt.GridBagConstraints());

        hourPrintButton.setText(bundle.getString("button_hour_print")); // NOI18N
        hourPrintButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hourPrintButtonActionPerformed(evt);
            }
        });
        hourPrintPanel.add(hourPrintButton, new java.awt.GridBagConstraints());

        javax.swing.GroupLayout hourViewPanelLayout = new javax.swing.GroupLayout(hourViewPanel);
        hourViewPanel.setLayout(hourViewPanelLayout);
        hourViewPanelLayout.setHorizontalGroup(
            hourViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hourViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(hourViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hourTextAreaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 957, Short.MAX_VALUE)
                    .addComponent(hourPrintPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        hourViewPanelLayout.setVerticalGroup(
            hourViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hourViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hourTextAreaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hourPrintPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        hourSplitPane.setTopComponent(hourViewPanel);

        hourTabbedPane.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("border_hour_search"))); // NOI18N

        hourEmployeeComboBox.setModel(employeeComboboxModel);
        hourEmployeeComboBox.setRenderer(new WorktimeListCellRenderer());

        hourEmployeeLabel.setText(bundle.getString("hour_employee")); // NOI18N

        hourEmplFromLabel.setText(bundle.getString("hour_from")); // NOI18N

        hourEmplToLabel.setText(bundle.getString("hour_to")); // NOI18N

        hourEmplSearchButton.setText(bundle.getString("button_hour_search")); // NOI18N
        hourEmplSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hourEmplSearchButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout hourEmplSearchPanelLayout = new javax.swing.GroupLayout(hourEmplSearchPanel);
        hourEmplSearchPanel.setLayout(hourEmplSearchPanelLayout);
        hourEmplSearchPanelLayout.setHorizontalGroup(
            hourEmplSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hourEmplSearchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(hourEmplSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(hourEmplToLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(hourEmplFromLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hourEmployeeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(hourEmplSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(hourEmplSearchPanelLayout.createSequentialGroup()
                        .addComponent(hourEmployeeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 364, Short.MAX_VALUE)
                        .addComponent(hourEmplSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(hourEmplSearchPanelLayout.createSequentialGroup()
                        .addGroup(hourEmplSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hourEmplFromDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hourEmplToDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        hourEmplSearchPanelLayout.setVerticalGroup(
            hourEmplSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hourEmplSearchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(hourEmplSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hourEmplSearchButton)
                    .addComponent(hourEmployeeLabel)
                    .addComponent(hourEmployeeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(hourEmplSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hourEmplFromLabel)
                    .addComponent(hourEmplFromDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(hourEmplSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hourEmplToLabel)
                    .addComponent(hourEmplToDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(161, Short.MAX_VALUE))
        );

        hourTabbedPane.addTab(bundle.getString("title_hour_tab_empl"), hourEmplSearchPanel); // NOI18N

        hourContractLabel.setText(bundle.getString("hour_contract")); // NOI18N

        hourContractComboBox.setModel(new ContractTypeComboboxModel());
        hourContractComboBox.setRenderer(new WorktimeListCellRenderer());

        hourCtFromLabel.setText(bundle.getString("hour_from")); // NOI18N

        hourCtToLabel.setText(bundle.getString("hour_to")); // NOI18N

        hourCtSearchButton.setText(bundle.getString("button_hour_search")); // NOI18N
        hourCtSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hourCtSearchButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(hourCtToLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hourCtFromLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hourContractLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(hourCtFromDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addComponent(hourCtToDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(hourContractComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 364, Short.MAX_VALUE)
                .addComponent(hourCtSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hourContractLabel)
                    .addComponent(hourCtSearchButton)
                    .addComponent(hourContractComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hourCtFromLabel)
                    .addComponent(hourCtFromDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hourCtToLabel)
                    .addComponent(hourCtToDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(160, Short.MAX_VALUE))
        );

        hourTabbedPane.addTab(bundle.getString("title_hour_tab_contract"), jPanel1); // NOI18N

        hourSplitPane.setRightComponent(hourTabbedPane);

        mainTabbedPane.addTab(bundle.getString("title_worktimes"), hourSplitPane); // NOI18N

        notificationSplitPane.setDividerLocation(350);
        notificationSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        notificationEditPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("border_notification_edit"))); // NOI18N

        notificationTextLabel.setText(bundle.getString("notification_text")); // NOI18N

        notificationFromLabel.setText(bundle.getString("notification_from")); // NOI18N

        notificationToLabel.setText(bundle.getString("notification_to")); // NOI18N

        notificationTargetLabel.setText(bundle.getString("notification_target")); // NOI18N

        notificationTargetComboBox.setModel(new DepartmentComboboxModel());
        notificationTargetComboBox.setRenderer(new WorktimeListCellRenderer());

        notificationAddButton.setText(bundle.getString("button_notification_add")); // NOI18N
        notificationAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notificationAddButtonActionPerformed(evt);
            }
        });

        notificationDelOldButton.setText(bundle.getString("button_notification_delete_old")); // NOI18N
        notificationDelOldButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notificationDelOldButtonActionPerformed(evt);
            }
        });

        notificationDelSelectedButton.setText(bundle.getString("button_notification_delete_selected")); // NOI18N
        notificationDelSelectedButton.setEnabled(false);
        notificationDelSelectedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notificationDelSelectedButtonActionPerformed(evt);
            }
        });

        notificationUpdateButton.setText(bundle.getString("button_notification_update")); // NOI18N
        notificationUpdateButton.setEnabled(false);
        notificationUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notificationUpdateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout notificationEditPanelLayout = new javax.swing.GroupLayout(notificationEditPanel);
        notificationEditPanel.setLayout(notificationEditPanelLayout);
        notificationEditPanelLayout.setHorizontalGroup(
            notificationEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificationEditPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(notificationEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(notificationTargetLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(notificationToLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(notificationFromLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(notificationTextLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(notificationEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(notificationEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(notificationTargetComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(notificationToDatePicker, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addComponent(notificationFromDatePicker, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(notificationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(notificationEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, notificationEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(notificationUpdateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(notificationAddButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addComponent(notificationDelSelectedButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(notificationDelOldButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        notificationEditPanelLayout.setVerticalGroup(
            notificationEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificationEditPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(notificationEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(notificationTextLabel)
                    .addComponent(notificationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(notificationAddButton))
                .addGap(18, 18, 18)
                .addGroup(notificationEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(notificationFromLabel)
                    .addComponent(notificationFromDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(notificationUpdateButton))
                .addGap(18, 18, 18)
                .addGroup(notificationEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(notificationToLabel)
                    .addComponent(notificationToDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(notificationDelSelectedButton))
                .addGap(18, 18, 18)
                .addGroup(notificationEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(notificationTargetLabel)
                    .addComponent(notificationTargetComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(notificationDelOldButton))
                .addContainerGap(146, Short.MAX_VALUE))
        );

        notificationSplitPane.setRightComponent(notificationEditPanel);

        notificationTableScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("border_notification_list"))); // NOI18N

        notificationTable.setAutoCreateRowSorter(true);
        notificationTable.setModel(notificationTableModel);
        notificationTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        notificationTableScrollPane.setViewportView(notificationTable);

        notificationSplitPane.setLeftComponent(notificationTableScrollPane);

        mainTabbedPane.addTab(bundle.getString("title_news"), notificationSplitPane); // NOI18N

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainTabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void hourPrintButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hourPrintButtonActionPerformed
        JTextArea printArea = new JTextArea(hourTextArea.getText());
        printArea.append(GuiUtil.END_OF_DOCUMENT);
        printArea.setFont(new java.awt.Font("Monospaced", 0, 8));

        try {
            printArea.print();
        } catch (PrinterException ex) {
            LOG.warn("Failed to print working hours: {}", ex.getMessage());
        }
    }//GEN-LAST:event_hourPrintButtonActionPerformed

    private void notificationAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notificationAddButtonActionPerformed
        if (isValidNotificationData()) {
            Notification n = new Notification();
            n.setMessage(GuiUtil.getString(notificationTextField));
            n.setDepartment((Department) notificationTargetComboBox.getSelectedItem());
            n.setValidFrom(notificationFromDatePicker.getDate());
            n.setValidTo(notificationToDatePicker.getDate());

            LocalizationUtil lu = LocalizationUtil.getInstance();
            String addnotificationString = lu.getLocalizedString("button_notification_add") + "?";
            if (GuiUtil.confirmDialog(this, addnotificationString, addnotificationString + "\n\n" + GuiUtil.getNotificationInfo(n), true)) {
                notificationDAO.create(n);
                notificationTableModel.refresh();
            }
        }
    }//GEN-LAST:event_notificationAddButtonActionPerformed

    private boolean isValidEmployeeData() {
        LocalizationUtil lu = LocalizationUtil.getInstance();
        String missingInputString = lu.getLocalizedString("missing_input");
        if (GuiUtil.getString(employeeFirstNameTextField) == null
                || GuiUtil.getString(employeeLastNameTextField) == null
                || employeeDepartmentComboBox.getSelectedItem() == null
                || employeeContractComboBox.getSelectedItem() == null) {
            GuiUtil.errorDialog(this, missingInputString, missingInputString);
            return false;
        }
        return true;
    }

    private boolean isValidNotificationData() {
        LocalizationUtil lu = LocalizationUtil.getInstance();
        if (GuiUtil.getString(notificationTextField) == null
                || notificationTargetComboBox.getSelectedItem() == null
                || notificationFromDatePicker.getDate() == null
                || notificationToDatePicker.getDate() == null) {
            String missingInputString = lu.getLocalizedString("missing_input");
            GuiUtil.errorDialog(this, missingInputString, missingInputString);
            return false;
        }
        if (notificationFromDatePicker.getDate().compareTo(notificationToDatePicker.getDate()) > 0) {
            String invalidDatesString = lu.getLocalizedString("invalid_dates");
            GuiUtil.errorDialog(this, invalidDatesString, invalidDatesString);
            return false;
        }
        return true;
    }

    private boolean isValidEmplHourSearch() {
        LocalizationUtil lu = LocalizationUtil.getInstance();
        if (hourEmployeeComboBox.getSelectedItem() == null
                || hourEmplFromDatePicker.getDate() == null
                || hourEmplToDatePicker.getDate() == null) {
            String missingInputString = lu.getLocalizedString("missing_input");
            GuiUtil.errorDialog(this, missingInputString, missingInputString);
            return false;
        }
        if (hourEmplFromDatePicker.getDate().compareTo(hourEmplToDatePicker.getDate()) > 0) {
            String invalidDatesString = lu.getLocalizedString("invalid_dates");
            GuiUtil.errorDialog(this, invalidDatesString, invalidDatesString);
            return false;
        }
        return true;
    }

    private boolean isValidCtHourSearch() {
        LocalizationUtil lu = LocalizationUtil.getInstance();
        if (hourContractComboBox.getSelectedItem() == null
                || hourCtFromDatePicker.getDate() == null
                || hourCtToDatePicker.getDate() == null) {
            String missingInputString = lu.getLocalizedString("missing_input");
            GuiUtil.errorDialog(this, missingInputString, missingInputString);
            return false;
        }
        if (hourCtFromDatePicker.getDate().compareTo(hourCtToDatePicker.getDate()) > 0) {
            String invalidDatesString = lu.getLocalizedString("invalid_dates");
            GuiUtil.errorDialog(this, invalidDatesString, invalidDatesString);
            return false;
        }
        return true;
    }

    private void employeeAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeAddButtonActionPerformed
        if (isValidEmployeeData()) {
            Employee e = new Employee();
            e.setFirstName(GuiUtil.getString(employeeFirstNameTextField));
            e.setLastname(GuiUtil.getString(employeeLastNameTextField));
            e.setDepartment((Department) employeeDepartmentComboBox.getSelectedItem());
            e.setContractType((ContractType) employeeContractComboBox.getSelectedItem());

            LocalizationUtil lu = LocalizationUtil.getInstance();
            String addUserString = lu.getLocalizedString("button_employee_add") + "?";
            if (GuiUtil.confirmDialog(this, addUserString, addUserString + "\n\n" + GuiUtil.getEmployeeInfo(e), true)) {
                employeeDAO.create(e);
                employeeTableModel.refresh();
            }
        }
    }//GEN-LAST:event_employeeAddButtonActionPerformed

    private void employeeUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeUpdateButtonActionPerformed
        if (employeeTable.getSelectedRow() != -1) {
            if (isValidEmployeeData()) {
                int index = employeeTable.convertRowIndexToModel(employeeTable.getSelectedRow());
                Employee old = employeeTableModel.getObject(index);

                Employee e = new Employee();
                e.setEmployeeId(old.getEmployeeId());
                e.setFirstName(GuiUtil.getString(employeeFirstNameTextField));
                e.setLastname(GuiUtil.getString(employeeLastNameTextField));
                e.setDepartment((Department) employeeDepartmentComboBox.getSelectedItem());
                e.setContractType((ContractType) employeeContractComboBox.getSelectedItem());

                LocalizationUtil lu = LocalizationUtil.getInstance();
                String addUserString = lu.getLocalizedString("button_employee_update") + "?";
                if (GuiUtil.confirmDialog(this, addUserString, addUserString + "\n\n" + GuiUtil.getEmployeeInfo(e), true)) {
                    employeeDAO.update(e);
                    employeeTableModel.refresh();
                }
            }
        }
    }//GEN-LAST:event_employeeUpdateButtonActionPerformed

    private void employeeDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeDeleteButtonActionPerformed
        if (employeeTable.getSelectedRow() != -1) {
            int index = employeeTable.convertRowIndexToModel(employeeTable.getSelectedRow());
            Employee e = employeeTableModel.getObject(index);

            LocalizationUtil lu = LocalizationUtil.getInstance();
            String addUserString = lu.getLocalizedString("button_employee_delete") + "?";
            if (GuiUtil.confirmDialog(this, addUserString, addUserString + "\n\n" + GuiUtil.getEmployeeInfo(e), true)) {
                employeeDAO.deleteById(e.getEmployeeId());
                employeeTableModel.refresh();
            }
        }
    }//GEN-LAST:event_employeeDeleteButtonActionPerformed

    private void notificationDelOldButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notificationDelOldButtonActionPerformed
        LocalizationUtil lu = LocalizationUtil.getInstance();
        String addnotificationString = lu.getLocalizedString("button_notification_delete_old") + "?";
        if (GuiUtil.confirmDialog(this, addnotificationString, addnotificationString, true)) {
            notificationDAO.deleteOldNotifications();
            notificationTableModel.refresh();
        }
    }//GEN-LAST:event_notificationDelOldButtonActionPerformed

    private void notificationDelSelectedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notificationDelSelectedButtonActionPerformed
        if (notificationTable.getSelectedRow() != -1) {
            int index = notificationTable.convertRowIndexToModel(notificationTable.getSelectedRow());
            Notification n = notificationTableModel.getObject(index);

            LocalizationUtil lu = LocalizationUtil.getInstance();
            String addnotificationString = lu.getLocalizedString("button_notification_delete_selected") + "?";
            if (GuiUtil.confirmDialog(this, addnotificationString, addnotificationString + "\n\n" + GuiUtil.getNotificationInfo(n), true)) {
                notificationDAO.deleteById(n.getNotificationId());
                notificationTableModel.refresh();
            }
        }
    }//GEN-LAST:event_notificationDelSelectedButtonActionPerformed

    private void notificationUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notificationUpdateButtonActionPerformed
        if (notificationTable.getSelectedRow() != -1) {
            if (isValidNotificationData()) {
                int index = notificationTable.convertRowIndexToModel(notificationTable.getSelectedRow());
                Notification old = notificationTableModel.getObject(index);

                Notification n = new Notification();
                n.setNotificationId(old.getNotificationId());
                n.setMessage(GuiUtil.getString(notificationTextField));
                n.setDepartment((Department) notificationTargetComboBox.getSelectedItem());
                n.setValidFrom(notificationFromDatePicker.getDate());
                n.setValidTo(notificationToDatePicker.getDate());

                LocalizationUtil lu = LocalizationUtil.getInstance();
                String addnotificationString = lu.getLocalizedString("button_notification_update") + "?";
                if (GuiUtil.confirmDialog(this, addnotificationString, addnotificationString + "\n\n" + GuiUtil.getNotificationInfo(n), true)) {
                    notificationDAO.update(n);
                    notificationTableModel.refresh();
                }
            }
        }
    }//GEN-LAST:event_notificationUpdateButtonActionPerformed

    private void hourClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hourClearButtonActionPerformed
        hourTextArea.setText(null);
    }//GEN-LAST:event_hourClearButtonActionPerformed

    private void hourEmplSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hourEmplSearchButtonActionPerformed
        if (isValidEmplHourSearch()) {
            hourTextArea.setText(null);
            Employee e = (Employee) hourEmployeeComboBox.getSelectedItem();
            Date from = hourEmplFromDatePicker.getDate();
            Date to = hourEmplToDatePicker.getDate();
            fetchEmployeeHours(e, from, to);
        }
    }//GEN-LAST:event_hourEmplSearchButtonActionPerformed

    private void hourCtSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hourCtSearchButtonActionPerformed
        if (isValidCtHourSearch()) {
            hourTextArea.setText(null);
            ContractType ct = (ContractType) hourContractComboBox.getSelectedItem();
            Date from = hourCtFromDatePicker.getDate();
            Date to = hourCtToDatePicker.getDate();

            List<Employee> emplEntries = employeeDAO.findBy(ct);
            Iterator<Employee> iter = emplEntries.iterator();
            while (iter.hasNext()) {
                Employee e = iter.next();
                fetchEmployeeHours(e, from, to);
            }
        }
    }//GEN-LAST:event_hourCtSearchButtonActionPerformed

    private void fetchEmployeeHours(Employee e, Date from, Date to) {
        List<LogEntry> entries = logEntryDAO.findBy(e, from, to);
        hourTextArea.append(GuiUtil.getEmployeeHeader(e));
        Iterator<LogEntry> iter = entries.iterator();
        Duration total = new Duration(0L);
        while (iter.hasNext()) {
            LogEntry l = iter.next();
            total = total.plus(l.getTotal());
            hourTextArea.append(GuiUtil.getFormattedLogEntry(l));
        }
        hourTextArea.append(GuiUtil.getTotalHeader(total));
    }

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        GuiUtil.aboutDialog(this);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void mainTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mainTabbedPaneStateChanged
        if (mainTabbedPane.equals(evt.getSource())) {
            Component selected = mainTabbedPane.getSelectedComponent();
            if (selected != null) {
                // release entity managers to sync stale data
                employeeDAO.releaseEntityManager();
                logEntryDAO.releaseEntityManager();
                notificationDAO.releaseEntityManager();
                if (selected.equals(employeeSplitPane)) {
                    employeeTableModel.refresh();
                } else if (selected.equals(hourSplitPane)) {
                    employeeComboboxModel.refresh();
                    employeeComboboxModel.setSelectedItem(null);
                } else if (selected.equals(notificationSplitPane)) {
                    notificationTableModel.refresh();
                }
            }
        }
    }//GEN-LAST:event_mainTabbedPaneStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WorktimeManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WorktimeManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WorktimeManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WorktimeManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WorktimeManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JButton employeeAddButton;
    private javax.swing.JComboBox employeeContractComboBox;
    private javax.swing.JLabel employeeContractLabel;
    private javax.swing.JButton employeeDeleteButton;
    private javax.swing.JComboBox employeeDepartmentComboBox;
    private javax.swing.JLabel employeeDepartmentLabel;
    private javax.swing.JPanel employeeEditPanel;
    private javax.swing.JLabel employeeFirstNameLabel;
    private javax.swing.JTextField employeeFirstNameTextField;
    private javax.swing.JLabel employeeLastNameLabel;
    private javax.swing.JTextField employeeLastNameTextField;
    private javax.swing.JSplitPane employeeSplitPane;
    private javax.swing.JTable employeeTable;
    private javax.swing.JScrollPane employeeTableScrollPane;
    private javax.swing.JButton employeeUpdateButton;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton hourClearButton;
    private javax.swing.JComboBox hourContractComboBox;
    private javax.swing.JLabel hourContractLabel;
    private org.jdesktop.swingx.JXDatePicker hourCtFromDatePicker;
    private javax.swing.JLabel hourCtFromLabel;
    private javax.swing.JButton hourCtSearchButton;
    private org.jdesktop.swingx.JXDatePicker hourCtToDatePicker;
    private javax.swing.JLabel hourCtToLabel;
    private org.jdesktop.swingx.JXDatePicker hourEmplFromDatePicker;
    private javax.swing.JLabel hourEmplFromLabel;
    private javax.swing.JButton hourEmplSearchButton;
    private javax.swing.JPanel hourEmplSearchPanel;
    private org.jdesktop.swingx.JXDatePicker hourEmplToDatePicker;
    private javax.swing.JLabel hourEmplToLabel;
    private javax.swing.JComboBox hourEmployeeComboBox;
    private javax.swing.JLabel hourEmployeeLabel;
    private javax.swing.JButton hourPrintButton;
    private javax.swing.JPanel hourPrintPanel;
    private javax.swing.JSplitPane hourSplitPane;
    private javax.swing.JTabbedPane hourTabbedPane;
    private javax.swing.JTextArea hourTextArea;
    private javax.swing.JScrollPane hourTextAreaScrollPane;
    private javax.swing.JPanel hourViewPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton notificationAddButton;
    private javax.swing.JButton notificationDelOldButton;
    private javax.swing.JButton notificationDelSelectedButton;
    private javax.swing.JPanel notificationEditPanel;
    private org.jdesktop.swingx.JXDatePicker notificationFromDatePicker;
    private javax.swing.JLabel notificationFromLabel;
    private javax.swing.JSplitPane notificationSplitPane;
    private javax.swing.JTable notificationTable;
    private javax.swing.JScrollPane notificationTableScrollPane;
    private javax.swing.JComboBox notificationTargetComboBox;
    private javax.swing.JLabel notificationTargetLabel;
    private javax.swing.JTextField notificationTextField;
    private javax.swing.JLabel notificationTextLabel;
    private org.jdesktop.swingx.JXDatePicker notificationToDatePicker;
    private javax.swing.JLabel notificationToLabel;
    private javax.swing.JButton notificationUpdateButton;
    // End of variables declaration//GEN-END:variables

}
