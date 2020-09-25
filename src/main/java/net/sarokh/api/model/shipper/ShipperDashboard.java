package net.sarokh.api.model.shipper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.ShipperDashboardDTO;
import net.sarokh.api.model.entity.Shipper;

@Setter
@Getter
@Builder
public class ShipperDashboard {
    private Shipper shipper;
    private ShipperDashboardDTO dashboard;
}
