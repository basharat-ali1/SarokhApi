package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordDTO {
    private Integer userId;
    private String currentPassword;
    private String newPassword;
}
