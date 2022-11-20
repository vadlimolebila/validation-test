package com.nurvadli.test.service.impl;

import com.nurvadli.test.domain.User;
import com.nurvadli.test.dto.UserCreateDto;
import com.nurvadli.test.dto.UserUpdateDto;
import com.nurvadli.test.exception.RecordAlreadyExistException;
import com.nurvadli.test.exception.RecordNotFoundException;
import com.nurvadli.test.repository.UserRepository;
import com.nurvadli.test.service.UserService;
import com.nurvadli.test.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(UserCreateDto userCreateDto) {
        String SSN = String.format("%05d", Integer.valueOf(userCreateDto.getSocialSecurityNumber()));
        if (userRepository.findBySocialSecurityNumberAndIsDeletedIsFalse(SSN).isPresent()) {
            log.warn("User already exist with social security number: {}", SSN);
            throw new RecordAlreadyExistException(String.format("Record with SSN '%s' already exist in the system", SSN));
        }

        return userRepository.save(User.builder()
                .name(userCreateDto.getName())
                .socialSecurityNumber(SSN)
                .dateOfBirth(DateUtil.toLocalDate(userCreateDto.getDateOfBirth()))
                .isDeleted(false)
                .build());
    }

    @Override
    public User update(String userId, UserUpdateDto userUpdateDto) {
        log.info("Update user with userId: {} and updated payload: {}", userId, userUpdateDto);
        User user = getDetail(userId);
        user.setName(userUpdateDto.getName());
        user.setDateOfBirth(DateUtil.toLocalDate(userUpdateDto.getDateOfBirth()));
        return userRepository.save(user);
    }

    @Override
    public User getDetail(String userId) {
        Optional<User> anyUser = userRepository.findByIdAndIsDeletedIsFalse(userId);
        if (!anyUser.isPresent()) {
            log.warn("Record is not found by userId: {}", userId);
            throw new RecordNotFoundException(String.format("No such resource with id '%s'", userId));
        }
        return anyUser.get();
    }

    @Override
    public User delete(String userId) {
        User user = getDetail(userId);
        user.setIsDeleted(true);
        return userRepository.save(user);
    }


}
