package net.sarokh.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class CreateBillItemsDTO {

    private Integer id;
    private String trackingNumber;
    private String date;
    private Double amount;

}
