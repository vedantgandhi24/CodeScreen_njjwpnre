package code.devscreen.currenttakehome;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import code.devscreen.currenttakehome.DTO.AmountDTO;
import code.devscreen.currenttakehome.DTO.AuthorizationRequestDTO;
import code.devscreen.currenttakehome.DTO.CreditDebit;
import code.devscreen.currenttakehome.DTO.CreditDebitDTO;
import code.devscreen.currenttakehome.exception.HandleInsufficientBalanceException;
import code.devscreen.currenttakehome.repository.TransactionsRepository;
import code.devscreen.currenttakehome.services.TransactionService;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners(MockitoTestExecutionListener.class)
@ExtendWith(MockitoExtension.class)
public class TransactionServiceUnitTest {

    @InjectMocks
    @Spy
    private TransactionService transactionService;

    @Spy
    private TransactionsRepository transactionsRepository;

    @Test
    public void checkCreditFunc(){

        AuthorizationRequestDTO newTransacReq = new AuthorizationRequestDTO();
        AmountDTO newAmount = new AmountDTO();

        newAmount.setAmount("15000.00");
        newAmount.setCurrency("USD");
        newAmount.setDebitOrCredit(CreditDebit.CREDIT);

        newTransacReq.setMessageId("50e70c62-e480-49fc-bc1b-e991ac672172");
        newTransacReq.setUserId( "018b2f19-e79e-7d6a-a56d-29feb6211b04");
        newTransacReq.setTransactionAmount(newAmount);
        Mockito.doNothing().when(transactionService).checkIfTransactionExist("50e70c62-e480-49fc-bc1b-e991ac672172");
        CreditDebitDTO respCreditDebitDTO = transactionService.creditAndDebitFunc(newTransacReq.getMessageId(), newTransacReq);
        assertEquals(15000.0, respCreditDebitDTO.getAmount(),1);
    } 

    @Test
    public void checkCreditFuncWithTransPresent(){

        AuthorizationRequestDTO newTransacReq = new AuthorizationRequestDTO();
        AmountDTO newAmount = new AmountDTO();

        newAmount.setAmount("15000.00");
        newAmount.setCurrency("USD");
        newAmount.setDebitOrCredit(CreditDebit.CREDIT);

        newTransacReq.setMessageId("50e70c62-e480-49fc-bc1b-e991ac672172");
        newTransacReq.setUserId( "018b2f19-e79e-7d6a-a56d-29feb6211b04");
        newTransacReq.setTransactionAmount(newAmount);
        doReturn(10000.0).when(transactionService).calculateUserBalanceAmount(any());
        Mockito.doNothing().when(transactionService).checkIfTransactionExist("50e70c62-e480-49fc-bc1b-e991ac672171");
        CreditDebitDTO respCreditDebitDTO = transactionService.creditAndDebitFunc(newTransacReq.getMessageId(), newTransacReq);
        assertEquals(25000.0, respCreditDebitDTO.getAmount(),1);
    } 

    @Test
    public void checkValidDebitFuncWithTransPresent(){

        AuthorizationRequestDTO newTransacReq = new AuthorizationRequestDTO();
        AmountDTO newAmount = new AmountDTO();

        newAmount.setAmount("15000.00");
        newAmount.setCurrency("USD");
        newAmount.setDebitOrCredit(CreditDebit.DEBIT);

        newTransacReq.setMessageId("50e70c62-e480-49fc-bc1b-e991ac672172");
        newTransacReq.setUserId( "018b2f19-e79e-7d6a-a56d-29feb6211b04");
        newTransacReq.setTransactionAmount(newAmount);
        doReturn(20000.0).when(transactionService).calculateUserBalanceAmount(any());
        Mockito.doNothing().when(transactionService).checkIfTransactionExist("50e70c62-e480-49fc-bc1b-e991ac672171");
        CreditDebitDTO respCreditDebitDTO = transactionService.creditAndDebitFunc(newTransacReq.getMessageId(), newTransacReq);
        assertEquals(5000.0, respCreditDebitDTO.getAmount(),1);
    } 

    @Test
    public void checkInvalidDebitFuncWithTransPresent(){

        AuthorizationRequestDTO newTransacReq = new AuthorizationRequestDTO();
        AmountDTO newAmount = new AmountDTO();

        newAmount.setAmount("15000.00");
        newAmount.setCurrency("USD");
        newAmount.setDebitOrCredit(CreditDebit.DEBIT);

        newTransacReq.setMessageId("50e70c62-e480-49fc-bc1b-e991ac672172");
        newTransacReq.setUserId( "018b2f19-e79e-7d6a-a56d-29feb6211b04");
        newTransacReq.setTransactionAmount(newAmount);
        doReturn(10000.0).when(transactionService).calculateUserBalanceAmount(any());
        Mockito.doNothing().when(transactionService).checkIfTransactionExist("50e70c62-e480-49fc-bc1b-e991ac672171");
        doThrow(new HandleInsufficientBalanceException("User has Insufficient Balance.")).when(transactionService).creditAndDebitFunc(newTransacReq.getMessageId(), newTransacReq);
    } 
}