package fi.jperala.worktime.jpa.impl;

import fi.jperala.worktime.jpa.domain.ContractType;
import fi.jperala.worktime.jpa.domain.Employee;
import java.util.List;
import javax.persistence.Query;

/**
 * Employee DAO.
 */
public class EmployeeDAO extends AbstractDAO<Employee> {

    public EmployeeDAO() {
        super(Employee.class);
    }
    
    public List<Employee> findBy(ContractType ct) {
        initEntityManager();
        Query query = em.createQuery("Select e from Employee e where e.contractType = :ct order by e.lastName,e.firstName asc");
        query.setParameter("ct", ct);
        return query.getResultList();
    }        
}
