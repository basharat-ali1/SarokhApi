package net.sarokh.api.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PermissionDTO {
    private int id;
    private String permission;
    private Timestamp createdDatetime;
    private Timestamp updatedDatetime;
    private int roleId;

}
