package com.api.moviesbattle.mappers.impl;

import com.api.moviesbattle.dtos.UserDTO;
import com.api.moviesbattle.dtos.UserDTORating;
import com.api.moviesbattle.mappers.UserMapper;
import com.api.moviesbattle.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTORating convertUserToUserDtoRating(User user) {
        UserDTORating userDTORating = new UserDTORating();
        userDTORating.setId(user.getId());
        userDTORating.setUsername(user.getUsername());
        userDTORating.setQuizzesTotal(user.getQuizzesTotal());
        userDTORating.setQuizzesHits(user.getQuizzesHits());
        userDTORating.setQuizzesWrong(user.getQuizzesWrong());
        return userDTORating;
    }

    @Override
    public User convertUserDtoToUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setQuizzesTotal(userDTO.getQuizzesTotal());
        user.setQuizzesHits(userDTO.getQuizzesHits());
        user.setQuizzesWrong(userDTO.getQuizzesWrong());
        return user;
    }
}
