package com.nttdata.bankingInquiries.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.nttdata.bankingInquiries.entity.Client;
import com.nttdata.bankingInquiries.repository.ClientRepository;
import com.nttdata.bankingInquiries.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepo;
    
    //CRUD
    @GetMapping(value = "/all")
    public List<Client> getAll() {
        log.info("lista todos");
        return clientService.getAll();
    }  

    @GetMapping("getClient/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getClientData(@PathVariable("id") String id){
      Map<String, Object> salida = new HashMap<>();
      Optional<Client> client_doc = clientRepo.findById(id);
      if (client_doc.isPresent()) {
        salida.put("client", client_doc);
      }else{
        salida.put("status", "Id de Cliente no encontrado");
      }
      return ResponseEntity.ok(salida);
    }



    @PostMapping(value = "/create")
    public Client createClient(@RequestBody Client new_client){
        new_client.setStatus("ACTIVE");
        return clientService.createClient(new_client);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") String id, @RequestBody Client temp) {
      Optional<Client> client = clientRepo.findById(id);
      if (client.isPresent()) {
        temp.setId(id);
        return new ResponseEntity<>(clientService.createClient(temp), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    @PutMapping("setInactive/{id}")
    public ResponseEntity<Client> setInactive(@PathVariable("id") String id, @RequestBody Client temp_client) {
      Optional<Client> client_doc = clientRepo.findById(id);
      if (client_doc.isPresent()) {
        Client _client = client_doc.get();
        _client.setStatus("INACTIVE");
        return new ResponseEntity<>(clientRepo.save(_client), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } 

}
