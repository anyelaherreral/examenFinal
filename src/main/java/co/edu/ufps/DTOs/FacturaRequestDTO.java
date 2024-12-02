package co.edu.ufps.DTOs;

import java.math.BigDecimal;
import java.util.List;

import co.edu.ufps.entities.Vendedor;
import lombok.Data;

@Data
public class FacturaRequestDTO {
	private String token;
    private String cliente;
    private int factura;
    
}
