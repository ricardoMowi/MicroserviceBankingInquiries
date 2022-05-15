package com.nttdata.bankingInquiries.service.Impl;

import java.util.List;

import com.nttdata.bankingInquiries.entity.Transaction;
import com.nttdata.bankingInquiries.repository.TransactionRepository;
import com.nttdata.bankingInquiries.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public List<Transaction> getAll() {
      return transactionRepository.findAll();
    }

    @Override
    public Transaction createTransaction(Transaction transaction){
      return transactionRepository.save(transaction);
    }
}