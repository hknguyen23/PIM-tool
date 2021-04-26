package vn.elca.training.service;

import vn.elca.training.model.entity.Groupz;

import java.util.List;

public interface GroupService {
    List<Groupz> findAll();

    long count();

    Groupz findOne(Long id);

    Groupz save(Groupz groupz);
}
