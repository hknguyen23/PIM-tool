package vn.elca.training.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.QEmployee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Employee> findAllByListIds(List<Long> ids) {
        QEmployee employee = QEmployee.employee;

        return new JPAQuery<Employee>(em)
                .from(employee)
                .where(employee.id.in(ids))
                .fetch();
    }
}
