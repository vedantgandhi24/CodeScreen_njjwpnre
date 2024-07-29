package code.devscreen.currenttakehome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import code.devscreen.currenttakehome.DTO.ResponseCode;
import code.devscreen.currenttakehome.models.Transactions;
import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository < Transactions, String>{
    List<Transactions> findTransactionsByUserIdAndStatus(String userId, ResponseCode status);
}
