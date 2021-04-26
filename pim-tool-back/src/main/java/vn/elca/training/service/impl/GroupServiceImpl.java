package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Groupz;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.GroupNotFoundException;
import vn.elca.training.model.exception.ProjectNotFoundException;
import vn.elca.training.repository.GroupRepository;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.GroupService;

import java.util.List;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public List<Groupz> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public long count() {
        return groupRepository.count();
    }

    @Override
    public Groupz findOne(Long id) {
        Groupz groupz = groupRepository.findById(id).orElse(null);
        if (groupz == null) {
            throw new GroupNotFoundException("Group not found!!!");
        }
        return groupz;
    }

    @Override
    public Groupz save(Groupz groupz) {
        return groupRepository.save(groupz);
    }
}
