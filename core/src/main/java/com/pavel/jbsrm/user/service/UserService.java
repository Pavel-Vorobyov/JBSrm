package com.pavel.jbsrm.user.service;

import com.pavel.jbsrm.user.UserFilter;
import com.pavel.jbsrm.user.dto.CreateUserDto;
import com.pavel.jbsrm.user.dto.UpdateUserDto;
import com.pavel.jbsrm.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
public interface UserService {

    UserDto create(@Valid CreateUserDto createUserDto);

    Optional<UserDto> update(long id, @Valid UpdateUserDto updateUserDto);

    void updateDeleted(long id, boolean deleted);

    Optional<UserDto> find(long id);

    List<UserDto> findAllByPropsMatch(String searchParams);

    Page<UserDto> findAllPageByFilter(UserFilter filter, Pageable pageable);
}
