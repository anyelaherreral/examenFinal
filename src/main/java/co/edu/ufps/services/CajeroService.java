package co.edu.ufps.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.DTOs.FacturaRequest;
import co.edu.ufps.entities.Cajero;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.Tienda;
import co.edu.ufps.entities.Vendedor;
import co.edu.ufps.repositories.CajeroRepository;
import co.edu.ufps.repositories.ClienteRepository;
import co.edu.ufps.repositories.CompraRepository;
import co.edu.ufps.repositories.DetallesCompraRepository;
import co.edu.ufps.repositories.PagoRepository;
import co.edu.ufps.repositories.ProductoRepository;
import co.edu.ufps.repositories.TiendaRepository;
import co.edu.ufps.repositories.VendedorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CajeroService {

	@Autowired
    private CajeroRepository cajeroRepository;

    // Crear o actualizar un cajero
    public Cajero guardarCajero(Cajero cajero) {
        return cajeroRepository.save(cajero);
    }

    // Obtener todos los cajeros
    public List<Cajero> listarCajeros() {
        return cajeroRepository.findAll();
    }

    // Obtener un cajero por su ID
    public Cajero obtenerCajeroPorId(Integer id) {
        return cajeroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cajero no encontrado con el ID: " + id));
    }

    // Eliminar un cajero por su ID
    public void eliminarCajero(Integer id) {
        if (!cajeroRepository.existsById(id)) {
            throw new EntityNotFoundException("Cajero no encontrado con el ID: " + id);
        }
        cajeroRepository.deleteById(id);
    }

    // Buscar un cajero por su documento
    public Cajero obtenerCajeroPorDocumento(String documento) {
        return cajeroRepository.findByDocumento(documento)
                .orElseThrow(() -> new EntityNotFoundException("Cajero no encontrado con el documento: " + documento));
    }
    
    public Cajero obtenerCajeroPorToken(String token) {
        return cajeroRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Cajero no encontrado con el token: " + token));
    }
}