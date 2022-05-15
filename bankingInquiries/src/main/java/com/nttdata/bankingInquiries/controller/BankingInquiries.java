package com.nttdata.bankingInquiries.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.nttdata.bankingInquiries.entity.Client;
import com.nttdata.bankingInquiries.entity.Product;
import com.nttdata.bankingInquiries.entity.Transaction;
import com.nttdata.bankingInquiries.repository.ClientRepository;
import com.nttdata.bankingInquiries.repository.ProductRepository;
import com.nttdata.bankingInquiries.repository.TransactionRepository;

import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bankingInquiries")
public class BankingInquiries {

    @Autowired
    private TransactionRepository trans_repo;

    @Autowired
    private ProductRepository product_repo;

    @Autowired
    private ClientRepository client_repo;

    //El sistema debe permitir consultar los saldos disponibles en sus productos como: cuentas bancarias y tarjetas de crédito.
    @GetMapping("GetBankBalance/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> GetBankBalance(@PathVariable("id") String id){
        Map<String, Object> salida = new HashMap<>();
        //Obtener data del producto 
        Optional<Product> produc_doc = product_repo.findById(id);
        Product data_pro = Product.class.cast(produc_doc);
        //Obtener data del cliente
        Optional<Client> client_doc = client_repo.findById(data_pro.getClientId());
        Client data_cli = Client.class.cast(client_doc);

        if (produc_doc.isPresent()) {
            //preparar data           
            Map<String, Object> data = new HashMap<>();
            data.put("BankBalance", data_pro.getAmount());
            data.put("IdProduct", data_pro.getId());
            data.put("ProductType", data_pro.getProductType());
            data.put("Client", data_cli.getLastName() + "," + data_cli.getName());
            salida.put("data", data);
        }else{
            salida.put("status", "Id del producto no encontrado");
        }
        return ResponseEntity.ok(salida);
    }


    //El sistema debe permitir consultar todos los movimientos de un producto bancario que tiene un cliente.
    //microservicio: obtener todos los movimientos por producto
    @GetMapping("GetTransactionsByProduct/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> GetTransactionsByProduct(@PathVariable("id") String id){
        Map<String, Object> salida = new HashMap<>();
        //Validar id del cliente
        Optional<Product> produc_doc = product_repo.findById(id);
        if (produc_doc.isPresent()) {
            //obtener cantidad de productos
            List <Transaction> transactions = trans_repo.findByIdProduct(id);  
            salida.put("transactions", transactions);
        }else{
            salida.put("status", "Id del producto no encontrado");
        }
        return ResponseEntity.ok(salida);
    }

}
