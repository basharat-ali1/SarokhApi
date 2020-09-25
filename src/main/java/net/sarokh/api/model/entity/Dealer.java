package net.sarokh.api.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "dealer", schema = "sarokh_db")
public class Dealer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "owner_name", nullable = true)
    private String ownerName;
    
    @Column(name = "company_name", nullable = true)
    private String companyName;

    @Column(name = "company_name_AR", nullable = true)
    private String companyNameAr;

    @Column(name = "contact", nullable = true)
    private String contact;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "zip_code", nullable = true)
    private Integer zipCode;

    @Column(name = "country", nullable = true)
    private String country;

    @Column(name = "nic_number", nullable = true)
    private String nicNumber;

    @Column(name = "nic_file", nullable = true)
    private String nicFile;

    @Column(name = "CR_number", nullable = true)
    private String crNumber;

    @Column(name = "CR_file", nullable = true)
    private String crFile;

    @Column(name = "compensation_clearence_duration", nullable = true)
    private Integer compensationClearanceDuration;

    @Column(name = "per_shipments_compensation", nullable = true)
    private Double perShipmentsCompensation;

    @Column(name = "contract_start_date", nullable = true)
    private String contractStartDate;

    @Column(name = "contract_end_date", nullable = true)
    private String contractEndDate;

    @Column(name = "contract_file", nullable = true)
    private String contractFile;

    @Column(name = "contract_id", nullable = true)
    private String contractId;

    @Column(name = "location_latitude", nullable = true)
    private String locationLatitude;

    @Column(name = "location_longitude", nullable = true)
    private String locationLongitude;

    @Column(name = "operation_timing", nullable = true)
    private String operationTiming;

    @Column(name = "shop_picture", nullable = true)
    private String shopPicture;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "bank_account_id", nullable = false)
    private Integer bankAccountId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_account_id", insertable=false, updatable=false)
    private BankAccount bankAccount;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "dealer")
    private List<DealerPoint> dealerPoints;

}
