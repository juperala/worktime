package fi.jperala.worktime.jpa.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

/**
 * LogEntry entity.
 */
@Entity
public class LogEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int MINUTE_IN_SECS = 60;
    private static final int HOUR_IN_MINS = 60;
    private static final int NORMAL_HOURS_IN_SECS = (int) (7.5 * HOUR_IN_MINS * MINUTE_IN_SECS);
    private static final int OT_50_HOURS_IN_SECS = 2 * HOUR_IN_MINS * MINUTE_IN_SECS;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logId;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeId", referencedColumnName = "employeeId")
    private Employee employee;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @Basic(optional = true)
    private Date logIn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @Basic(optional = true)
    private Date breakIn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @Basic(optional = true)
    private Date breakOut;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @Basic(optional = true)
    private Date logOut;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getLogIn() {
        return logIn;
    }

    public void setLogIn(Date logIn) {
        this.logIn = logIn;
    }

    public Date getBreakIn() {
        return breakIn;
    }

    public void setBreakIn(Date breakIn) {
        this.breakIn = breakIn;
    }

    public Date getBreakOut() {
        return breakOut;
    }

    public void setBreakOut(Date breakOut) {
        this.breakOut = breakOut;
    }

    public Date getLogOut() {
        return logOut;
    }

    public void setLogOut(Date logOut) {
        this.logOut = logOut;
    }

    public Date getDate() {
        if (getLogIn() == null) {
            throw new RuntimeException("Can not fetch LogEntry date, no login entry.");
        }
        LocalDate date = new LocalDate(getLogIn());
        return date.toDate();
    }

    public Duration getOt50Hours() {
        Duration total = getTotal();
        if (total.getStandardSeconds() >= (NORMAL_HOURS_IN_SECS + OT_50_HOURS_IN_SECS)) {
            return Duration.standardSeconds(OT_50_HOURS_IN_SECS);
        } else if (total.getStandardSeconds() >= NORMAL_HOURS_IN_SECS) {
            Duration ot50Threshold = Duration.standardSeconds(NORMAL_HOURS_IN_SECS);
            return total.minus(ot50Threshold);
        }
        return null;
    }

    public Duration getOt100Hours() {
        Duration total = getTotal();
        if (total.getStandardSeconds() >= (NORMAL_HOURS_IN_SECS + OT_50_HOURS_IN_SECS)) {
            Duration ot100Threshold = Duration.standardSeconds(NORMAL_HOURS_IN_SECS + OT_50_HOURS_IN_SECS);
            return total.minus(ot100Threshold);
        }
        return null;
    }

    public Duration getTotal() {
        if (getLogIn() == null || getLogOut() == null) {
            throw new RuntimeException("Can not calculate total hours, no login/logout entry.");
        }
        DateTime lIn = new DateTime(getLogIn());
        DateTime lOut = new DateTime(getLogOut());
        if (getBreakOut() == null) {
            return new Duration(lIn, lOut);
        } else {
            DateTime bIn = new DateTime(getBreakIn());
            DateTime bOut = new DateTime(getBreakOut());
            Duration d1 = new Duration(lIn, bIn);
            Duration d2 = new Duration(bOut, lOut);
            return d1.plus(d2);
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logId != null ? logId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogEntry)) {
            return false;
        }
        LogEntry other = (LogEntry) object;
        if ((this.logId == null && other.logId != null) || (this.logId != null && !this.logId.equals(other.logId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fi.jperala.worktime.jpa.domain.LogEntry[ id=" + logId + " ]";
    }

}
