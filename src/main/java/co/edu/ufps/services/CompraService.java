package co.edu.ufps.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Compra crearCompra(FacturaRequest facturaRequest, Tienda tienda) {
        try {
        	
            // Crear y guardar la compra
            Compra compra = new Compra();
            compra.setImpuestos(facturaRequest.getImpuesto());
          
            compra.setFecha(LocalDate.now());
            compra = compraRepository.save(compra);
            
            compra.setTienda(tienda);
            
         // Manejo del cliente
            Cliente cliente;
            try {
                cliente = clienteService.getClienteByDocumento(facturaRequest.getCliente().getDocumento());
            } catch (RuntimeException e) {
                // Si el cliente no existe, lo creamos
                Cliente nuevoCliente = new Cliente();
                nuevoCliente.setDocumento(facturaRequest.getCliente().getDocumento());
                nuevoCliente.setNombre(facturaRequest.getCliente().getNombre());
                
                String tipoDocumentoString = facturaRequest.getCliente().getTipoDocumento();
                TipoDocumento tipoDocumento = tipoDocumentoRepository.findByNombre(tipoDocumentoString)
                        .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado: " + tipoDocumentoString));
                
                nuevoCliente.setTipoDocumento(tipoDocumento);                
                cliente = clienteService.createCliente(nuevoCliente);
            }
         // Manejar los productos
            for (ProductoRequest productoRequest : facturaRequest.getProductos()) {
                Producto producto = productoService.obtenerProductoPorReferencia(productoRequest.getReferencia());
                if (producto == null) {
                    throw new RuntimeException("Producto no encontrado con referencia: " + productoRequest.getReferencia());
                }

                // Verificar disponibilidad de inventario
                if (producto.getCantidad() < productoRequest.getCantidad()) {
                    throw new RuntimeException("Cantidad insuficiente para el producto con referencia: " + productoRequest.getReferencia());
                }

                // Actualizar inventario
                producto.setCantidad(producto.getCantidad() - productoRequest.getCantidad());
                productoService.actualizarProducto(producto.getId(), producto);

             // Crear y guardar el detalle de compra
                DetallesCompra detalle = new DetallesCompra();
                detalle.setCompra(compra);
                detalle.setProducto(producto);
                detalle.setCantidad(productoRequest.getCantidad());
                detalle.setPrecio(producto.getPrecio());
                detalle.setDescuento(productoRequest.getDescuento());
                detallesCompraService.crearDetalleCompra(detalle);
            }
         // Manejar los medios de pago
            for (PagoRequest pagoRequest : facturaRequest.getMediosPago()) {
                TipoPago tipoPago = tipoPagoService.obtenerPorNombre(pagoRequest.getTipoPago());

                Pago pago = new Pago();
                pago.setTipoPago(tipoPago);
                pago.setCompra(compra);
                pago.setTarjetaTipo(pagoRequest.getTipoTarjeta());;
                pago.setCuotas(pagoRequest.getCuotas());
                pago.setValor(pagoRequest.getValor());
                pagoService.crearPago(pago);
            }            
         // Asociar al vendedor (se busca por documento)
            Vendedor vendedor = vendedorService.obtenerVendedorPorDocumento(facturaRequest.getVendedor().getDocumento());
            if (vendedor == null) {
                throw new RuntimeException("Vendedor no encontrado con el documento: " + facturaRequest.getVendedor().getDocumento());
            }
            compra.setVendedor(vendedor);

            // Asociar al cajero (se busca por token)
            Cajero cajero = cajeroService.obtenerCajeroPorToken(facturaRequest.getCajero().getToken());
            if (cajero == null) {
                throw new RuntimeException("Cajero no encontrado con el token: " + facturaRequest.getCajero().getToken());
            }
            compra.setCajero(cajero);
            
         // Calcular el total de los medios de pago
            Integer total = 0;
            for (PagoRequest pago : facturaRequest.getMediosPago()) {
                if (pago.getValor() != null) {
                    total += pago.getValor().intValue();  // Convertimos el valor a int si es necesario
                }
            }
            compra.setTotal(total);
            
            
            // Preparar la respuesta
            compra.setTotal(total);
            compra.setFecha(LocalDate.now());
            compra.setCliente(cliente);
            compra.setVendedor(vendedor);
            compra.setCajero(cajero);

            // Guardar la compra en la base de datos y devolver el objeto Compra
            return guardarCompra(compra);
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar la compra: " + e.getMessage());
        }
    }

    public Compra guardarCompra(Compra compra) {
        return compraRepository.save(compra);
    }
}