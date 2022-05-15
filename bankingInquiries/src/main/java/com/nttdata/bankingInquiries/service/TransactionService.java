package com.nttdata.bankingInquiries.service;

import java.util.List;

import com.nttdata.bankingInquiries.entity.Transaction;

public interface TransactionService {
    List<Transaction> getAll();
    Transaction createTransaction(Transaction new_transaction);
}


