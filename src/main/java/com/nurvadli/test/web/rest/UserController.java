package com.nurvadli.test.web.rest;

import com.nurvadli.test.dto.UserCreateDto;
import com.nurvadli.test.dto.UserUpdateDto;
import com.nurvadli.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping(value = "")
    public ResponseEntity<Object> save(@RequestBody @Valid UserCreateDto userCreateDto) {
        return new ResponseEntity<>(userService.save(userCreateDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") String userId, @RequestBody @Valid UserUpdateDto userUpdateDto) {
        return new ResponseEntity<>(userService.update(userId, userUpdateDto), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> detail(@PathVariable("id") String userId) {
        return new ResponseEntity<>(userService.getDetail(userId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String userId) {
        return new ResponseEntity<>(userService.delete(userId), HttpStatus.NO_CONTENT);
    }
}
