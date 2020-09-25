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
@Table(name = "ledger", schema = "Sarokh_db")
public class Ledger {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "bill_type", nullable = true)
    private String billType;

    @Column(name = "bill_category", nullable = true)
    private String billCategory;

    @Column(name = "start_date", nullable = true)
    private Date startDate;

    @Column(name = "end_date", nullable = true)
    private Date endDate;

    @Column(name = "due_date", nullable = true)
    private Date dueDate;

    @Column(name = "total_amount", nullable = true)
    private Double totalAmount;

    @Column(name = "payment_status", nullable = true)
    private String paymentStatus;

    @Column(name = "shipments_id_list", nullable = true)
    private String shipmentsIdList;

    @Column(name = "shipper_id", nullable = true)
    private Integer shipperId;

    @Column(name = "transaction_type", nullable = true)
    private String transactionType;

    @Column(name = "ledger_for_detail", nullable = true)
    private Integer ledgerForDetail;

    @Column(name = "ledger_for", nullable = true)
    private String ledgerFor;

    @Column(name = "user_type", nullable = true)
    private String userType;

    @Column(name = "bill_to", nullable = true)
    private Integer billTo;

    @Column(name = "item", nullable = true)
    private String item;

    @Column(name = "units", nullable = true)
    private Integer units;

    @Column(name = "unit_price", nullable = true)
    private Double unitPrice;

    @Column(name = "payee_type", nullable = true)
    private String payeeType;

    @Column(name = "payee_detail", nullable = true)
    private String payeeDetail;

    @Column(name = "payee_wallet", nullable = true)
    private String payeeWallet;

    @Column(name = "payment_date", nullable = true)
    private String paymentDate;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_datetime", nullable = true)
    private Date createdDatetime;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_datetime", nullable = true)
    private Date updatedDatetime;

}
