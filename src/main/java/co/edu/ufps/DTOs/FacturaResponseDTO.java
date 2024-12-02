package co.edu.ufps.DTOs;

import java.util.List;

import lombok.Data;

@Data
public class FacturaResponseDTO {
	
    private double total;
    private double impuestos;
    private ClienteDTO cliente;
    private List<ProductoDTO> productos;
    private CajeroDTO cajero;

    // Getters y Setters

    // Inner classes para ClienteDTO, ProductoDTO y CajeroDTO
    public static class ClienteDTO {
        private String documento;
        private String nombre;
        private String tipo_documento;

        // Getters y Setters
    }

    public static class ProductoDTO {
        private String referencia;
        private String nombre;
        private int cantidad;
        private double precio;
        private double descuento;
        private double subtotal;

        // Getters y Setters
    }

    public static class CajeroDTO {
        private String documento;
        private String nombre;

        // Getters y Setters
    }
}