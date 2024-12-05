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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.ufps.services.ClienteService;
import co.edu.ufps.services.FacturaService;
import co.edu.ufps.DTOs.ClienteRequest;
import co.edu.ufps.DTOs.FacturaRequestDTO;
import co.edu.ufps.DTOs.FacturaResponseDTO;
import co.edu.ufps.entities.Cliente;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	@Autowired
    private ClienteService clienteService;

    // Listar todos los clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.list();
        return ResponseEntity.ok(clientes);
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Integer id) {
        Cliente cliente = clienteService.getClienteById(id);
        return ResponseEntity.ok(cliente);
    }

    // Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.createCliente(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }

    // Actualizar un cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Integer id, @RequestBody Cliente clienteDetalles) {
        Cliente clienteActualizado = clienteService.updateCliente(id, clienteDetalles);
        return ResponseEntity.ok(clienteActualizado);
    }

    // Eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Integer id) {
        String mensaje = clienteService.deleteCliente(id);
        return ResponseEntity.ok(mensaje);
    }

 // Crear o buscar cliente por documento
    @PostMapping("/buscar-o-crear")
    public ResponseEntity<Cliente> buscarOCrearCliente(
            @RequestParam("documento") String documento,
            @RequestParam("tipoDocumento") String tipoDocumento,
            @RequestBody ClienteRequest clienteRequest) {
        try {
            // Buscar o crear el cliente utilizando el servicio
            Cliente cliente = clienteService.buscarOCrearCliente(documento, tipoDocumento, clienteRequest);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
 // Buscar cliente por documento
    @GetMapping("/buscar-por-documento/{documento}")
    public ResponseEntity<Cliente> buscarClientePorDocumento(
            @RequestParam("documento") String documento,
            @RequestParam("tipoDocumento") String tipoDocumento) {
        try {
            // Buscar el cliente por documento y tipo de documento
            Cliente cliente = clienteService.buscarClientePorDocumentoYTipo(documento, tipoDocumento);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 si el cliente no es encontrado
        }
    }
}