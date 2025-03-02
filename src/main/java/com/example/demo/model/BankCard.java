package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BankCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankCardID;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    @JsonIgnore
    private User user;

    private String cardNumber;
    private String bankName;
    private LocalDate expirationDate;

    @OneToMany(mappedBy = "bankCard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

    // Getters and Setters
    public Long getBankCardID() {
        return bankCardID;
    }

    public void setBankCardID(Long bankCardID) {
        this.bankCardID = bankCardID;
    }

    public User getAccount() {
        return user;
    }

    public void setAccount(User account) {
        this.user = account;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
