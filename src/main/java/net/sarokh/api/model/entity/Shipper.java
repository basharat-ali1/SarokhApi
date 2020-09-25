package net.sarokh.api.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "shipper", schema = "sarokh_db")

public class Shipper {
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
/*
    @Basic
    @Column(name = "business_name", nullable = true)
    private String businessName;

    @Basic
    @Column(name = "business_logo", nullable = true)
    private String businessLogo;
*/
    @Basic
    @Column(name = "nic_number", nullable = true)
    private String nicNumber;

    @Basic
    @Column(name = "nic_file", nullable = true)
    private String nicFile;

    @Basic
    @Column(name = "VAT_number", nullable = true)
    private String vatNumber;

    @Basic
    @Column(name = "CR_file", nullable = true)
    private String crFile;

    @Basic
    @Column(name = "CR_number", nullable = true)
    private String crNumber;

    @Basic
    @Column(name = "shipper_type", nullable = true)
    private String shipperType;

    @Basic
    @Column(name = "shipper_warehouses", nullable = true)
    private Integer shipperWarehouses;

    @Basic
    @Column(name = "contact", nullable = true)
    private String contact;

    @Basic
    @Column(name = "address", nullable = true)
    private String address;

        @Basic
        @Column(name = "city", nullable = true)
        private String city;
    /*
        @Basic
        @Column(name = "location_latitude", nullable = true)
        private String locationLatitude;

        @Basic
        @Column(name = "location_longitude", nullable = true)
        private String locationLongitude;

        @Basic
        @Column(name = "concern_person_contact", nullable = true)
        private String concernPersonContact;

        @Basic
        @Column(name = "concern_person", nullable = true)
        private String concernPerson;

        @Basic
        @Column(name = "concern_person_designation", nullable = true)
        private String concernPersonDesignation;

        @Basic
        @Column(name = "concern_person_email", nullable = true)
        private String concernPersonEmail;

        @Basic
        @Column(name = "business_operations_hours", nullable = true)
        private String businessOperationsHours;
    */
    @Basic
    @Column(name = "status", nullable = true)
    private String status;

    @Basic
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Basic
    @Column(name = "bank_account_id", nullable = false)
    private int bankAccountId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;

}
