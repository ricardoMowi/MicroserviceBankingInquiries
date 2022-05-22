package com.nttdata.bankingInquiries.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@Slf4j

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

    //Para un cliente se debe generar un resumen con los saldos promedio diarios del mes en curso de todos los productos de crédito o cuentas bancarias que posee.
    @GetMapping("/GetCommissionReport")
    //@RequestMapping(path = "/GetCommissionReport", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> GetProductReport(@RequestParam String id){
        log.info("entrada GetProductReport");
        Map<String, Object> salida = new HashMap<>();   
        //Validar id del producto
        Optional<Client> produc_doc = client_repo.findById(id);
        if (produc_doc.isPresent()) {
            //obtener productos por cliente
            List <Product> products = product_repo.findByClientId(id);            
            salida.put("products", products);
        }else{
            salida.put("status", "Id del producto no encontrado");
        }
        return ResponseEntity.ok(salida);
    }


    //Generar un reporte de todas las comisiones cobradas por producto en un periodo de tiempo
    @GetMapping("/GetCommissionReport")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> GetCommissionReport(@RequestParam String id, @RequestParam String startDate, @RequestParam String endDate){
        log.info("entrada GetCommissionReport");
        Map<String, Object> salida = new HashMap<>();   
        //Transformar string a date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH); 
        LocalDate date = LocalDate.parse(startDate, formatter);
        Date dateA = java.sql.Date.valueOf(date);
        LocalDate date1 = LocalDate.parse(endDate, formatter);
        Date dateB = java.sql.Date.valueOf(date1);

        //Validar id del producto
        Optional<Product> produc_doc = product_repo.findById(id);
        if (produc_doc.isPresent()) {
            //obtener transacciones en un periodo
            List <Transaction> transactions = trans_repo.findByRegisterDateBetween(dateA, dateB);
            //filtrar por idProduct y por isFlagWithCommission == true
            List <Transaction> transactions_r =  transactions.stream().filter(transaction -> 
                (transaction.getIdProduct().equals(id) && transaction.isFlagWithCommission() )).collect(Collectors.toList());  

            
            salida.put("report", transactions_r);
        }else{
            salida.put("status", "Id del producto no encontrado");
        }
        return ResponseEntity.ok(salida);
    }

}
