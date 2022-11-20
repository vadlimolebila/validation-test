package com.nurvadli.test.service;

import com.nurvadli.test.domain.User;
import com.nurvadli.test.dto.UserCreateDto;
import com.nurvadli.test.dto.UserUpdateDto;

public interface UserService {

    User save(UserCreateDto userCreateDto);


    User update(String userId, UserUpdateDto userUpdateDto);

    User getDetail(String userId);

    User delete(String userId);
}
