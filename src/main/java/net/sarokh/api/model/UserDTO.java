package net.sarokh.api.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.entity.Role;

import java.util.Date;


@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserDTO {
    private int id;
    private String fullName;
    private String designation;
    private String userName;
    private String userPassword;
    private String email;
    private String contact;
    private String gender;
    private String dob;
    private String profilePicture;
    private Integer parentTypeId;
    private String userType;

    private Role role;

    private Date createdDatetime;
    private Date updatedDatetime;


}
