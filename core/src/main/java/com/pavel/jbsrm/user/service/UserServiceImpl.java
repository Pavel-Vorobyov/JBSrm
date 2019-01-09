package com.pavel.jbsrm.user.service;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
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

    private UserRepository userRepository;
    private JavaMailSender mailSender;

    public UserServiceImpl(UserRepository userRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @Transactional
    @Override
    public Optional<UserDto> create(@Valid CreateUserDto createUserDto) {
        User user = ObjectMapperUtills.mapTo(createUserDto, User.class);

        return Optional.of(ObjectMapperUtills.mapTo(userRepository.save(user), UserDto.class));
    }

    @Transactional
    @Override
    public Optional<UserDto> update(long id, @Valid UpdateUserDto updateUserDto) {
        User user = userRepository.getOne(id);
        ObjectMapperUtills.mapTo(updateUserDto, user);
        user.getCompany().setId(updateUserDto.getCompanyId());

        return Optional.of(ObjectMapperUtills.mapTo(userRepository.save(user), UserDto.class));
    }

    @Override
    public void updateDeleted(long id, boolean deleted) {
        User user = userRepository.getOne(id);
        user.setDeleted(deleted);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> find(long id) {
        return Optional.of(ObjectMapperUtills
                .mapTo(userRepository.findById(id).orElse(User.builder().build()), UserDto.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> findAllByPropsMatch(String searchParams) {
        List<UserDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            List<String> list = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.equals(""))
                    .collect(Collectors.toList());

            userRepository.findAllByPropsMatch(list)
                    .forEach(user -> result.add(ObjectMapperUtills.mapTo(user, UserDto.class)));
        }
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserDto> findAllPageByFilter(UserFilter filter, Pageable pageable) {
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
