package code.devscreen.currenttakehome.models;

import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;

import code.devscreen.currenttakehome.DTO.CreditDebit;
import code.devscreen.currenttakehome.DTO.ResponseCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transactions {
    @Id
    @Column(nullable = false)
    private String messageId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    private CreditDebit debitOrCredit;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdOn;

    @Enumerated(EnumType.STRING)
    private ResponseCode status;
}
