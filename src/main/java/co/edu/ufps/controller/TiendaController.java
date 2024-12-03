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

import co.edu.ufps.services.ClienteService;
import co.edu.ufps.services.FacturaService;
import co.edu.ufps.services.TiendaService;
import co.edu.ufps.DTOs.FacturaRequestDTO;
import co.edu.ufps.DTOs.FacturaResponseDTO;
import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.Tienda;

@RestController
@RequestMapping("/api/tiendas")
public class TiendaController {
	
	@Autowired
    private TiendaService tiendaService;

	@GetMapping
    public List<Tienda> listarTiendas() {
        return tiendaService.obtenerTodasLasTiendas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tienda> obtenerTiendaPorId(@PathVariable Integer id) {
        Tienda tienda = tiendaService.obtenerTiendaPorId(id);
        return ResponseEntity.ok(tienda);
    }

    @PostMapping
    public ResponseEntity<Tienda> crearTienda(@RequestBody Tienda tienda) {
        Tienda nuevaTienda = tiendaService.guardarTienda(tienda);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTienda);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tienda> actualizarTienda(@PathVariable Integer id, @RequestBody Tienda tiendaActualizada) {
        Tienda tienda = tiendaService.actualizarTienda(id, tiendaActualizada);
        return ResponseEntity.ok(tienda);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTienda(@PathVariable Integer id) {
        tiendaService.eliminarTienda(id);
        return ResponseEntity.noContent().build();
    }
    
 // Endpoint para buscar tienda por UUID
    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<Tienda> buscarPorUuid(@PathVariable String uuid) {
        Tienda tienda = tiendaService.buscarTiendaPorUuid(uuid);
        return ResponseEntity.ok(tienda);
    }

    // Endpoint para buscar o crear tienda por UUID
    @PostMapping("/uuid/{uuid}")
    public ResponseEntity<Tienda> buscarOCrearPorUuid(@PathVariable String uuid, @RequestBody Tienda tiendaNueva) {
        Tienda tienda = tiendaService.buscarOCrearPorUuid(uuid, tiendaNueva);
        return ResponseEntity.status(HttpStatus.CREATED).body(tienda);
    }
}