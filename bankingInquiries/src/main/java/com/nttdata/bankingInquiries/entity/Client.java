package com.nttdata.bankingInquiries.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Document(collection="client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    private String id;
    private String clientType;
    private String name;
    private String lastName;
    private String RUC;
    private String address;
    private String email;
    private String status; 
}