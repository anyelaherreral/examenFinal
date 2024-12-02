package co.edu.ufps.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.DTOs.FacturaRequest;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.repositories.ClienteRepository;
import co.edu.ufps.repositories.TiendaRepository;
import jakarta.transaction.Transactional;

@Service
public class CompraService {
	
	@Autowired
	TiendaRepository tiendaRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	
//	@Transactional
//    public Compra procesarCompra(String tiendaUuid, FacturaRequest request) {
//        // Validar la tienda
//        Tienda tienda = TiendaService.buscarPorUuid(tiendaUuid);
//
//        // Buscar o registrar cliente
//        Cliente cliente = clienteService.obtenerORegistrarCliente(request.getCliente());
//
//        // Validar vendedor y cajero
//        Vendedor vendedor = vendedorService.buscarPorDocumento(request.getVendedor().getDocumento());
//        Cajero cajero = cajeroService.buscarPorToken(request.getCajero().getToken());
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
//            Producto producto = productoService.buscarPorReferencia(productoRequest.getReferencia());
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
