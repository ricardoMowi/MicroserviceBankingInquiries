package com.nttdata.bankingInquiries.repository;

import com.nttdata.bankingInquiries.entity.Client;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository  extends MongoRepository <Client, String> {
    
}
