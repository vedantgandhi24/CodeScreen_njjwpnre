package code.devscreen.currenttakehome.DTO;

import lombok.Data;

@Data
public class AuthorizationResponseDTO {
    private String userId;
    private String messageId;
    private ResponseCode responseCode;
    private AmountDTO balance;
}
