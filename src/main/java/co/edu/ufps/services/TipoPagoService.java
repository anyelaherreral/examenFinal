package co.edu.ufps.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.DTOs.FacturaRequest;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.Tienda;
import co.edu.ufps.entities.TipoPago;
import co.edu.ufps.entities.Vendedor;
import co.edu.ufps.repositories.CajeroRepository;
import co.edu.ufps.repositories.ClienteRepository;
import co.edu.ufps.repositories.CompraRepository;
import co.edu.ufps.repositories.DetallesCompraRepository;
import co.edu.ufps.repositories.PagoRepository;
import co.edu.ufps.repositories.ProductoRepository;
import co.edu.ufps.repositories.TiendaRepository;
import co.edu.ufps.repositories.TipoPagoRepository;
import co.edu.ufps.repositories.VendedorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class TipoPagoService {

	@Autowired
    private TipoPagoRepository tipoPagoRepository;

    // Obtener un tipo de pago por nombre
    public TipoPago obtenerPorNombre(String nombre) {
        return tipoPagoRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Tipo de pago no encontrado: " + nombre));
    }

    // Crear un nuevo tipo de pago
    public TipoPago crearTipoPago(TipoPago tipoPago) {
        return tipoPagoRepository.save(tipoPago);
    }
}