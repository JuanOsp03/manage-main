package co.edu.umanizales.manage_store.controller.dto;

import co.edu.umanizales.manage_store.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class VendedorxCantDTO {
    private List<Seller> seller;
    private int cant;
}
