package co.edu.umanizales.manage_store.service;

import co.edu.umanizales.manage_store.controller.dto.BestSellerDTO;
import co.edu.umanizales.manage_store.controller.dto.BestStoreDTO;
import co.edu.umanizales.manage_store.model.Sale;
import co.edu.umanizales.manage_store.model.Seller;
import co.edu.umanizales.manage_store.model.Store;
import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class SaleService {
    private List<Sale> sales;

    public SaleService(){
        this.sales = new ArrayList<>();
    }

    public void addSale(Sale sale){
        this.sales.add(sale);
    }
    
    public int getTotalSales(){
        int sum = 0 ;
        for (Sale sale:sales) {
            sum =sum +sale.getQuantity();
        }
        return sum;
    }
    public int getTotalSalesBySeller(String codSeller){
        int sum = 0 ;
        for (Sale sale:sales) {
            if (sale.getSeller().getCode().equals(codSeller)) {
                sum = sum + sale.getQuantity();
            }
        }
        return sum;
    }

    public int getTotalSalesByStore(String codStore){
        int sum = 0 ;
        for (Sale sale:sales) {
            if (sale.getStore().getCode().equals(codStore)) {
                sum = sum + sale.getQuantity();
            }
        }
        return sum;
    }

    public BestStoreDTO getBestStoreDTO(List<Store> stores){
        BestStoreDTO bestStoreDTO = new BestStoreDTO(new Store("1","Armenia"),0);
        for (Store store:stores) {
            int pos = getTotalSalesByStore(store.getCode());
            if (pos >= bestStoreDTO.getQuantity()){
                bestStoreDTO = new BestStoreDTO(store,pos);
            }
        }
        return bestStoreDTO;
    }

    public BestSellerDTO getBestSellerDTO(List<Seller> sellers){
        BestSellerDTO bestSellerDTO = new BestSellerDTO(new Seller("1","Juan"),0);
        for (Seller seller:sellers) {
            int pos = getTotalSalesBySeller(seller.getCode());
            if (pos >= bestSellerDTO.getQuantity()){
                bestSellerDTO = new BestSellerDTO(seller,pos);
            }
        }
        return bestSellerDTO;
    }
}