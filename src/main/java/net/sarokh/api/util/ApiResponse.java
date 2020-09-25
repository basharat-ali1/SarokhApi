package net.sarokh.api.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ApiResponse {

    private Object data;
    private Integer status;
    private String message;

}
