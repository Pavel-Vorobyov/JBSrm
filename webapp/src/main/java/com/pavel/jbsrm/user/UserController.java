package com.pavel.jbsrm.user;

import com.pavel.jbsrm.user.dto.CreateUserDto;
import com.pavel.jbsrm.user.dto.UpdateUserDto;
import com.pavel.jbsrm.user.dto.UserDto;
import com.pavel.jbsrm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable long id) {
        return userService.find(id);
    }

    @GetMapping("/quickSearch/{searchParams}")
    public List<UserDto> findAllByPropMatch(@PathVariable String searchParams) {
        return userService.findAllByPropsMatch(searchParams);
    }

    @GetMapping
    public Page<UserDto> findAll(UserFilter filter, Pageable pageable) {
        return userService.findAllPageByDeleted(filter, pageable); //todo pageable supported supplies
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody UpdateUserDto updateDto) {
        userService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public UserDto create(@RequestBody CreateUserDto createDto) {
        return userService.create(createDto);
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<String> updateDeleted(@PathVariable long id, Boolean deleted) {
        userService.updateDeleted(id, deleted);
        return ResponseEntity.ok().build();
    }
}
