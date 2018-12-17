package com.pavel.jbsrm.user.repository;

import com.pavel.jbsrm.user.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> findAllByPropsMatch(List<String> props);
}
