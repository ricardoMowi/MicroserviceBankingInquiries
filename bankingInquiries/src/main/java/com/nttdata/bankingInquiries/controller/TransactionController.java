package com.nttdata.bankingInquiries.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.nttdata.bankingInquiries.entity.Transaction;
import com.nttdata.bankingInquiries.repository.TransactionRepository;
import com.nttdata.bankingInquiries.service.TransactionService;

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
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transRepo;
    
    //CRUD
    @GetMapping(value = "/all")
    public List<Transaction> getAll() {
        log.info("lista todos");
        return transactionService.getAll();
    }  

    @GetMapping("getTransaction/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getData(@PathVariable("id") String id){
      Map<String, Object> salida = new HashMap<>();
      Optional<Transaction> trans_doc = transRepo.findById(id);
      if (trans_doc.isPresent()) {
        salida.put("transaction", trans_doc);
      }else{
        salida.put("status", "Id de transacción no encontrado");
      }
      return ResponseEntity.ok(salida);
    }



    @PostMapping(value = "/create")
    public Transaction createTransaction(@RequestBody Transaction new_trans){
        new_trans.setStatus("ACTIVE");
        return transactionService.createTransaction(new_trans);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable("id") String id, @RequestBody Transaction temp) {
      Optional<Transaction> transaction = transRepo.findById(id);
      if (transaction.isPresent()) {
        temp.setId(id);
        return new ResponseEntity<>(transactionService.createTransaction(temp), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    @PutMapping("setInactive/{id}")
    public ResponseEntity<Transaction> setInactive(@PathVariable("id") String id, @RequestBody Transaction temp_trans) {
      Optional<Transaction> trans_doc = transRepo.findById(id);
      if (trans_doc.isPresent()) {
        Transaction _trans = trans_doc.get();
        _trans.setStatus("INACTIVE");
        return new ResponseEntity<>(transRepo.save(_trans), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } 

}
