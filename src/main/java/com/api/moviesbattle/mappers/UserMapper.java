package com.api.moviesbattle.mappers;

import com.api.moviesbattle.dtos.UserDTO;
import com.api.moviesbattle.dtos.UserDTORating;
import com.api.moviesbattle.models.User;

public interface UserMapper {
    UserDTORating convertUserToUserDtoRating(User user);
    User convertUserDtoToUser(UserDTO userDTO);
}
