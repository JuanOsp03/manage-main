package co.edu.umanizales.manage_store.controller.dto;

import co.edu.umanizales.manage_store.model.Store;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;

@AllArgsConstructor
@Data
public class BestStoreDTO {

 private Store store;
 private int quantity;

}