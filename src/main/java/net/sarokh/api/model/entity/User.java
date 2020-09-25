package net.sarokh.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "user", schema = "sarokh_db")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name", nullable = true)
    private String userName;

    @Column(name = "user_password", nullable = true)
    private String userPassword;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "contact", nullable = true)
    private String contact;

    @Column(name = "gender", nullable = true)
    private String gender;

    @Column(name = "DOB", nullable = true)
    private String dob;

    @Column(name = "full_name", nullable = true)
    private String fullName;

    @Column(name = "designation", nullable = true)
    private String designation;

    @Column(name = "profile_picture", nullable = true)
    private String profilePicture;

    @Column(name = "user_type", nullable = true)
    private String userType;

    @Column(name = "parent_type_id")
    private Integer parentTypeId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "warehouse_id")
    private Integer warehouseId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id", insertable=false, updatable=false)
    private Role role;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_datetime")
    private Date createdDatetime;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_datetime")
    private Date updatedDatetime;

}
