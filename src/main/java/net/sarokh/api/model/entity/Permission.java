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
@Table(name = "permission", schema = "Sarokh_db")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "permission", nullable = true, length = 45)
    private String permission;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_datetime", nullable = true)
    private Date createdDatetime;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_datetime", nullable = true)
    private Date updatedDatetime;

    @ManyToOne
    @JoinColumn(name="role_id", nullable=false)
    private Role role;

}
