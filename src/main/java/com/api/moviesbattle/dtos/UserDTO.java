package com.api.moviesbattle.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {


    private String username;

    private String password;

    private Integer quizzesTotal;

    private Integer quizzesHits;

    private Integer quizzesWrong ;
}
