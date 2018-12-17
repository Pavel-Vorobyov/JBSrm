package com.pavel.jbsrm.user.service;

import com.pavel.jbsrm.common.exception.ResourceNotFoundException;
import com.pavel.jbsrm.user.dto.CreateUserDto;
import com.pavel.jbsrm.user.dto.UpdateUserDto;
import com.pavel.jbsrm.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface UserService {

    UserDto create(@Valid CreateUserDto createUserDto);

    UserDto update(long id, @Valid UpdateUserDto updateUserDto) throws ResourceNotFoundException;

    void updateDeleted(long id, boolean deleted) throws ResourceNotFoundException;

    UserDto find(long id);

    List<UserDto> findAllByPropsMatch(String searchParams);

    Page<UserDto> findAllPageByDeleted(boolean deleted, Pageable pageable);
}
