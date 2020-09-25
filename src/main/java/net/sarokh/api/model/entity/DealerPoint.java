package net.sarokh.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "dealer_point", schema = "sarokh_db")
public class DealerPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "dealer_point_name", nullable = false)
    private String dealerPointName;

    @Column(name = "CR_number", nullable = true)
    private String commercialRegistrationNumber;

    @Column(name = "CR_file", nullable = true)
    private String commercialRegistrationFile;

    @Column(name = "operator_name", nullable = true)
    private String operatorName;

    @Column(name = "operator_contact", nullable = true)
    private String operatorContact;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "country", nullable = true)
    private String country;

    @Column(name = "postal_code", nullable = true)
    private Integer postalCode;

    @Column(name = "point_picture", nullable = true)
    private String pointPicture;

    @Column(name = "shipments", nullable = true)
    private Integer shipment;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "dealer_id", nullable = true)
    private Integer dealerId;

    @Column(name = "current_inventory", nullable = true)
    private Integer currentInventory;

    @Column(name = "owner", nullable = true)
    private String owner;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "location_latitude", nullable = true)
    private String locationLatitude;

    @Column(name = "location_longitude", nullable = true)
    private String locationLongitude;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id", insertable=false, updatable=false)
    private Dealer dealer;

}
