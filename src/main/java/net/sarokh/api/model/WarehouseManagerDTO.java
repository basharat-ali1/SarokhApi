package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class WarehouseManagerDTO {
    private int id;
    private String firstName;
    private String lastName;
    private Date joiningDate;
    private String status;
    private Timestamp createdDatetime;
    private Timestamp updatedDatetime;
    private int userId;

}
