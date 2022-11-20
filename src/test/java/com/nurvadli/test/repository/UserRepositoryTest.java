package com.nurvadli.test.repository;

import com.nurvadli.test.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindBySocialSecurityNumberAndIsDeletedIsFalse() {
        User user = User.builder()
                .name("nurvadli")
                .socialSecurityNumber("00001")
                .dateOfBirth(LocalDate.of(1990,1,1))
                .isDeleted(false)
                .build();

        userRepository.save(user);

        Optional<User> anyUser = userRepository.findBySocialSecurityNumberAndIsDeletedIsFalse("00001");
        assertThat(anyUser.isPresent()).isTrue();
    }

    @Test
    public void testFindByIdAndIsDeletedIsFalse() {
        User user =  userRepository.save(User.builder()
                .name("Jhone")
                .socialSecurityNumber("00022")
                .dateOfBirth(LocalDate.of(1990,2,2))
                .isDeleted(false)
                .build());

        Optional<User> anyUser = userRepository.findByIdAndIsDeletedIsFalse(user.getId());
        assertThat(anyUser.isPresent()).isTrue();
    }
}
