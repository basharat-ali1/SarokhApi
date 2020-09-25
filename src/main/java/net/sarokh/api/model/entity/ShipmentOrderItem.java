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
@Table(name = "shipment_order_item", schema = "sarokh_db")
public class ShipmentOrderItem {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "tracking_number", nullable = true)
    private String trackingNumber;

    @Column(name = "shipment_title", nullable = true)
    private String shipmentTitle;

    @Column(name = "shipment_content", nullable = true)
    private String shipmentContent;

    @Column(name = "shipment_value", nullable = true)
    private Double shipmentValue;

    @Column(name = "shipment_type", nullable = true)
    private String shipmentType;

    @Column(name = "payment_type", nullable = true)
    private String paymentType;

    @Column(name = "cod_amount", nullable = true)
    private Double codAmount;

    @Column(name = "shipment_billed_amount", nullable = true)
    private Double shipmentBilledAmount;

    @Column(name = "shipment_delivery_charges", nullable = true)
    private Double shipmentDeliveryCharges;

    @Column(name = "weight", nullable = true)
    private String weight;

    @Column(name = "delivery_status", nullable = true)
    private String deliveryStatus;

    @Column(name = "signature", nullable = true)
    private String signature;

    @Column(name = "shipment_order_id", nullable = true)
    private Integer shipmentOrderId;

    @Column(name = "receiver_name", nullable = true)
    private String receiverName;

    @Column(name = "contact", nullable = true)
    private String contact;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "location_longitude", nullable = true)
    private String locationLongitude;

    @Column(name = "location_latitude", nullable = true)
    private String locationLatitude;

    @Column(name = "delivery_date", nullable = true)
    private Date deliveryDate;

    @Column(name = "qr_code", nullable = true)
    private String QRCode;

    @Column(name = "bar_code", nullable = true)
    private String barCode;

    @Column(name = "shipment_status", nullable = true)
    private String shipmentStatus;

    @Column(name = "additional_services", nullable = true)
    private String additionalServices;

    @Column(name = "receiver_confirmation", nullable = true)
    private String receiverConfirmation;

    @Column(name = "received_from_shipper", nullable = true)
    private String receivedFromShipper;

    @Column(name = "report_issue_id", nullable = true)
    private Integer reportIssueId;

    @Column(name = "ledger_created", nullable = true)
    private Integer ledgerCreated;

    @Column(name = "additional_charges", nullable = true)
    private Double additionalCharges;

}

