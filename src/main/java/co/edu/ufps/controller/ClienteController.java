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
    public ResponseEntity<Cliente> buscarOCrearCliente(@RequestBody Cliente clienteDetalles) {
        // Buscar cliente por documento
        Cliente clienteExistente = clienteService.getClienteByDocumento(clienteDetalles.getDocumento());

        if (clienteExistente != null) {
            // Si el cliente existe, retornamos el cliente
            return ResponseEntity.ok(clienteExistente);
        } else {
            // Si el cliente no existe, creamos un nuevo cliente
            Cliente nuevoCliente = clienteService.createCliente(clienteDetalles);
            return ResponseEntity.ok(nuevoCliente);
        }
    }
    
 // Buscar cliente por documento
    @GetMapping("/buscar-por-documento/{documento}")
    public ResponseEntity<Cliente> buscarClientePorDocumento(@PathVariable String documento) {
        Cliente cliente = clienteService.getClienteByDocumento(documento);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();  // Retorna 404 si el cliente no es encontrado
        }
    }
}