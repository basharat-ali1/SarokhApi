package net.sarokh.api.model.trip;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class CreateTripSearchResultsDTO {
    private List<TripDetailsDTO> pickups;
    private List<TripDetailsDTO> deliveries;
    private List<TripPointsDTO> pointsList;
}
