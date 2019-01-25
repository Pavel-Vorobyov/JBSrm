package com.pavel.jbsrm.user.service;

import com.pavel.jbsrm.common.auth.UserPrinciple;
import com.pavel.jbsrm.common.mail.MailSender;
import com.pavel.jbsrm.common.mail.MailTemplate;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.common.utill.StringConverter;
import com.pavel.jbsrm.company.repository.CompanyRepository;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@EnableAsync
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private RegistrationLinkManager linkManager;
    private MailSender mailSender;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CompanyRepository companyRepository, RegistrationLinkManager linkManager, MailSender mailSender) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.linkManager = linkManager;
        this.mailSender = mailSender;
    }

    @Transactional
    @Override
    public UserDto create(@Valid CreateUserDto createUserDto) {
        if (userRepository.findByEmail(createUserDto.getEmail()).isPresent()) {
            throw new EntityExistsException("Such email is present!");
        }

        createUserDto.setPassword(BCrypt.hashpw(createUserDto.getPassword(), BCrypt.gensalt(10)));
        User userToCreate = ObjectMapperUtills.mapTo(createUserDto, User.class);

        User systemAdmin = userRepository.getOne(((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        userToCreate.setCompany(companyRepository.getOne(systemAdmin.getCompany().getId()));
        User user = userRepository.save(userToCreate);

        sendMail(user.getId(),
                MailTemplate.builder()
                .to(createUserDto.getEmail())
                .subject("Verification")
                .build());

        return ObjectMapperUtills.mapTo(user, UserDto.class);
    }

    @Async
    private void sendMail(long userId, MailTemplate mail) {
        linkManager.getLink(userId)
                .ifPresent(link -> {
                    mail.setText(link);
                    mailSender.sendMail(mail);
                });
    }

    @Transactional
    @Override
    public Optional<UserDto> update(long id, @Valid UpdateUserDto updateUserDto) {
        User user = userRepository.getOne(id);
        user.setId(id);
        ObjectMapperUtills.mapTo(updateUserDto, user);
        user.setCompany(companyRepository.getOne(updateUserDto.getCompanyId()));

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
        return userRepository.findById(id)
                .map(user -> ObjectMapperUtills.mapTo(user, UserDto.class));
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
