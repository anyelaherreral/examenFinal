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
import co.edu.ufps.services.VendedorService;
import co.edu.ufps.DTOs.FacturaRequestDTO;
import co.edu.ufps.DTOs.FacturaResponseDTO;
import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.Tienda;
import co.edu.ufps.entities.Vendedor;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {
	
	@Autowired
    private VendedorService vendedorService;

    @GetMapping
    public ResponseEntity<List<Vendedor>> obtenerTodosLosVendedores() {
        List<Vendedor> vendedores = vendedorService.obtenerTodosLosVendedores();
        return ResponseEntity.ok(vendedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> obtenerVendedorPorId(@PathVariable Integer id) {
        Vendedor vendedor = vendedorService.obtenerVendedorPorId(id);
        return ResponseEntity.ok(vendedor);
    }

    @PostMapping
    public ResponseEntity<Vendedor> crearVendedor(@RequestBody Vendedor vendedor) {
        Vendedor nuevoVendedor = vendedorService.crearVendedor(vendedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVendedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> actualizarVendedor(@PathVariable Integer id, @RequestBody Vendedor vendedorDetalles) {
        Vendedor vendedorActualizado = vendedorService.actualizarVendedor(id, vendedorDetalles);
        return ResponseEntity.ok(vendedorActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVendedor(@PathVariable Integer id) {
        vendedorService.eliminarVendedor(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/buscar-por-documento/{documento}")
    public ResponseEntity<Vendedor> obtenerVendedorPorDocumento(@PathVariable String documento) {
        Vendedor vendedor = vendedorService.obtenerVendedorPorDocumento(documento);
        return ResponseEntity.ok(vendedor);
    }
}