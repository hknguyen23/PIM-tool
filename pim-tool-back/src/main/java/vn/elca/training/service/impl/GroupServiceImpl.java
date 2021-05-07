package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.exception.GroupNotFoundException;
import vn.elca.training.repository.GroupRepository;
import vn.elca.training.service.GroupService;

import java.util.List;


@Service
@Transactional
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public long count() {
        return groupRepository.count();
    }

    @Override
    public Group findOne(Long id) {
        Group group = groupRepository.findById(id).orElse(null);
        if (group == null) {
            throw new GroupNotFoundException("Group not found!!!");
        }
        return group;
    }

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }
}
