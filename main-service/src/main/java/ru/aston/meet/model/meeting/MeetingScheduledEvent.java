package ru.aston.meet.model.meeting;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MeetingScheduledEvent {
    private final Meeting meeting;
}
