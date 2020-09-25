package net.sarokh.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "wallet", schema = "Sarokh_db")
public class Wallet {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer walletId;

    @Basic
    @Column(name = "description", nullable = true)
    private String description;

    @Basic
    @Column(name = "current_balance", nullable = true)
    private Double currentBalance;

    @Basic
    @Column(name = "receivable", nullable = true)
    private Double receivable;

    @Basic
    @Column(name = "payable", nullable = true)
    private Double payable;

    @Basic
    @Column(name = "user_type_id", nullable = true)
    private Integer userTypeId;

    @Basic
    @Column(name = "user_type", nullable = true)
    private String userType;

    @Basic
    @Column(name = "wallet_type", nullable = true)
    private String walletType;

    @Basic
    @Column(name = "wallet_holder", nullable = true)
    private String walletHolder;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_datetime", nullable = true)
    private Date createdDatetime;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_datetime", nullable = true)
    private Date updatedDatetime;

    @Basic
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    
}
