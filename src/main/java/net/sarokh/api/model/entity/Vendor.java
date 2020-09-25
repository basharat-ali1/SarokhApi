package net.sarokh.api.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "vendor", schema = "Sarokh_db")
public class Vendor {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    @Column(name = "company_name", nullable = true)
    private String companyName;

    @Basic
    @Column(name = "contact", nullable = true)
    private String contact;

    @Basic
    @Column(name = "address", nullable = true)
    private String address;

    @Basic
    @Column(name = "city", nullable = true)
    private String city;

    @Basic
    @Column(name = "country", nullable = true)
    private String country;

    @Basic
    @Column(name = "email", nullable = true)
    private String email;

    @Basic
    @Column(name = "commercial_registration_number", nullable = true)
    private String commercialRegistrationNumber;

    @Basic
    @Column(name = "cr_file", nullable = true)
    private String crFile;

    @Basic
    @Column(name = "payment_api_id", nullable = true)
    private String paymentApiId;

    @Basic
    @Column(name = "payment_api_key", nullable = true)
    private String paymentApiKey;

    @Basic
    @Column(name = "payment_api_url", nullable = true)
    private String paymentApiUrl;

    @Basic
    @Column(name = "bank_account_id", nullable = true)
    private Integer bankAccountId;

    @Basic
    @Column(name = "user_id", nullable = false)
    private int userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_account_id", insertable=false, updatable=false)
    private BankAccount bankAccount;

}
