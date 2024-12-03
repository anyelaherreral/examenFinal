package co.edu.ufps.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.DTOs.FacturaRequest;
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
public class VendedorService {

	@Autowired
    private VendedorRepository vendedorRepository;

    public List<Vendedor> obtenerTodosLosVendedores() {
        return vendedorRepository.findAll();
    }

    public Vendedor obtenerVendedorPorId(Integer id) {
        return vendedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendedor no encontrado con ID: " + id));
    }

    public Vendedor crearVendedor(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    public Vendedor actualizarVendedor(Integer id, Vendedor vendedorDetalles) {
        Vendedor vendedorExistente = obtenerVendedorPorId(id);
        vendedorExistente.setNombre(vendedorDetalles.getNombre());
        vendedorExistente.setDocumento(vendedorDetalles.getDocumento());
        vendedorExistente.setEmail(vendedorDetalles.getEmail());
        return vendedorRepository.save(vendedorExistente);
    }

    public void eliminarVendedor(Integer id) {
        Vendedor vendedor = obtenerVendedorPorId(id);
        vendedorRepository.delete(vendedor);
    }
    
    public Vendedor obtenerVendedorPorDocumento(String documento) {
        return vendedorRepository.findByDocumento(documento)
                .orElseThrow(() -> new EntityNotFoundException("Vendedor no encontrado con el documento: " + documento));
    }
}