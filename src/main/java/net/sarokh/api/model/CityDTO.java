package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CityDTO {
    private Integer id;
    private String name;
    private String country;
}
