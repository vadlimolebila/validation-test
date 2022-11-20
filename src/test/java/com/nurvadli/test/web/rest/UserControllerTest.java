package com.nurvadli.test.web.rest;

import com.nurvadli.test.domain.User;
import com.nurvadli.test.dto.UserCreateDto;
import com.nurvadli.test.dto.UserUpdateDto;
import com.nurvadli.test.service.UserService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private final String BASE_URL = "/v1/users";

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Captor
    private ArgumentCaptor<UserCreateDto> userCreateDtoArgumentCaptor;
    @Captor
    private ArgumentCaptor<UserUpdateDto> userUpdateDtoArgumentCaptor;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testSaveSuccessful() throws Exception {
        JSONObject request = new JSONObject();
        request.put("name", "nurvadli");
        request.put("socialSecurityNumber", "32");
        request.put("dateOfBirth", "1990-01-01");

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(userService, times(1)).save(userCreateDtoArgumentCaptor.capture());
        UserCreateDto userCreateDto = userCreateDtoArgumentCaptor.getValue();
        assertEquals("nurvadli", userCreateDto.getName());
        assertEquals("32", userCreateDto.getSocialSecurityNumber());
        assertEquals("1990-01-01", userCreateDto.getDateOfBirth());
    }

    @Test
    public void testUpdate() throws Exception {
        JSONObject request = new JSONObject();
        request.put("name", "nurvadli");
        request.put("dateOfBirth", "1990-01-01");

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL+"/userIdSix")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).update(eq("userIdSix"), userUpdateDtoArgumentCaptor.capture());
        UserUpdateDto userUpdateDto = userUpdateDtoArgumentCaptor.getValue();
        assertEquals("nurvadli", userUpdateDto.getName());
        assertEquals("1990-01-01", userUpdateDto.getDateOfBirth());
    }

    @Test
    public void testGetDetail() throws Exception {
        when(userService.getDetail("userIdEight")).then(invocationOnMock -> {
            User user = new User();
            user.setId("userIdEight");
            user.setName("Chusnean");
            user.setSocialSecurityNumber("00237");
            user.setDateOfBirth(LocalDate.of(1980, 5, 16));
            user.setIsDeleted(false);
            user.setCreatedBy("SPRING_BOOT_TEST");
            user.setDateCreated(OffsetDateTime.now());
            return user;
        });
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/userIdEight")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("userIdEight"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Chusnean"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth[0]").value(1980))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth[1]").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth[2]").value(16))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isDeleted").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value("SPRING_BOOT_TEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateCreated").isNotEmpty());

        verify(userService, times(1)).getDetail(eq("userIdEight"));
    }

    @Test
    public  void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL+"/userIdSeven")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(userService, times(1)).delete(eq("userIdSeven"));
    }
}
