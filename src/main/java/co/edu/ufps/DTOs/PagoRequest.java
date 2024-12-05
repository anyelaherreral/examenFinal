package co.edu.ufps.DTOs;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PagoRequest {
	@JsonProperty("tipo_pago")
	private String tipoPago;
	@JsonProperty("tipo_tarjeta")
    private String tipoTarjeta;
    private int cuotas;
    private Integer valor;
}
