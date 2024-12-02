package co.edu.ufps.DTOs;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PagoRequest {
	private String tipoPago;
    private String tipoTarjeta;
    private int cuotas;
    private BigDecimal valor;
}
