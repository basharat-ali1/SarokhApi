package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BasicInfoDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String contact;
    private String city;
    private String email;
    private String dateOfBirth;
    private String profilePicture;

}
