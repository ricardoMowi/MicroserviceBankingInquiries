package com.nttdata.bankingInquiries.service;

import java.util.List;

import com.nttdata.bankingInquiries.entity.Client;
public interface ClientService {
    List<Client> getAll();
    Client createClient(Client new_client);
}
