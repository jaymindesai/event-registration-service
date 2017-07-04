package com.services.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    @NotNull(message = "User must have a primary contact number")
    @Valid
    private String primary;

    private String alternate;
}
