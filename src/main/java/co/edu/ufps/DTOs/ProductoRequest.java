package co.edu.ufps.DTOs;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductoRequest {
	private String referencia;
    private int cantidad;
    private Integer descuento;
}
