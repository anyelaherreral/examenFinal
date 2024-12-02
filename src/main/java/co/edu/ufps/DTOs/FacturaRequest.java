package co.edu.ufps.DTOs;

import java.math.BigDecimal;
import java.util.List;

import co.edu.ufps.entities.Vendedor;
import lombok.Data;

@Data
public class FacturaRequest {
	private BigDecimal impuesto;
    private ClienteRequest cliente;
    private List<ProductoRequest> productos;
    private List<PagoRequest> mediosPago;
    private Vendedor vendedor;
    private CajeroRequest cajero;
    
}
