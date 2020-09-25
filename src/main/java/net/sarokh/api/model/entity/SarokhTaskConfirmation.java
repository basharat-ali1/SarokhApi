package net.sarokh.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "sarokh_task_confirmation", schema = "sarokh_db")
public class SarokhTaskConfirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "sarokh_task_id", nullable = false)
    private Integer sarokhTaskId;

    @Column(name = "driver_id", nullable = false)
    private Integer driverId;

    @Column(name = "receive_shipments", nullable = true)
    private Boolean receiveShipments;

    @Column(name = "give_shipments", nullable = true)
    private Boolean giveShipments;

    @Column(name = "receive_cod", nullable = true)
    private Boolean receiveCod;

    @Column(name = "pending_cod", nullable = true)
    private Boolean pendingCod;

    @Column(name = "signature", nullable = true)
    private String signature;

    @JsonIgnore
    @Column(name = "status", nullable = true)
    private String status;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "date", nullable = true)
    private Date dateTime;
}
