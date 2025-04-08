package ru.aston.meet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.aston.meet.model.InvitationStatus;

@Getter
@Setter
@NoArgsConstructor
public class InvitationDto {

    private Long id;

    private Long meetingId;

    private Long userId;

    private InvitationStatus status;
}
