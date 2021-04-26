package vn.elca.training.service.impl.dummy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.ProjectService;

import java.util.List;

/**
 * @author gtn
 *
 */
@Service("second")
@Transactional
@Profile("dummy")
public class SecondDummyProjectServiceImpl extends AbstractDummyProjectService implements ProjectService {

    @Override
    public List<Project> findAll() {
        throw new UnsupportedOperationException("This is second dummy service");
    }

    @Override
    public Page<Project> findAllWithPagination(int page, int size, String status, String searchValue) {
        throw new UnsupportedOperationException("This is second dummy service");
    }

    @Override
    public long count() {
        printCurrentActiveProfiles();
        throw new UnsupportedOperationException("This is second dummy service");
    }

    @Override
    public Project findOne(Long id) {
        throw new UnsupportedOperationException("This is second dummy service");
    }

    @Override
    public Project update(Project project, Long version) {
        throw new UnsupportedOperationException("This is second dummy service");
    }

    @Override
    public Project save(Project project) {
        throw new UnsupportedOperationException("This is second dummy service");
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("This is second dummy service");
    }

    @Override
    public Project createMaintenanceProject(Project oldProject) {
        throw new UnsupportedOperationException("This is second dummy service");
    }

    @Override
    public Boolean checkExistProjectNumber(Long projectNumber) {
        throw new UnsupportedOperationException("This is second dummy service");
    }
}
