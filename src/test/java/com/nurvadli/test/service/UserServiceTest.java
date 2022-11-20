package com.nurvadli.test.service;

import com.nurvadli.test.domain.User;
import com.nurvadli.test.dto.UserCreateDto;
import com.nurvadli.test.dto.UserUpdateDto;
import com.nurvadli.test.exception.RecordAlreadyExistException;
import com.nurvadli.test.exception.RecordNotFoundException;
import com.nurvadli.test.repository.UserRepository;
import com.nurvadli.test.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    public void testSaveSuccessful() {
        when(userRepository.findBySocialSecurityNumberAndIsDeletedIsFalse("00033")).thenReturn(Optional.empty());
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setName("Yurri");
        userCreateDto.setSocialSecurityNumber("33");
        userCreateDto.setDateOfBirth("1995-05-11");

        userService.save(userCreateDto);

        verify(userRepository, times(1)).findBySocialSecurityNumberAndIsDeletedIsFalse("00033");
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();
//        assertNotNull(user.getId());
        assertEquals("Yurri", user.getName());
        assertEquals("00033", user.getSocialSecurityNumber());
        assertEquals(LocalDate.of(1995, 5, 11), user.getDateOfBirth());
        assertFalse(user.getIsDeleted());
    }

    @Test(expected = RecordAlreadyExistException.class)
    public void testSaveRejected() {
        when(userRepository.findBySocialSecurityNumberAndIsDeletedIsFalse("00033")).then(invocationOnMock -> {
            User user = new User();
            user.setId("userIdOne");
            user.setName("Yurri");
            user.setSocialSecurityNumber("33");
            user.setDateOfBirth(LocalDate.of(1995, 5, 11));
            user.setIsDeleted(false);
            return Optional.of(user);
        });

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setName("Yurri");
        userCreateDto.setSocialSecurityNumber("33");
        userCreateDto.setDateOfBirth("1995-05-11");
        userService.save(userCreateDto);
        verify(userRepository, times(1)).findBySocialSecurityNumberAndIsDeletedIsFalse("00033");

    }

    @Test
    public void testUpdate() {
        String userId = "userIdOne";
        when(userRepository.findByIdAndIsDeletedIsFalse(userId)).then(invocationOnMock -> {
            User user = new User();
            user.setId(userId);
            user.setName("Olano");
            user.setSocialSecurityNumber("00047");
            user.setDateOfBirth(LocalDate.of(1980, 5, 16));
            user.setIsDeleted(false);
            return Optional.of(user);
        });

        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setName("Ronaldo");
        userUpdateDto.setDateOfBirth("1980-05-16");

        userService.update(userId, userUpdateDto);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();
        assertEquals(userId, user.getId());
        assertEquals("Ronaldo", user.getName());
        assertEquals("00047", user.getSocialSecurityNumber());
        assertEquals(LocalDate.of(1980, 5, 16), user.getDateOfBirth());
        assertFalse(user.getIsDeleted());

    }

    @Test
    public void testGetDetailSuccess() {
        String userId = "userIdTwo";
        when(userRepository.findByIdAndIsDeletedIsFalse(userId)).then(invocationOnMock -> {
            User user = new User();
            user.setId(userId);
            user.setName("Chusnean");
            user.setSocialSecurityNumber("00237");
            user.setDateOfBirth(LocalDate.of(1980, 5, 16));
            user.setIsDeleted(false);
            user.setCreatedBy("SPRING_BOOT_TEST");
            user.setDateCreated(OffsetDateTime.now());
            return Optional.of(user);
        });

        User user = userService.getDetail(userId);
        assertEquals(userId, user.getId());
        assertEquals("Chusnean", user.getName());
        assertEquals("00237", user.getSocialSecurityNumber());
        assertEquals(LocalDate.of(1980, 5, 16), user.getDateOfBirth());
        assertFalse(user.getIsDeleted());
        assertNotNull(user.getDateCreated());
    }

    @Test(expected = RecordNotFoundException.class)
    public void testGetDetailFailed() {
        String userId = "userIdFourth";
        when(userRepository.findByIdAndIsDeletedIsFalse(userId)).thenReturn(Optional.empty());

        User user = userService.getDetail(userId);
        assertNull(user);

    }

    @Test
    public void testDelete() {
        String userId = "userIdFive";
        when(userRepository.findByIdAndIsDeletedIsFalse(userId)).then(invocationOnMock -> {
            User user = new User();
            user.setId(userId);
            user.setName("Steven");
            user.setSocialSecurityNumber("01234");
            user.setDateOfBirth(LocalDate.of(1990, 2, 3));
            user.setIsDeleted(false);
            user.setCreatedBy("SPRING_BOOT_TEST");
            user.setDateCreated(OffsetDateTime.now());
            return Optional.of(user);
        });

        userService.delete(userId);

        verify(userRepository, times(1)).save(userArgumentCaptor.capture());

        User user = userArgumentCaptor.getValue();
        assertEquals(userId, user.getId());
        assertEquals("Steven", user.getName());
        assertEquals("01234", user.getSocialSecurityNumber());
        assertEquals(LocalDate.of(1990, 2, 3), user.getDateOfBirth());
        assertTrue(user.getIsDeleted());
    }
}
