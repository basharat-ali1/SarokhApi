package net.sarokh.api.model.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AllShippersListingDTO {
    private Object shipperId;
    private Object shipperName;
    private Object shipperType;
    private Object warehouses;
    private Object contact;
    private Object email;
    private Object status;

}
