package co.edu.ufps.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.DTOs.RespuestaApi;
import co.edu.ufps.DTOs.DataFactura;
import co.edu.ufps.DTOs.FacturaRequest;
import co.edu.ufps.DTOs.ProductoRequest;
import co.edu.ufps.DTOs.PagoRequest;
import co.edu.ufps.entities.Cajero;
import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.DetallesCompra;
import co.edu.ufps.entities.Pago;
import co.edu.ufps.entities.Producto;
import co.edu.ufps.entities.Tienda;
import co.edu.ufps.entities.TipoDocumento;
import co.edu.ufps.entities.TipoPago;
import co.edu.ufps.entities.Vendedor;
import co.edu.ufps.repositories.CajeroRepository;
import co.edu.ufps.repositories.ClienteRepository;
import co.edu.ufps.repositories.CompraRepository;
import co.edu.ufps.repositories.DetallesCompraRepository;
import co.edu.ufps.repositories.PagoRepository;
import co.edu.ufps.repositories.ProductoRepository;
import co.edu.ufps.repositories.TiendaRepository;
import co.edu.ufps.repositories.TipoDocumentoRepository;
import co.edu.ufps.repositories.VendedorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CompraService {
	
	@Autowired
	TiendaRepository tiendaRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	VendedorRepository vendedorRepository;
	
	@Autowired
	CajeroRepository cajeroRepository;
	
	@Autowired
    CompraRepository compraRepository;
	
	@Autowired
    TipoDocumentoRepository tipoDocumentoRepository;
	
	@Autowired
	ClienteService clienteService;
	@Autowired
	ProductoService productoService;
	@Autowired
	DetallesCompraService detallesCompraService;
	@Autowired
	TipoPagoService tipoPagoService;
	@Autowired
	PagoService pagoService;
	@Autowired
	VendedorService vendedorService;
	@Autowired
	CajeroService cajeroService;

	
	@Transactional
    public RespuestaApi crearCompra(FacturaRequest facturaRequest, Tienda tienda) {
	    try {
	    	// Validar cliente
            if (facturaRequest.getCliente() == null) {
                return new RespuestaApi("error", "No hay información del cliente", null);
            }

            // Validar vendedor
            if (facturaRequest.getVendedor() == null) {
                return new RespuestaApi("error", "No hay información del vendedor", null);
            }

            // Validar cajero
            if (facturaRequest.getCajero() == null) {
                return new RespuestaApi("error", "No hay información del cajero", null);
            }

            // Validar productos
            if (facturaRequest.getProductos() == null || facturaRequest.getProductos().isEmpty()) {
                return new RespuestaApi("error", "No hay productos asignados para esta compra", null);
            }

            // Validar medios de pago
            if (facturaRequest.getMediosPago() == null || facturaRequest.getMediosPago().isEmpty()) {
                return new RespuestaApi("error", "No hay medios de pagos asignados para esta compra", null);
            }
	    	
	        // Crear la compra
	        Compra compra = new Compra();
	        compra.setImpuestos(facturaRequest.getImpuesto());
	        compra.setFecha(LocalDate.now());
	        compra.setTienda(tienda);

	        // Manejo del cliente
	        Cliente cliente = clienteService.buscarOCrearCliente(
	                facturaRequest.getCliente().getDocumento(),
	                facturaRequest.getCliente().getTipoDocumento(),
	                facturaRequest.getCliente()
	        );
	        compra.setCliente(cliente);

	        // Asociar al vendedor
	        Vendedor vendedor = vendedorService.obtenerVendedorPorDocumento(facturaRequest.getVendedor().getDocumento());
	        if (vendedor == null) {
                return new RespuestaApi("error", "El vendedor no existe en la tienda", null);
	        }
	        compra.setVendedor(vendedor);

	        // Manejo del cajero
	        Cajero cajero = cajeroService.obtenerCajeroPorToken(facturaRequest.getCajero().getToken());
	        if (cajero == null) {
                return new RespuestaApi("error", "El token no corresponde a ningún cajero en la tienda", null);
	        }
	        if (!cajero.getTienda().equals(tienda)) {
                return new RespuestaApi("error", "El cajero no está asignado a esta tienda", null);
            }
	        compra.setCajero(cajero);

	        // Calcular el total
	        Integer total = facturaRequest.getMediosPago().stream()
	                .mapToInt(PagoRequest::getValor)
	                .sum();
	        compra.setTotal(total);

	        // Guardar la compra inicialmente
	        compra = compraRepository.save(compra);

	        // Manejar los productos
	        for (ProductoRequest productoRequest : facturaRequest.getProductos()) {
	            Producto producto = productoService.obtenerProductoPorReferencia(productoRequest.getReferencia());
	            if (producto == null) {
                    return new RespuestaApi("error", "La referencia del producto " + productoRequest.getReferencia() + " no existe, por favor revisar los datos", null);
	            }

	            // Verificar disponibilidad de inventario
	            if (producto.getCantidad() < productoRequest.getCantidad()) {
                    return new RespuestaApi("error", "La cantidad a comprar supera el máximo del producto en tienda", null);
	            }

	            // Actualizar inventario
	            producto.setCantidad(producto.getCantidad() - productoRequest.getCantidad());
	            productoService.actualizarProducto(producto.getId(), producto);

	            // Crear y guardar el detalle de compra
	            DetallesCompra detalle = new DetallesCompra();
	            detalle.setCompra(compra); // Ahora `compra` ya tiene un ID válido
	            detalle.setProducto(producto);
	            detalle.setCantidad(productoRequest.getCantidad());
	            detalle.setPrecio(producto.getPrecio());
	            detalle.setDescuento(productoRequest.getDescuento());
	            detallesCompraService.crearDetalleCompra(detalle);
	        }

	        // Manejar los medios de pago
	        for (PagoRequest pagoRequest : facturaRequest.getMediosPago()) {
	            TipoPago tipoPago = tipoPagoService.obtenerPorNombre(pagoRequest.getTipoPago());
	            if (tipoPago == null) {
                    return new RespuestaApi("error", "Tipo de pago no permitido en la tienda", null);
                }
	            Pago pago = new Pago();
	            pago.setTipoPago(tipoPago);
	            pago.setCompra(compra); // Ahora `compra` ya tiene un ID válido
	            pago.setTarjetaTipo(pagoRequest.getTipoTarjeta());
	            pago.setCuotas(pagoRequest.getCuotas());
	            pago.setValor(pagoRequest.getValor());
	            pagoService.crearPago(pago);
	        }

	     // Construir la respuesta
	        DataFactura data = new DataFactura(compra.getId(), compra.getTotal(), compra.getFecha());
	        return new RespuestaApi("success", "La factura se ha creado correctamente con el número: #" + compra.getId(), data);

        } catch (Exception e) {
            return new RespuestaApi("error", e.getMessage(), null);
        }
    }
}