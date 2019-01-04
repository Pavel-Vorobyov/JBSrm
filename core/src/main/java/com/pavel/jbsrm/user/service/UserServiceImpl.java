package com.pavel.jbsrm.user.service;

import com.pavel.jbsrm.common.exception.ResourceNotFoundException;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.user.QUser;
import com.pavel.jbsrm.user.User;
import com.pavel.jbsrm.user.UserFilter;
import com.pavel.jbsrm.user.dto.CreateUserDto;
import com.pavel.jbsrm.user.dto.UpdateUserDto;
import com.pavel.jbsrm.user.dto.UserDto;
import com.pavel.jbsrm.user.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDto create(@Valid CreateUserDto createUserDto) {
        User user = ObjectMapperUtills.mapTo(createUserDto, User.class);
        return ObjectMapperUtills.mapTo(userRepository.save(user), UserDto.class);
    }

    @Transactional
    @Override
    public UserDto update(long id, @Valid UpdateUserDto updateUserDto) throws ResourceNotFoundException {
//        Optional<User> user = Optional.of(userRepository.getOne(id));
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(cl -> ObjectMapperUtills.mapTo(updateUserDto, cl));
        user.get().getCompany().setId(updateUserDto.getCompanyId());

        return ObjectMapperUtills.mapTo(
                user.map(cl -> userRepository.save(cl)).orElseThrow(ResourceNotFoundException::new), UserDto.class);
    }

    @Override
    public void updateDeleted(long id, boolean deleted) throws ResourceNotFoundException {
        Optional<User> user = Optional.of(userRepository.getOne(id));
        user.orElseThrow(ResourceNotFoundException::new)
                .setDeleted(deleted);
        userRepository.save(user.get());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto find(long id) {
        return ObjectMapperUtills.mapTo(userRepository.findById(id).orElse(User.builder().build()), UserDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> findAllByPropsMatch(String searchParams) {
        List<UserDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            List<String> list = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.equals("")) //todo !s.equals("") check
                    .collect(Collectors.toList());

            userRepository.findAllByPropsMatch(list)
                    .forEach(user -> result.add(ObjectMapperUtills.mapTo(user, UserDto.class)));
        }
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserDto> findAllPageByDeleted(UserFilter filter, Pageable pageable) {
        return userRepository.findAll(buildFilter(filter), pageable)
                .map(user -> ObjectMapperUtills.mapTo(user, UserDto.class));
    }

    private BooleanBuilder buildFilter(UserFilter filter) {
        BooleanBuilder whereBuilder = new BooleanBuilder();

        if (filter != null) {
            if (filter.getDeleted() != null) {
                whereBuilder.and(QUser.user.deleted.eq(filter.getDeleted()));
            }
            if (filter.getBirthday() != null) {
                whereBuilder.and(QUser.user.birthday.eq(filter.getBirthday()));
            }
            if (filter.getCompanyId() != null) {
                whereBuilder.and(QUser.user.company.id.eq(filter.getCompanyId()));
            }
            if (filter.getUserRole() != null) {
                whereBuilder.and(QUser.user.userRole.eq(filter.getUserRole()));
            }
        }
        return whereBuilder;
    }
}
