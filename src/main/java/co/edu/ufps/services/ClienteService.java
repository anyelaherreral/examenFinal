package co.edu.ufps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.DTOs.ClienteRequest;
import co.edu.ufps.DTOs.FacturaRequest;
import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.TipoDocumento;
import co.edu.ufps.repositories.CajeroRepository;
import co.edu.ufps.repositories.ClienteRepository;
import co.edu.ufps.repositories.CompraRepository;
import co.edu.ufps.repositories.DetallesCompraRepository;
import co.edu.ufps.repositories.PagoRepository;
import co.edu.ufps.repositories.ProductoRepository;
import co.edu.ufps.repositories.TiendaRepository;
import co.edu.ufps.repositories.VendedorRepository;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {
	@Autowired
    private ClienteRepository clienteRepository;

    // Listar todos los clientes
    public List<Cliente> list() {
        return clienteRepository.findAll();
    }

    // Obtener cliente por ID
    public Cliente getClienteById(Integer id) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isPresent()) {
            return clienteOpt.get();
        }
        throw new RuntimeException("Cliente no encontrado con el id: " + id);
    }
    
    public Cliente getClienteByDocumentoAndTipoDocumento(String documento, String tipoDocumento) {
        return clienteRepository.findByDocumentoAndTipoDocumento_Nombre(documento, tipoDocumento)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con documento: " + documento + " y tipo de documento: " + tipoDocumento));
    }

    // Crear un nuevo cliente
    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Actualizar un cliente existente
    public Cliente updateCliente(Integer id, Cliente clienteDetalles) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombre(clienteDetalles.getNombre());
                    cliente.setDocumento(clienteDetalles.getDocumento());
                    cliente.setTipoDocumento(clienteDetalles.getTipoDocumento());
                    return clienteRepository.save(cliente);
                }).orElseThrow(() -> new RuntimeException("Cliente no encontrado con el id: " + id));
    }

    // Eliminar un cliente
    public String deleteCliente(Integer id) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isPresent()) {
            clienteRepository.delete(clienteOpt.get());
            return "Cliente eliminado correctamente.";
        }
        throw new RuntimeException("Cliente no encontrado con el id: " + id);
    }
    
    public Cliente buscarOCrearCliente(String documento, String tipoDocumento, ClienteRequest clienteRequest) {
        return clienteRepository.findByDocumentoAndTipoDocumento_Nombre(documento, tipoDocumento)
                .orElseGet(() -> {
                    Cliente nuevoCliente = new Cliente();
                    nuevoCliente.setDocumento(documento);
                    nuevoCliente.setNombre(clienteRequest.getNombre());

                    TipoDocumento tipoDoc = new TipoDocumento();
                    tipoDoc.setNombre(tipoDocumento);
                    nuevoCliente.setTipoDocumento(tipoDoc);

                    return clienteRepository.save(nuevoCliente);
                });
    }
    
    public Cliente buscarClientePorDocumentoYTipo(String documento, String tipoDocumento) {
        return clienteRepository.findByDocumentoAndTipoDocumento_Nombre(documento, tipoDocumento)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con documento: " 
                                                        + documento + " y tipo: " + tipoDocumento));
    }

}