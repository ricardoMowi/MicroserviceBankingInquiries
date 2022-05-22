package com.nttdata.bankingInquiries.repository;

import java.util.Date;
import java.util.List;

import com.nttdata.bankingInquiries.entity.Transaction;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository <Transaction, String> {
    List<Transaction> findByIdProduct (String IdProduct);
    List<Transaction> findByIdProductAndFlagWithCommission (String IdProduct, Boolean flagWithCommission);
    List <Transaction> findByRegisterDateBetween( Date startDate, Date endDate);
}
