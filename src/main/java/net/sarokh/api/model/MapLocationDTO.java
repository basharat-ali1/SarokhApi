package net.sarokh.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MapLocationDTO {
    private String label;
    private String latitude;
    private String longitude;
}
