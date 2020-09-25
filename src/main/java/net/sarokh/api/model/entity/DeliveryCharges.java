package net.sarokh.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "delivery_charges", schema = "sarokh_db")
public class DeliveryCharges {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "weight_zero_to_five", nullable = true)
    private Double weightUptoFiveKg;

    @Column(name = "weight_five_to_ten", nullable = true)
    private Double weightFiveToTen;

    @Column(name = "weight_ten_to_fifteen", nullable = true)
    private Double weightTenToFifteen;

    @Column(name = "COD_charges", nullable = true)
    private Double CODCharges;

    @Column(name = "return_charges", nullable = true)
    private Double returnCharges;

    @Column(name = "last_mile", nullable = true)
    private Double lastMile;

    @Column(name = "normal_packaging", nullable = true)
    private Double normalPackaging;

    @Column(name = "gift_packaging", nullable = true)
    private Double giftPackaging;

    @Column(name = "insurance", nullable = true)
    private Double insurance;

    @Column(name = "pickup_sarokh_point", nullable = true)
    private Boolean pickupSarokhPoint;

    @Column(name = "pickup_shipper_warehouse", nullable = true)
    private Boolean pickupShipperWarehouse;

    @Column(name = "pickup_sarokh_warehouse", nullable = true)
    private Boolean pickupSarokhWarehouse;

    @Column(name = "delivery_sarokh_point", nullable = true)
    private Boolean deliverySarokhPoint;

    @Column(name = "delivery_last_mile", nullable = true)
    private Boolean deliveryLastMile;

    @Column(name = "delivery_customer_choice", nullable = true)
    private Boolean deliveryCustomerChoice;

    @Column(name = "shipper_id", nullable = false)
    private Integer shipperId;

    @Column(name = "notes", nullable = false)
    private String notes;

    @Column(name = "enable", nullable = true)
    private Boolean enable;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_date", nullable = true)
    private Date createdDatetime;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_date", nullable = true)
    private Date updatedDatetime;

}
