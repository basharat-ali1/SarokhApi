package net.sarokh.api.model.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackOrderFormDTO {
    private String trackingNumber;
    private Integer otp;
}
