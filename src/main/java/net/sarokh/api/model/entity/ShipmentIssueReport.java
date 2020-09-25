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
@Table(name = "shipment_issue_report", schema = "sarokh_db")
public class ShipmentIssueReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "tracking_number", nullable = true)
    private String trackingNumber;

    @Column(name = "complaint_against", nullable = true)
    private String complaintAgainst;

    @Column(name = "complaint_against_name", nullable = true)
    private String complaintAgainstName;

    @Column(name = "complaint_description", nullable = true)
    private String compliantDescription;

    @Column(name = "reported_by", nullable = true)
    private String reportedBy;

    @Column(name = "reported_by_id", nullable = true)
    private Integer reportedById;

    @Column(name = "status", nullable = true)
    private String status;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "date", nullable = true)
    private Date date;

}
