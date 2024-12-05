package co.edu.ufps.DTOs;

import java.math.BigDecimal;
import java.util.List;

import co.edu.ufps.entities.Vendedor;
import lombok.Data;

@Data
public class RespuestaApi {
	private String status;
    private String message;
    private DataFactura data;
    
    public RespuestaApi() {}

    public RespuestaApi(String status, String message, DataFactura data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
