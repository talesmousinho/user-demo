package com.user.demo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UserDTO (
    Long id,

    @NotBlank
    @Size(min = 2, max = 25)
    String name,

    @NotBlank
    @Size(min = 1, max = 50)
    String lastname
) {

}
