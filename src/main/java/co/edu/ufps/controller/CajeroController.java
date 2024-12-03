package co.edu.ufps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.ufps.services.CajeroService;
import co.edu.ufps.services.ClienteService;
import co.edu.ufps.services.FacturaService;
import co.edu.ufps.services.TiendaService;
import co.edu.ufps.services.VendedorService;
import co.edu.ufps.DTOs.FacturaRequestDTO;
import co.edu.ufps.DTOs.FacturaResponseDTO;
import co.edu.ufps.entities.Cajero;
import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.Tienda;
import co.edu.ufps.entities.Vendedor;

@RestController
@RequestMapping("/api/cajeros")
public class CajeroController {
	
	@Autowired
    private CajeroService cajeroService;

    // Crear o actualizar un cajero
    @PostMapping
    public ResponseEntity<Cajero> guardarCajero(@RequestBody Cajero cajero) {
        Cajero cajeroGuardado = cajeroService.guardarCajero(cajero);
        return ResponseEntity.status(HttpStatus.CREATED).body(cajeroGuardado);
    }

    // Listar todos los cajeros
    @GetMapping
    public ResponseEntity<List<Cajero>> listarCajeros() {
        List<Cajero> cajeros = cajeroService.listarCajeros();
        return ResponseEntity.ok(cajeros);
    }

    // Obtener un cajero por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Cajero> obtenerCajeroPorId(@PathVariable Integer id) {
        Cajero cajero = cajeroService.obtenerCajeroPorId(id);
        return ResponseEntity.ok(cajero);
    }

    // Eliminar un cajero por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCajero(@PathVariable Integer id) {
        cajeroService.eliminarCajero(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar un cajero por su documento
    @GetMapping("/buscar-por-documento/{documento}")
    public ResponseEntity<Cajero> obtenerCajeroPorDocumento(@PathVariable String documento) {
        Cajero cajero = cajeroService.obtenerCajeroPorDocumento(documento);
        return ResponseEntity.ok(cajero);
    }
}