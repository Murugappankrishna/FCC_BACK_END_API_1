package com.murugappan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class InputUrl {
    @JsonProperty("original_url")
    private String originalUrl;
}
