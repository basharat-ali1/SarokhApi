package net.sarokh.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "Warehouse_Manager", schema = "Sarokh_db")
public class WarehouseManager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "warehouse_id", nullable = false)
    private Integer warehouseId;

    @JsonIgnore
    @Column(name = "joining_date", nullable = true)
    private Date joiningDate;

    @Column(name = "status", nullable = true)
    private String status;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_datetime")
    private java.util.Date createdDatetime;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_datetime")
    private java.util.Date updatedDatetime;

  //  @Column(name = "user_id", nullable = false)
  //  private int userId;

}
