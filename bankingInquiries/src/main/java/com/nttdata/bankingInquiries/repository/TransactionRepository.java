package com.nttdata.bankingInquiries.repository;

import com.nttdata.bankingInquiries.entity.Transaction;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository <Transaction, String> {
    
}
