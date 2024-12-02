package co.edu.ufps.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "total", nullable = false, length = 100)
    private Integer total;
    
    @Column(name = "impuestos", nullable = false, length = 100)
    private Integer impuestos;
    
    @Column(name = "fecha")
    private Date fecha;
    
    @Column(name = "observaciones", length = 100)
    private String observaciones;
    
    @ManyToOne 
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
    
    @ManyToOne 
	@JoinColumn(name="vendedor_id")
	private Vendedor vendedor;
    
    @ManyToOne 
	@JoinColumn(name="tienda_id")
	private Tienda tienda;

    @ManyToOne
    @JoinColumn(name = "cajero_id", nullable = false)
    private Cajero cajero;
    
    @OneToMany(mappedBy = "compra", cascade= CascadeType.ALL)
	@JsonIgnore
	private List<DetallesCompra> detallesCompras;
    
    @OneToMany(mappedBy = "compra", cascade= CascadeType.ALL)
	@JsonIgnore
	private List<Pago> pagos;
}