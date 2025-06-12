package com.banking.core.transferservice.repository;

import com.banking.core.transferservice.entity.Transaction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  @Query("SELECT t FROM Transaction t " +
         "WHERE (t.fromAccount = :acc OR t.toAccount = :acc) " +
         "AND YEAR(t.timestamp) = :year AND MONTH(t.timestamp) = :month " +
         "ORDER BY t.timestamp ASC")
  List<Transaction> findByAccountAndMonth(
      @Param("acc") String accountNumber,
      @Param("year") int year,
      @Param("month") int month);

  //find by transactionId
  Optional<Transaction> findByTransactionId(String transactionId);


}
