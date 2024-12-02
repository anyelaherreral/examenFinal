package co.edu.ufps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.ufps.services.FacturaService;
import co.edu.ufps.DTOs.FacturaRequestDTO;
import co.edu.ufps.DTOs.FacturaResponseDTO;

@RestController
@RequestMapping("/consultar")
public class FacturaController {
	@Autowired
    private FacturaService facturaService;

    @PostMapping("/{facturaId}")
    public ResponseEntity<FacturaResponseDTO> consultarFactura(
            @PathVariable int facturaId,
            @RequestBody FacturaRequestDTO request) {
        try {
            // Asignar el ID de factura desde el path variable al DTO
            request.setFactura(facturaId);

            // Consultar factura a través del servicio
            FacturaResponseDTO response = facturaService.consultarFactura(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Manejo de errores con respuesta 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            // Manejo de errores inesperados con respuesta 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}