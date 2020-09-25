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
@Table(name = "payment_transaction", schema = "Sarokh_db")
public class PaymentTransaction {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;

    @Basic
    @Column(name = "transaction_type", nullable = true)
    private String transactionType;

    @Basic
    @Column(name = "transaction_from_id", nullable = true)
    private Integer transactionFromId;

    @Basic
    @Column(name = "transaction_from", nullable = true)
    private String transactionFrom;

    @Basic
    @Column(name = "transaction_to_id", nullable = true)
    private Integer transactionToId;

    @Basic
    @Column(name = "transaction_to", nullable = true)
    private String transactionTo;

    @Basic
    @Column(name = "amount_paid", nullable = true)
    private Double amountPaid;

    @Basic
    @Column(name = "amount_pending", nullable = true)
    private Double amountPending;

    @Basic
    @Column(name = "details", nullable = true)
    private String details;

    @Basic
    @Column(name = "payment_method", nullable = true)
    private String paymentMethod;

    @Basic
    @Column(name = "payment_description", nullable = true)
    private String paymentDescription;

    @Basic
    @Column(name = "bill", nullable = true)
    private String bill;

    @Basic
    @Column(name = "ledger_no", nullable = true)
    private Integer ledgerNo;

    @Basic
    @Column(name = "ledger_name", nullable = true)
    private String ledgerName;

    @Basic
    @JsonIgnore
    @Column(name = "wallet_id", nullable = false)
    private Integer walletId;

    @Basic
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_datetime", nullable = true)
    private Date createdDatetime;

    @UpdateTimestamp
    @Column(name = "updated_datetime", nullable = true)
    private Date datetime;
}
