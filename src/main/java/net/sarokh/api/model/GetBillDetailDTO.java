package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetBillDetailDTO {
    private Integer userId;
    private String UserType;
}
