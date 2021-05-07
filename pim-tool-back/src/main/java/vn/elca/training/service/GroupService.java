package vn.elca.training.service;

import vn.elca.training.model.entity.Group;

import java.util.List;

public interface GroupService {
    List<Group> findAll();

    long count();

    Group findOne(Long id);

    Group save(Group groupz);
}
