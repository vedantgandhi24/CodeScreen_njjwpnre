package code.devscreen.currenttakehome.DTO;

import lombok.Data;

@Data
public class LoadResponseDTO {
    private String userId;
    private String messageId;
    private AmountDTO balance; 
}
