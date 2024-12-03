package co.edu.ufps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.ufps.services.CompraService;
import co.edu.ufps.services.FacturaService;
import co.edu.ufps.services.TiendaService;
import jakarta.persistence.EntityNotFoundException;
import co.edu.ufps.DTOs.FacturaRequest;
import co.edu.ufps.DTOs.FacturaRequestDTO;
import co.edu.ufps.DTOs.FacturaResponseDTO;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.Tienda;

@RestController
@RequestMapping("/compras")
public class CompraController {
	@Autowired
    CompraService compraService;
	@Autowired
    TiendaService tiendaService;

	@PostMapping("/crear/{uuid}")
    public ResponseEntity<?> crearCompra(@PathVariable String uuid, @RequestBody FacturaRequest facturaRequest) {
        try {
        	Tienda tienda = tiendaService.buscarTiendaPorUuid(uuid);
        	
            // Llamar al servicio para crear la compra
            Compra compra = compraService.crearCompra(facturaRequest, tienda);

            // Devolver la respuesta con la compra creada
            return ResponseEntity.ok(compra);
        } catch (EntityNotFoundException e) {
            // Si la tienda no existe, retornamos un error con un mensaje adecuado
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tienda no encontrada con UUID: " + uuid);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}