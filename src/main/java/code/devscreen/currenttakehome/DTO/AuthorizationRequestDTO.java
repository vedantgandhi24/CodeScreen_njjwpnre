package code.devscreen.currenttakehome.DTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthorizationRequestDTO {
    @NotNull
    private String userId;
    @NotNull
    private String messageId;
    @NotNull
    private AmountDTO transactionAmount;
}
