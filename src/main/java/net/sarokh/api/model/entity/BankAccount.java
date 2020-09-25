package net.sarokh.api.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "bank_account", schema = "Sarokh_db")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "account_number", nullable = true, length = 45)
    private String accountNumber;

    @Column(name = "bank", nullable = true, length = 45)
    private String bank;

    @Column(name = "IBAN", nullable = true, length = 45)
    private String iban;


}
