package co.edu.ufps.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.DTOs.FacturaRequest;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.Tienda;
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
public class TiendaService {

    @Autowired
    private TiendaRepository tiendaRepository;
    
    public List<Tienda> obtenerTodasLasTiendas() {
        return tiendaRepository.findAll();
    }

    public Tienda obtenerTiendaPorId(Integer id) {
        return tiendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tienda no encontrada con ID: " + id));
    }

    public Tienda guardarTienda(Tienda tienda) {
        return tiendaRepository.save(tienda);
    }

    public Tienda actualizarTienda(Integer id, Tienda tiendaActualizada) {
        Tienda tiendaExistente = obtenerTiendaPorId(id);
        tiendaExistente.setNombre(tiendaActualizada.getNombre());
        tiendaExistente.setDireccion(tiendaActualizada.getDireccion());
        tiendaExistente.setUuid(tiendaActualizada.getUuid());
        return tiendaRepository.save(tiendaExistente);
    }

    public void eliminarTienda(Integer id) {
        Tienda tienda = obtenerTiendaPorId(id);
        tiendaRepository.delete(tienda);
    }
    
 // Método para buscar tienda por UUID
    public Tienda buscarTiendaPorUuid(String uuid) {
        return tiendaRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Tienda no encontrada con UUID: " + uuid));
    }
    
 // Método para crear una tienda si no existe por UUID
    public Tienda buscarOCrearPorUuid(String uuid, Tienda tiendaNueva) {
        return tiendaRepository.findByUuid(uuid)
                .orElseGet(() -> tiendaRepository.save(tiendaNueva));
    }
}