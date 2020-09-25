package net.sarokh.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BankAccountDTO {
    @JsonIgnore
    private Integer id;
    private String accountNumber;
    private String bank;
    private String iban;

}
