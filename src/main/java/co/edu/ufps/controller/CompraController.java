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
import co.edu.ufps.DTOs.DataFactura;
import co.edu.ufps.DTOs.FacturaRequest;
import co.edu.ufps.DTOs.FacturaRequestDTO;
import co.edu.ufps.DTOs.FacturaResponseDTO;
import co.edu.ufps.DTOs.RespuestaApi;
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
	public ResponseEntity<RespuestaApi> crearCompra(@PathVariable String uuid, @RequestBody FacturaRequest facturaRequest) {
	    try {
	        Tienda tienda = tiendaService.buscarTiendaPorUuid(uuid);
	        if (tienda == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new RespuestaApi("error", "Tienda no encontrada con UUID: " + uuid, null));
	        }

	        // Llamar al servicio para crear la compra
	        RespuestaApi respuesta = compraService.crearCompra(facturaRequest, tienda);

	        // Retornar la respuesta del servicio
	        return ResponseEntity.ok(respuesta);

	    } catch (EntityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(new RespuestaApi("error", e.getMessage(), null));
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                .body(new RespuestaApi("error", e.getMessage(), null));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new RespuestaApi("error", "Ocurrió un error inesperado: " + e.getMessage(), null));
	    }
	}

}