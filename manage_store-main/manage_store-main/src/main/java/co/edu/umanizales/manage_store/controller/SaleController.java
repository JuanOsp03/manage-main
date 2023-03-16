package co.edu.umanizales.manage_store.controller;

import co.edu.umanizales.manage_store.controller.dto.*;
import co.edu.umanizales.manage_store.model.Sale;
import co.edu.umanizales.manage_store.model.Seller;
import co.edu.umanizales.manage_store.model.Store;
import co.edu.umanizales.manage_store.service.SaleService;
import co.edu.umanizales.manage_store.service.SellerService;
import co.edu.umanizales.manage_store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "Sale")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StoreService storeService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getSales(){
        return  new ResponseEntity<>(new ResponseDTO(200, saleService.getSales() , null) , HttpStatus.OK);
    }

    @GetMapping(path = "/total")
    public ResponseEntity<ResponseDTO> getTotalSales(){
        return new ResponseEntity<>(new ResponseDTO(200,saleService.getTotalSales(),null),HttpStatus.OK);
    }

    @GetMapping(path = "/total/{code}")
    public ResponseEntity<ResponseDTO> getTotalSalesBySeller(@PathVariable String code){
        return new ResponseEntity<>(new ResponseDTO(200,saleService.getTotalSalesBySeller(code),null),HttpStatus.OK);
    }

    @GetMapping(path = "/totalVentas*Store/{code}")
    public ResponseEntity<ResponseDTO> getTotalSalesByStore(@PathVariable String code){
        return new ResponseEntity<>(new ResponseDTO(200,saleService.getTotalSalesByStore(code),null),HttpStatus.OK);
    }

    @GetMapping(path = "/totalbestStore")
    public ResponseEntity<ResponseDTO> getBestStoreDTO(){
        return new ResponseEntity<>(new ResponseDTO(200,saleService.getBestStoreDTO(storeService.getStores()),null),HttpStatus.OK);
    }

    @GetMapping(path = "/totalbestSeller")
    public ResponseEntity<ResponseDTO> getBestSellerDTO(){
        return new ResponseEntity<>(new ResponseDTO(200,(saleService.getBestSellerDTO(sellerService.getSellers())),null),HttpStatus.OK);
    }

    @GetMapping(path = "/averagesalesbystores")
    public ResponseEntity<ResponseDTO> getAverageSaleByStore(){
        int finSale = saleService.getTotalSales();
        while(finSale != 0){
            return new ResponseEntity<>(new ResponseDTO(200, saleService.getTotalSales()/(float)storeService.getStores().size(),null),HttpStatus.OK);
        }
            return new ResponseEntity<>(new ResponseDTO(409, "No hay ventas, no se puede obtener un promedio", null), HttpStatus.BAD_REQUEST);
        }

    @GetMapping(path = "/averagesalesbysellers")
    public ResponseEntity<ResponseDTO> getAverageSaleBySellers() {
        int findSale = saleService.getTotalSales();
        while(findSale != 0) {
            return new ResponseEntity<>(new ResponseDTO(200, saleService.getTotalSales() / (float) sellerService.getSellers().size(), null), HttpStatus.OK);
        }
            return new ResponseEntity<>(new ResponseDTO(409, "No hay ventas, no se puede obtener un promedio", null), HttpStatus.BAD_REQUEST);
    }


    //----------------------------------------------- PROTOTIPO ---------------------------------
    @GetMapping(path = "/sellerMoreOneSale/{cant}")
    public ResponseEntity<ResponseDTO> getSalesBySellerMoreOne (@PathVariable int cant) {
        if (saleService.getTotalSales() == 0) {
            return new ResponseEntity<>(new ResponseDTO(404, "No hay vendedores que cumplan con este requisito", null)
                    , HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(new ResponseDTO(200,saleService.getSalesbySellersByCant(cant),null),HttpStatus.OK);
        }
    }
//--------------------------------------------------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<ResponseDTO> createSale(@RequestBody SaleDTO saleDTO) {
        Seller findSeller = sellerService.getSellerById(saleDTO.getSellerId());
        if (findSeller == null) {
            return new ResponseEntity<>(new ResponseDTO(409, "El vendedor ingresado no existe", null),
                    HttpStatus.BAD_REQUEST);
        }
        Store findStore = storeService.getStoreById(saleDTO.getStoreId());
        if(findStore == null){
            return new ResponseEntity<>(new ResponseDTO(409, "La tienda ingresado no existe", null),
                    HttpStatus.BAD_REQUEST);
        }
        saleService.addSale(new Sale(findStore,findSeller, saleDTO.getQuantity()));
        return new ResponseEntity<>(new ResponseDTO(200, "Venta adicionada" , null), HttpStatus.OK);
    }

} // fin c_SaleController