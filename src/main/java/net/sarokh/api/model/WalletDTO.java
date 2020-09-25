package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class WalletDTO {
    private int id;
    private String description;
    private Double currentBalance;
    private Double receivable;
    private Double payable;
    private Timestamp datetime;
    private int userUserId;

}
