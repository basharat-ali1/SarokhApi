package net.sarokh.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "country", schema = "sarokh_db")
public class Country {

    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @Column(name = "continent", nullable = true)
    private String continent;

    @JsonIgnore
    @Column(name = "region", nullable = true)
    private String region;

    @JsonIgnore
    @Column(name = "capital", nullable = true)
    private String capital;

    @JsonIgnore
    @Column(name = "code2", nullable = true)
    private String code2;

}
