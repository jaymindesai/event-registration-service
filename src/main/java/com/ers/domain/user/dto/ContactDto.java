package com.ers.domain.user.dto;

import com.ers.domain.AbstractDto;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ContactDto extends AbstractDto {

    @NotNull(message = "User must have a primary contact number")
    @Valid
    private String primary;

    private String alternate;
}
