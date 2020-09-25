package net.sarokh.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "role", schema = "Sarokh_db")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "parent_role", nullable = true)
    private String parentRole;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_datetime", nullable = true)
    private Date createdDatetime;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_datetime", nullable = true)
    private Date updatedDatetime;

    //@OneToMany(mappedBy = "role")
    //private Set<Permission> permissions;


}
