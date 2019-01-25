package com.pavel.jbsrm.user.service;

import com.pavel.jbsrm.common.auth.UserPrinciple;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.user.User;
import com.pavel.jbsrm.user.UserRole;
import com.pavel.jbsrm.user.dto.CreateUserDto;
import com.pavel.jbsrm.user.dto.UpdateUserDto;
import com.pavel.jbsrm.user.dto.UserDto;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import test.AbstractIntegrationTest;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class UserServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    public void createTest() {
        String email = "testEmail";
        String password = "testPassword";
        long authorId = 2L;

        CreateUserDto createUserDto = CreateUserDto.builder()
                .email(email)
                .password(password)
                .build();

        User author = new User();
        author.setId(authorId);
        author.setUserRole(UserRole.ROLE_ADMIN);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        when(authentication.getPrincipal()).thenReturn(UserPrinciple.build(author));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserDto userDto = userService.create(createUserDto);
        assertThat(userDto, allOf(
                hasProperty("email", is(email)),
                hasProperty("id", notNullValue())
        ));
    }

    @Test
    public void updateTest() {
        String email = "testEmail";
        String password = "testPassword";
        String name = "name";
        String surname = "surname";
        String updatedEmail = "updatedEmail";
        String updatedName = "updatedName";
        String updatedSurname = "updatedSurname";
        long authorId = 2L;

        CreateUserDto createUserDto = CreateUserDto.builder()
                .email(email)
                .password(password)
                .build();

        User author = new User();
        author.setId(authorId);
        author.setUserRole(UserRole.ROLE_ADMIN);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        when(authentication.getPrincipal()).thenReturn(UserPrinciple.build(author));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserDto userDto = userService.create(createUserDto);

        UpdateUserDto updateUserDto = ObjectMapperUtills.mapTo(userDto, UpdateUserDto.class);
        updateUserDto.setEmail(updatedEmail);
        updateUserDto.setName(updatedName);
        updateUserDto.setSurname(updatedSurname);

        Optional<UserDto> updatedUser = userService.update(userDto.getId(), updateUserDto);
        assertTrue(updatedUser.isPresent());
        assertThat(updatedUser.get(), allOf(
                hasProperty("name", is(updatedName)),
                hasProperty("surname", is(updatedSurname)),
                hasProperty("email", is(updatedEmail))
        ));
    }

    @Test
    public void updateDeletedTest() {
        String email = "testEmail";
        String password = "testPassword";
        long authorId = 2L;

        CreateUserDto createUserDto = CreateUserDto.builder()
                .email(email)
                .password(password)
                .build();

        User author = new User();
        author.setId(authorId);
        author.setUserRole(UserRole.ROLE_ADMIN);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        when(authentication.getPrincipal()).thenReturn(UserPrinciple.build(author));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserDto userDto = userService.create(createUserDto);
        userService.updateDeleted(userDto.getId(), true);
    }
}
