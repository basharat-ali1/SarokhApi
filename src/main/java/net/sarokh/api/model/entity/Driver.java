package net.sarokh.api.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "driver", schema = "Sarokh_db")
public class Driver {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    @Column(name = "first_name", nullable = true)
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = true)
    private String lastName;

    @Basic
    @Column(name = "address", nullable = true)
    private String address;

    @Basic
    @Column(name = "city", nullable = true)
    private String city;

    @Basic
    @Column(name = "postal_code", nullable = true)
    private Integer postalCode;

    @Basic
    @Column(name = "country", nullable = true)
    private String country;

    @Basic
    @Column(name = "nic_number", nullable = true)
    private String nicNumber;

    @Basic
    @Column(name = "nic_file", nullable = true)
    private String nicFile;

    @Basic
    @Column(name = "license_number", nullable = true)
    private String licenseNumber;

    @Basic
    @Column(name = "license_file", nullable = true)
    private String licenseFile;

    @Basic
    @Column(name = "driver_type", nullable = true)
    private String driverType;

    @Basic
    @Column(name = "compensation_cycle", nullable = true)
    private String compensationCycle;

    @Basic
    @Column(name = "compensation", nullable = true)
    private Double compensation;

    @Basic
    @Column(name = "contract_start_date", nullable = true)
    private String contractStartDate;

    @Basic
    @Column(name = "contract_end_date", nullable = true)
    private String contractValidTill;

    @Basic
    @Column(name = "contract_file", nullable = true)
    private String contractFile;

    @Basic
    @Column(name = "status", nullable = true)
    private String status;

    @Basic
    @Column(name = "performance_rating", nullable = true)
    private Integer performanceRating;

    @Basic
    @Column(name = "notes", nullable = true)
    private String notes;

    @Basic
    @Column(name = "bank_account_id", nullable = true)
    private Integer bankAccountId;

    @Basic
    @Column(name = "warehouse_id", nullable = true)
    private Integer warehouseId;

    @Basic
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Basic
    @Column(name = "vehicle_id", nullable = false)
    private Integer vehicleId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_account_id", insertable=false, updatable=false)
    private BankAccount bankAccount;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "warehouse_id", insertable=false, updatable=false)
    private SarokhWarehouse warehouse;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", insertable=false, updatable=false)
    private Vehicle vehicle;
}
