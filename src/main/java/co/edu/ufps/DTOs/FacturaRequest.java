package co.edu.ufps.DTOs;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import co.edu.ufps.entities.Vendedor;
import lombok.Data;

@Data
public class FacturaRequest {
	private Integer impuesto;
    private ClienteRequest cliente;
    private List<ProductoRequest> productos;
    @JsonProperty("medios_pago")
    private List<PagoRequest> mediosPago;
    private VendedorRequest vendedor;
    private CajeroRequest cajero;
    
}
