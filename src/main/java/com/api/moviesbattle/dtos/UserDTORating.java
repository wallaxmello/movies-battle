package com.api.moviesbattle.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTORating {

    private Long id;
    private String username;
    private Integer quizzesTotal;
    private Integer quizzesHits;
    private Integer quizzesWrong ;
    private BigDecimal score;

    public BigDecimal getScore(){
        return new BigDecimal(quizzesTotal * (quizzesHits/100));
    }

    @Override
    public String toString() {
        return "id=" + id + "\n" +
                ", Usuário = '" + username + '\'' + "\n" +
                ", Quizzes Total = " + quizzesTotal + "\n" +
                ", Quizzes Acertos = " + quizzesHits + "\n" +
                ", Quizzes Erros = " + quizzesWrong + "\n" +
                ", Quizzes Pontuacao = " + getScore() + "\n" +
                '}';
    }
}
