package code.devscreen.currenttakehome.DTO;

import lombok.Data;

@Data
public class AmountDTO {
    private String amount;
    private String currency;
    private CreditDebit debitOrCredit;
}