package co.edu.ufps.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.DTOs.FacturaRequest;
import co.edu.ufps.entities.Compra;
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
    private CompraRepository compraRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DetallesCompraRepository detallesCompraRepository;

    @Autowired
    private PagoRepository pagoRepository;
	
	
//    @Transactional
//    public Compra procesarCompra(String tiendaUuid, FacturaRequest request) {
//        // Validar la tienda
//        Tienda tienda = tiendaRepository.findByUuid(tiendaUuid)
//                .orElseThrow(() -> new RuntimeException("Tienda no encontrada con UUID: " + tiendaUuid));
//
//        // Buscar o registrar cliente
//        Cliente cliente = clienteRepository.findByDocumento(request.getCliente().getDocumento())
//                .orElseGet(() -> {
//                    Cliente nuevoCliente = new Cliente();
//                    nuevoCliente.setNombre(request.getCliente().getNombre());
//                    nuevoCliente.setDocumento(request.getCliente().getDocumento());
//                    nuevoCliente.setTipoDocumento(request.getCliente().getTipoDocumento());
//                    return clienteRepository.save(nuevoCliente);
//                });
//
//        // Validar vendedor
//        Vendedor vendedor = vendedorRepository.findByDocumento(request.getVendedor().getDocumento())
//                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado con documento: " + request.getVendedor().getDocumento()));
//
//        // Validar cajero
//        Cajero cajero = cajeroRepository.findByToken(request.getCajero().getToken())
//                .orElseThrow(() -> new RuntimeException("Cajero no encontrado con token: " + request.getCajero().getToken()));
//
//        // Crear compra
//        Compra compra = new Compra();
//        compra.setCliente(cliente);
//        compra.setTienda(tienda);
//        compra.setVendedor(vendedor);
//        compra.setCajero(cajero);
//        compra.setImpuestos(request.getImpuesto());
//        compra.setFecha(LocalDateTime.now());
//        compra = compraRepository.save(compra);
//
//        // Procesar productos
//        BigDecimal totalCompra = BigDecimal.ZERO;
//        for (ProductoRequest productoRequest : request.getProductos()) {
//            Producto producto = productoRepository.findByReferencia(productoRequest.getReferencia())
//                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con referencia: " + productoRequest.getReferencia()));
//            BigDecimal subtotal = producto.getPrecio()
//                    .multiply(BigDecimal.valueOf(productoRequest.getCantidad()))
//                    .subtract(productoRequest.getDescuento());
//
//            DetallesCompra detalle = new DetallesCompra();
//            detalle.setCompra(compra);
//            detalle.setProducto(producto);
//            detalle.setCantidad(productoRequest.getCantidad());
//            detalle.setPrecio(producto.getPrecio());
//            detalle.setDescuento(productoRequest.getDescuento());
//            detallesCompraRepository.save(detalle);
//
//            totalCompra = totalCompra.add(subtotal);
//        }
//
//        // Actualizar total de la compra
//        compra.setTotal(totalCompra.add(compra.getImpuestos()));
//        compraRepository.save(compra);
//
//        // Procesar pagos
//        for (PagoRequest pagoRequest : request.getMediosPago()) {
//            Pago pago = new Pago();
//            pago.setCompra(compra);
//            pago.setTipoPago(pagoRequest.getTipoPago());
//            pago.setTarjetaTipo(pagoRequest.getTipoTarjeta());
//            pago.setCuotas(pagoRequest.getCuotas());
//            pago.setValor(pagoRequest.getValor());
//            pagoRepository.save(pago);
//        }
//
//        return compra;
//    }
}