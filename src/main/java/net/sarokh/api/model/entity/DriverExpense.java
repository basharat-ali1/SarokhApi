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
@Table(name = "driver_expense", schema = "Sarokh_db")
public class DriverExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "expense_type", nullable = true)
    private String expenseType;

    @Column(name = "amount", nullable = true)
    private Double amount;

    @Column(name = "invoice_file", nullable = true)
    private String invoiceFile;

    @Column(name = "driver_id", nullable = true)
    private Integer driverId;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_datetime", nullable = true)
    private Date createdDatetime;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_datetime", nullable = true)
    private Date updatedDatetime;

}
