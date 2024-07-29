package code.devscreen.currenttakehome.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import code.devscreen.currenttakehome.DTO.AmountDTO;
import code.devscreen.currenttakehome.DTO.AuthorizationRequestDTO;
import code.devscreen.currenttakehome.DTO.AuthorizationResponseDTO;
import code.devscreen.currenttakehome.DTO.CreditDebit;
import code.devscreen.currenttakehome.DTO.CreditDebitDTO;
import code.devscreen.currenttakehome.DTO.LoadResponseDTO;
import code.devscreen.currenttakehome.DTO.ResponseCode;
import code.devscreen.currenttakehome.exception.HandleInsufficientBalanceException;
import code.devscreen.currenttakehome.exception.HandleTransactionAlreadyExistsException;
import code.devscreen.currenttakehome.models.Transactions;
import code.devscreen.currenttakehome.repository.TransactionsRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService {
    
    private final TransactionsRepository transactionsRepository;

    public AuthorizationResponseDTO debitFunc(String messageId, AuthorizationRequestDTO authorizationRequest){
        AuthorizationResponseDTO response = new AuthorizationResponseDTO();
        CreditDebitDTO creditDebitDTO = creditAndDebitFunc(messageId, authorizationRequest);
        
        AmountDTO updatedBalance = new AmountDTO();
        updatedBalance.setAmount(Double.toString(creditDebitDTO.getAmount()));    
        updatedBalance.setCurrency(creditDebitDTO.getTransaction().getCurrency());
        updatedBalance.setDebitOrCredit(creditDebitDTO.getTransaction().getDebitOrCredit());
        response.setMessageId(messageId);
        response.setUserId(authorizationRequest.getUserId());
        response.setResponseCode(ResponseCode.APPROVED);
        response.setBalance(updatedBalance);
        return response;
    }


    public LoadResponseDTO loadFunc(String messageId, AuthorizationRequestDTO loadRequest){
        LoadResponseDTO response = new LoadResponseDTO();
        CreditDebitDTO creditDebitDTO = creditAndDebitFunc(messageId, loadRequest);
        
        //Set new balance
        AmountDTO updatedBalance = new AmountDTO();
        updatedBalance.setAmount(Double.toString(creditDebitDTO.getAmount()));    
        updatedBalance.setCurrency(creditDebitDTO.getTransaction().getCurrency());
        updatedBalance.setDebitOrCredit(creditDebitDTO.getTransaction().getDebitOrCredit());
        response.setMessageId(messageId);
        response.setUserId(loadRequest.getUserId());
        response.setBalance(updatedBalance);
        return response;
    }

    public CreditDebitDTO creditAndDebitFunc(String messageId, AuthorizationRequestDTO authorizationRequest){
        checkIfTransactionExist(messageId);
        AmountDTO reqTransactionAmt = authorizationRequest.getTransactionAmount();
        CreditDebit creditDebit = reqTransactionAmt.getDebitOrCredit();
        Transactions newTransac = new Transactions();
        double transactionAmt = Double.parseDouble(reqTransactionAmt.getAmount());
        newTransac.setMessageId(messageId);
        newTransac.setUserId(authorizationRequest.getUserId());
        newTransac.setAmount(transactionAmt);
        newTransac.setDebitOrCredit(creditDebit);
        newTransac.setCurrency(reqTransactionAmt.getCurrency());
        newTransac.setStatus(ResponseCode.APPROVED);
        double updatedBalance=0;
        double balance = calculateUserBalanceAmount(authorizationRequest.getUserId());
        if(creditDebit.equals(CreditDebit.DEBIT)){
            if (!(balance > transactionAmt)){
                newTransac.setStatus(ResponseCode.DECLINED);
                transactionsRepository.save(newTransac);
                throw new HandleInsufficientBalanceException("User has Insufficient Balance.");
            }
            updatedBalance = balance-transactionAmt;
        }else{
            updatedBalance= balance+transactionAmt;
        }
        
        transactionsRepository.save(newTransac);

        CreditDebitDTO creditDebitDTO = new CreditDebitDTO();
        creditDebitDTO.setTransaction(newTransac);
        creditDebitDTO.setAmount(updatedBalance);

        return creditDebitDTO;
    }

    public void checkIfTransactionExist(String messageId){
        Optional<Transactions> trOptional = transactionsRepository.findById(messageId);
        if(trOptional.isPresent()){
            throw new HandleTransactionAlreadyExistsException("Transaction with id already exists");
        }
    }

    public double calculateUserBalanceAmount(String userId){
        List<Transactions> transactions = transactionsRepository.findTransactionsByUserIdAndStatus(userId, ResponseCode.APPROVED);
        double balance = 0;
        for (Transactions transaction : transactions) {
            if(transaction.getDebitOrCredit().equals(CreditDebit.CREDIT)){
                balance += transaction.getAmount();
            }else{
                balance-=transaction.getAmount();
            }
        }
        return balance;
    }
}