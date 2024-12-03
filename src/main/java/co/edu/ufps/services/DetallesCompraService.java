package co.edu.ufps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.DTOs.FacturaRequest;
import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.DetallesCompra;
import co.edu.ufps.entities.Producto;
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
public class DetallesCompraService {
	@Autowired
    private DetallesCompraRepository detallesCompraRepository;

    // Guardar un detalle de compra
    public DetallesCompra crearDetalleCompra(DetallesCompra detalleCompra) {
        return detallesCompraRepository.save(detalleCompra);
    }

    // Obtener todos los detalles de compra
    public List<DetallesCompra> obtenerTodosLosDetalles() {
        return detallesCompraRepository.findAll();
    }

    // Obtener detalle por ID
    public DetallesCompra obtenerDetallePorId(Integer id) {
        return detallesCompraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Detalle de compra no encontrado con ID: " + id));
    }

    // Eliminar detalle de compra
    public void eliminarDetalleCompra(Integer id) {
        DetallesCompra detalle = obtenerDetallePorId(id);
        detallesCompraRepository.delete(detalle);
    }
}