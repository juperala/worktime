package fi.jperala.worktime.client;

import fi.jperala.worktime.client.updater.NotificationUpdater;
import fi.jperala.worktime.jpa.domain.Department;
import fi.jperala.worktime.jpa.domain.Employee;
import fi.jperala.worktime.jpa.domain.LogEntry;
import fi.jperala.worktime.jpa.impl.EmployeeDAO;
import fi.jperala.worktime.jpa.impl.LogEntryDAO;
import fi.jperala.worktime.jpa.impl.NotificationDAO;
import fi.jperala.worktime.util.GuiUtil;
import fi.jperala.worktime.util.LocalizationUtil;
import fi.jperala.worktime.util.Settings;
import fi.jperala.worktime.util.TimeUtil;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorktimeClient extends javax.swing.JFrame {

    private static final Logger LOG = LoggerFactory.getLogger(WorktimeClient.class);

    private static final int NOTIFICATION_UPDATE_INTERVAL_SECS = 60;

    private final EmployeeDAO employeeDAO;
    private final LogEntryDAO logEntryDAO;
    private final NotificationDAO notificationDAO;

    private final NotificationUpdater notificationUpdater;

    private final ScheduledExecutorService scheduler;
    
    private final Department department;

    /**
     * Creates new form WorktimeClient
     */
    public WorktimeClient() {
        // dao objects
        employeeDAO = new EmployeeDAO();
        logEntryDAO = new LogEntryDAO();
        notificationDAO = new NotificationDAO();
        
        Department tmp;
        try {
            // department for notifications
            tmp = Settings.getInstance().getDepartment();
        } catch (IOException ex) {
            tmp = Department.OFFICE;
            LOG.warn("Failed to load properties file {}, setting default to {}", Settings.PROPERTY_FILE, LocalizationUtil.getInstance().getDepartment(tmp));
        }
        department = tmp;

        // init default button localization
        LocalizationUtil.getInstance().initOptionPaneLocalization();

        initComponents();

        // notification updater
        notificationUpdater = new NotificationUpdater(notificationDAO, department, notificationTextArea);

        // start scheduler to update view
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(notificationUpdater, 0, NOTIFICATION_UPDATE_INTERVAL_SECS, TimeUnit.SECONDS);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainSplitPane = new javax.swing.JSplitPane();
        notificationScrollPane = new javax.swing.JScrollPane();
        notificationTextArea = new javax.swing.JTextArea();
        loginPanel = new javax.swing.JPanel();
        empIdLabel = new javax.swing.JLabel();
        loginButton = new javax.swing.JButton();
        empIdFormattedTextField = new javax.swing.JFormattedTextField();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Localization"); // NOI18N
        setTitle(bundle.getString("title")); // NOI18N
        setIconImage(GuiUtil.getIcon());

        mainSplitPane.setDividerLocation(250);
        mainSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        notificationScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("title_news"))); // NOI18N

        notificationTextArea.setEditable(false);
        notificationTextArea.setColumns(20);
        notificationTextArea.setRows(5);
        notificationScrollPane.setViewportView(notificationTextArea);

        mainSplitPane.setTopComponent(notificationScrollPane);

        loginPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("title_login_panel"))); // NOI18N

        empIdLabel.setText(bundle.getString("input_employee_id")); // NOI18N

        loginButton.setText(bundle.getString("button_login")); // NOI18N
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        empIdFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(empIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(empIdFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 424, Short.MAX_VALUE)
                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empIdLabel)
                    .addComponent(loginButton)
                    .addComponent(empIdFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(155, Short.MAX_VALUE))
        );

        mainSplitPane.setRightComponent(loginPanel);

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
            .addComponent(mainSplitPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        LocalizationUtil lu = LocalizationUtil.getInstance();

        try {
            Integer id = GuiUtil.getInt(empIdFormattedTextField);
            if (id != null) {
                // release entity managers to sync stale data
                employeeDAO.releaseEntityManager();
                logEntryDAO.releaseEntityManager();

                Employee e = employeeDAO.findOne(id);
                if (e != null) {
                    String employeeString = "\n\n[" + e.getEmployeeId() + "] " + e.getLastName() + ", " + e.getFirstName();
                    String logTitle = lu.getLocalizedString("dialog_employee_login_title");

                    LogEntry l = logEntryDAO.findLast(e);
                    if (l == null || l.getLogOut() != null) {
                        // user is out of office
                        String loginString = lu.getLocalizedString("dialog_employee_login");
                        if (GuiUtil.confirmDialog(this, logTitle, loginString + employeeString, false)) {
                            doLogIn(e);
                        }
                    } else if (l.getBreakOut() != null) {
                        // at work, break already taken
                        String logoutString = lu.getLocalizedString("dialog_employee_logout");
                        if (GuiUtil.confirmDialog(this, logTitle, logoutString + employeeString, false)) {
                            doLogOut(l);
                        }
                    } else if (l.getBreakIn() != null) {
                        // at break
                        String breakOutString = lu.getLocalizedString("dialog_employee_breakout");
                        if (GuiUtil.confirmDialog(this, logTitle, breakOutString + employeeString, false)) {
                            doBreakOut(l);
                        }
                    } else {
                        // at works, break no yet taken
                        String breakInOrLogOutString = lu.getLocalizedString("dialog_employee_breakin_or_logout");
                        String[] options = lu.getLogInChoiceOptions();
                        int sel = GuiUtil.optionDialog(this, logTitle, breakInOrLogOutString + employeeString, options);
                        switch (sel) {
                            case JOptionPane.YES_OPTION:
                                doLogOut(l);
                                break;
                            case JOptionPane.NO_OPTION:
                                doBreakIn(l);
                                break;
                            default:
                                break;
                        }
                    }
                    empIdFormattedTextField.setText(null);
                } else {
                    String employeeNotFoundString = lu.getLocalizedString("employee_not_found");
                    GuiUtil.errorDialog(this, employeeNotFoundString, employeeNotFoundString + ": " + id);
                }
            } else {
                String missingInputString = lu.getLocalizedString("missing_input");
                GuiUtil.errorDialog(this, missingInputString, missingInputString);
            }
        } catch (Exception e) {
            String errorString = lu.getLocalizedString("dialog_employee_login_title");
            GuiUtil.errorDialog(this, errorString, errorString + "\n\nError: " + e.getMessage());
        }
    }//GEN-LAST:event_loginButtonActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        GuiUtil.aboutDialog(this);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

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
            java.util.logging.Logger.getLogger(WorktimeClient.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WorktimeClient.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WorktimeClient.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WorktimeClient.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WorktimeClient().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JFormattedTextField empIdFormattedTextField;
    private javax.swing.JLabel empIdLabel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton loginButton;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JSplitPane mainSplitPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JScrollPane notificationScrollPane;
    private javax.swing.JTextArea notificationTextArea;
    // End of variables declaration//GEN-END:variables

    private void doLogIn(Employee e) {
        LogEntry l = new LogEntry();
        l.setEmployee(e);
        l.setLogIn(new Date());
        logEntryDAO.create(l);
        LocalizationUtil lu = LocalizationUtil.getInstance();
        String logTitle = lu.getLocalizedString("dialog_employee_login_title");
        String successString = lu.getLocalizedString("dialog_employee_login_success");
        GuiUtil.messageDialog(this, logTitle, successString);
    }

    private void doLogOut(LogEntry l) {
        l.setLogOut(new Date());
        logEntryDAO.update(l);
        List<LogEntry> entries = logEntryDAO.findBy(l.getEmployee(), TimeUtil.getFirstDateOfMonth(), TimeUtil.getLastDateOfMonth());
        Iterator<LogEntry> iter = entries.iterator();
        Duration total = new Duration(0L);
        while (iter.hasNext()) {
            total = total.plus(iter.next().getTotal());
        }
        LocalizationUtil lu = LocalizationUtil.getInstance();
        String logTitle = lu.getLocalizedString("dialog_employee_login_title");
        String successString = lu.getLocalizedString("dialog_employee_login_success");
        String saldoTitleString = lu.getLocalizedString("dialog_employee_saldo_title");
        String saldoTodayString = lu.getLocalizedString("dialog_employee_saldo_today");
        String saldoMonthString = lu.getLocalizedString("dialog_employee_saldo_month");
        String saldoMessage = successString + "\n\n" + saldoTitleString + "\n" + saldoTodayString + ": " + GuiUtil.formatDuration(l.getTotal()) + "\n" + saldoMonthString + ": " + GuiUtil.formatDuration(total);
        GuiUtil.messageDialog(this, logTitle, saldoMessage);
    }

    private void doBreakOut(LogEntry l) {
        l.setBreakOut(new Date());
        logEntryDAO.update(l);
        LocalizationUtil lu = LocalizationUtil.getInstance();
        String logTitle = lu.getLocalizedString("dialog_employee_login_title");
        String successString = lu.getLocalizedString("dialog_employee_login_success");
        GuiUtil.messageDialog(this, logTitle, successString);
    }

    private void doBreakIn(LogEntry l) {
        l.setBreakIn(new Date());
        logEntryDAO.update(l);
        LocalizationUtil lu = LocalizationUtil.getInstance();
        String logTitle = lu.getLocalizedString("dialog_employee_login_title");
        String successString = lu.getLocalizedString("dialog_employee_login_success");
        GuiUtil.messageDialog(this, logTitle, successString);
    }
}
