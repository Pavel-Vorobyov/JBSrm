package com.pavel.jbsrm.user.service;

import com.pavel.jbsrm.common.auth.UserPrinciple;
import com.pavel.jbsrm.common.mail.MailSender;
import com.pavel.jbsrm.company.Company;
import com.pavel.jbsrm.company.repository.CompanyRepository;
import com.pavel.jbsrm.user.User;
import com.pavel.jbsrm.user.UserRole;
import com.pavel.jbsrm.user.dto.CreateUserDto;
import com.pavel.jbsrm.user.dto.UpdateUserDto;
import com.pavel.jbsrm.user.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private RegistrationLinkManager linkManager;
    @Mock
    private MailSender mailSender;
    @InjectMocks
    private UserServiceImpl userService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void create() {
        String email = "test email";
        String password = "test password";
        long authorId = 2L;
        long authorCompanyId = 3L;
        long userDbId = 5L;
        String authorEmail = "author email";
        String authorPassword = "author password";
        UserRole authorUserRole = UserRole.ROLE_ADMIN;

        CreateUserDto createUserDto = CreateUserDto.builder()
                .email(email)
                .password(password)
                .build();

        Company authorCompany = Company.builder().id(authorCompanyId).build();

        User author = new User();
        author.setId(authorId);
        author.setEmail(authorEmail);
        author.setPassword(authorPassword);
        author.setUserRole(authorUserRole);
        author.setCompany(authorCompany);

        ArgumentCaptor<User> userToSave = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userToSave.capture()))
                .thenAnswer((Answer<User>) invocation -> {
                    User result = invocation.getArgument(0);
                    result.setId(userDbId);
                    return result;
                });

        when(userRepository.getOne(authorId))
                .thenReturn(author);
        when(companyRepository.getOne(authorCompanyId))
                .thenReturn(authorCompany);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        when(authentication.getPrincipal()).thenReturn(UserPrinciple.build(author));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        userService.create(createUserDto);

        verify(linkManager, times(1)).getLink(userDbId);
        verify(userRepository, times(1)).save(any());
        assertThat(userToSave.getValue(), allOf(
                hasProperty("email", is(email)),
                hasProperty("password")
        ));
        assertTrue(passwordEncoder.matches(password, userToSave.getValue().getPassword()));
    }

    @Test
    public void update() {
        String updatedEmail = "updated email";
        String updatedName = "updated name";
        String updatedSurname = "updated surname";

        long userId = 1L;

        User dbUser = new User();
        dbUser.setId(userId);
        dbUser.setEmail("before mapping email");
        dbUser.setName("before mapping name");
        dbUser.setSurname("before mapping surname");

        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .email(updatedEmail)
                .name(updatedName)
                .surname(updatedSurname)
                .build();

        when(userRepository.getOne(userId))
                .thenReturn(dbUser);

        ArgumentCaptor<User> userToSave = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userToSave.capture()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        userService.update(userId, updateUserDto);

        verify(userRepository, times(1)).getOne(userId);
        verify(userRepository, times(1)).save(userToSave.getValue());

        assertThat(userToSave.getValue(), allOf(
                hasProperty("email", is(updatedEmail)),
                hasProperty("name", is(updatedName)),
                hasProperty("surname", is(updatedSurname))
        ));
    }

    @Test
    public void updateDeleted() {
        long userId = 1L;
        String email = "email";

        User dbUser = new User();
        dbUser.setId(userId);
        dbUser.setEmail(email);
        dbUser.setDeleted(false);

        ArgumentCaptor<User> userToSave = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userToSave.capture()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(userRepository.getOne(userId))
                .thenReturn(dbUser);

        userService.updateDeleted(userId, true);

        assertThat(userToSave.getValue(), allOf(
                hasProperty("email", is(email)),
                hasProperty("deleted", is(true))
        ));
    }

    @Test
    public void findAllByPropsMatch() {
        String props = "prop1 prop2 prop3";

        ArgumentCaptor<List<String>> propsList = ArgumentCaptor.forClass(List.class);
        when(userRepository.findAllByPropsMatch(propsList.capture()))
                .thenReturn(new ArrayList<>());

        userService.findAllByPropsMatch(props);

        assertEquals(propsList.getValue().size(), 3);
        assertThat(propsList.getValue(), allOf(
                hasItems("prop1", "prop1", "prop1")
        ));
    }
}