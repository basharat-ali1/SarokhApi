package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class UserRoleDTO {
    private int id;
    private int userId;
    private int roleId;

}
