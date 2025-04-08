package ru.aston.meet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateInvitationDto {

    @NotNull
    @Positive
    private Long meetingId;

    @NotNull
    @Positive
    private Long userId;
}
