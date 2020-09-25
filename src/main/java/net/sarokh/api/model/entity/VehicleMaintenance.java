package net.sarokh.api.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "vehicle_maintenance", schema = "Sarokh_db")
public class VehicleMaintenance {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Basic
    @Column(name = "description", nullable = true)
    private String description;

    @Basic
    @Column(name = "maintenance_type", nullable = true)
    private String maintenanceType;

    @Basic
    @Column(name = "repair_facility", nullable = true)
    private String repairFacility;

    @Basic
    @Column(name = "repair_facility_contact", nullable = true)
    private String repairFacilityContact;

    @Basic
    @Column(name = "authorized_by", nullable = true)
    private String authorizedby;

    @Basic
    @Column(name = "repair_start_datetime", nullable = true)
    private Date repairStartDatetime;

    @Basic
    @Column(name = "repair_end_datetime", nullable = true)
    private Date repairEndDatetime;

    @Basic
    @Column(name = "invoice", nullable = true)
    private String invoice;

    @Basic
    @Column(name = "vehicle_id", nullable = false)
    private Integer vehicleId;

    @Basic
    @Column(name = "ledger_id", nullable = false)
    private Integer ledgerId;
}
