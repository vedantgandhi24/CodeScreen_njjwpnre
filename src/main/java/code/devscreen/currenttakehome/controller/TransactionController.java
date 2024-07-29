package code.devscreen.currenttakehome.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import code.devscreen.currenttakehome.DTO.AuthorizationRequestDTO;
import code.devscreen.currenttakehome.DTO.AuthorizationResponseDTO;
import code.devscreen.currenttakehome.DTO.LoadResponseDTO;
import code.devscreen.currenttakehome.services.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PutMapping(path="/authorization/{messageId}",
    consumes = {"application/json"},
    produces = {"application/json"})
    public ResponseEntity<AuthorizationResponseDTO> debitFunc(@PathVariable("messageId") String messageId, @RequestBody @Valid AuthorizationRequestDTO authorizationRequest){
        AuthorizationResponseDTO response = transactionService.debitFunc(messageId, authorizationRequest);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping(path="/load/{messageId}",
    consumes = {"application/json"},
    produces = {"application/json"})
    public ResponseEntity<LoadResponseDTO> loadFunc(@PathVariable("messageId") String messageId, @RequestBody @Valid AuthorizationRequestDTO loadRequest){
        LoadResponseDTO response = transactionService.loadFunc(messageId, loadRequest);
    
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}