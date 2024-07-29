package code.devscreen.currenttakehome.DTO;

import code.devscreen.currenttakehome.models.Transactions;
import lombok.Data;

@Data
public class CreditDebitDTO {
    private Transactions transaction;
    private double amount;
}
