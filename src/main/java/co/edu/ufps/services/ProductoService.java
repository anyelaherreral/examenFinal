package co.edu.ufps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.DTOs.FacturaRequest;
import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.Compra;
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
public class ProductoService {
	 @Autowired
	    private ProductoRepository productoRepository;

	    public List<Producto> obtenerTodosLosProductos() {
	        return productoRepository.findAll();
	    }

	    public Producto obtenerProductoPorId(Integer id) {
	        return productoRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));
	    }

	    public Producto crearProducto(Producto producto) {
	        return productoRepository.save(producto);
	    }

	    public Producto actualizarProducto(Integer id, Producto productoDetalles) {
	        Producto productoExistente = obtenerProductoPorId(id);
	        productoExistente.setNombre(productoDetalles.getNombre());
	        productoExistente.setDescripcion(productoDetalles.getDescripcion());
	        productoExistente.setPrecio(productoDetalles.getPrecio());
	        productoExistente.setReferencia(productoDetalles.getReferencia());
	        productoExistente.setCantidad(productoDetalles.getCantidad());
	        productoExistente.setTipoProducto(productoDetalles.getTipoProducto());
	        return productoRepository.save(productoExistente);
	    }

	    public void eliminarProducto(Integer id) {
	        Producto producto = obtenerProductoPorId(id);
	        productoRepository.delete(producto);
	    }
	    
	    public Producto obtenerProductoPorReferencia(String referencia) {
	        return productoRepository.findByReferencia(referencia)
	                .orElseThrow(() -> new RuntimeException("Producto no encontrado con referencia: " + referencia));
	    }

	}