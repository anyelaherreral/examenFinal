package co.edu.ufps.DTOs;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ClienteRequest {
	private String documento;
    private String nombre;
    private String tipoDocumento;
}
