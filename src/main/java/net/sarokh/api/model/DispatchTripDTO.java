package net.sarokh.api.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class DispatchTripDTO {
    private int id;
    private String startPoint;
    private String endPoint;
    private Timestamp dispatchDatetime;
    private Double driverCompensation;
    private Date tripCompletionDatetime;
    private int driverId;


}
