package co.edu.ufps.DTOs;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DataFactura {
	private Integer numero;  // ID de la compra
    private Integer total;   // Total de la compra
    private LocalDate fecha; // Fecha de la compra
    
    public DataFactura() {
    }

    public DataFactura(Integer numero, Integer total, LocalDate fecha) {
        this.numero = numero;
        this.total = total;
        this.fecha = fecha;
    }
}
