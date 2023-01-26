package com.api.moviesbattle.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuizDTOResponse {
    @JsonProperty(value = "imdbID")
    private String imdbId;
    @JsonProperty(value = "imdbRating")
    private String imdbRating;
    @JsonProperty(value = "Title")
    private String title;
    @JsonProperty(value = "Year")
    private String year;

    @Override
    public String toString() {
        return "ID ='" + imdbId + '\'' +
                ", Nota ='" + imdbRating + '\'' +
                ", Titulo ='" + title + '\'' +
                ", Ano ='" + year + '\'' +
                '}';
    }
}
