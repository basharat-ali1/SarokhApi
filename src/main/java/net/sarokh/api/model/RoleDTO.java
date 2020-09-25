package net.sarokh.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class RoleDTO {
    @JsonIgnore
    private int id;
    private String name;
    @JsonIgnore
    private Timestamp createdDatetime;
    @JsonIgnore
    private Timestamp updatedDatetime;

}
