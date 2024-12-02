package co.edu.ufps.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.ufps.DTOs.FacturaRequestDTO;
import co.edu.ufps.DTOs.FacturaResponseDTO;

import org.springframework.stereotype.Service;

@Service
public class FacturaService {

    private final Map<Integer, FacturaResponseDTO> facturas = new HashMap<>();

    public FacturaService() {
        // Inicializar datos de ejemplo
        inicializarFacturas();
    }

    private void inicializarFacturas() {
        // Crear cliente
        FacturaResponseDTO.ClienteDTO cliente = new FacturaResponseDTO.ClienteDTO();
        cliente.setDocumento("10000001");
        cliente.setNombre("Juan Pérez");
        cliente.setTipo_documento("CC");

        // Crear productos
        List<FacturaResponseDTO.ProductoDTO> productos = new ArrayList<>();
        productos.add(crearProducto("ELEC001", "Smartphone", 2, 1200, 240, 2160));
        productos.add(crearProducto("ELEC002", "Laptop", 1, 2500, 0, 2500));
        productos.add(crearProducto("ELEC003", "Smartwatch", 10, 300, 300, 2700));

        // Crear cajero
        FacturaResponseDTO.CajeroDTO cajero = new FacturaResponseDTO.CajeroDTO();
        cajero.setDocumento("60606060");
        cajero.setNombre("Luis Herrera");

        // Crear factura
        FacturaResponseDTO factura = new FacturaResponseDTO();
        factura.setTotal(7728);
        factura.setImpuestos(368);
        factura.setCliente(cliente);
        factura.setProductos(productos);
        factura.setCajero(cajero);

        // Agregar al mapa
        facturas.put(1, factura);
    }

    private FacturaResponseDTO.ProductoDTO crearProducto(String referencia, String nombre, int cantidad, double precio, double descuento, double subtotal) {
        FacturaResponseDTO.ProductoDTO producto = new FacturaResponseDTO.ProductoDTO();
        producto.setReferencia(referencia);
        producto.setNombre(nombre);
        producto.setCantidad(cantidad);
        producto.setPrecio(precio);
        producto.setDescuento(descuento);
        producto.setSubtotal(subtotal);
        return producto;
    }

    public FacturaResponseDTO consultarFactura(FacturaRequestDTO request) {
        // Validar token (puedes implementar lógica real para validarlo)
        if (!"token123".equals(request.getToken())) {
            throw new IllegalArgumentException("Token inválido");
        }

        // Buscar factura por número
        FacturaResponseDTO factura = facturas.get(request.getFactura());
        if (factura == null) {
            throw new IllegalArgumentException("Factura no encontrada");
        }

        // Verificar cliente
        if (!factura.getCliente().getDocumento().equals(request.getCliente())) {
            throw new IllegalArgumentException("Cliente no coincide con la factura");
        }

        return factura;
    }
}