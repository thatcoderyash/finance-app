package com.zorvyn.finance.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "financial_records")
public class FinancialRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String category;

    private LocalDate date;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User createdBy;

    public enum Type {
        INCOME,
        EXPENSE
    }

}
