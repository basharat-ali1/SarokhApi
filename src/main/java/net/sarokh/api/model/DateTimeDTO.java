package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DateTimeDTO {
    private String date;
    private String time;
}
