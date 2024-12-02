package co.edu.ufps.entities;

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
@Table(name = "tienda")
public class Tienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "direccion", nullable = false, length = 500)
    private String direccion;

    @Column(name = "uuid", length = 50)
    private String uuid;
    
    @OneToMany(mappedBy = "tienda", cascade= CascadeType.ALL)
	@JsonIgnore
	private List<Compra> compra;
    
    @OneToMany(mappedBy = "tienda", cascade= CascadeType.ALL)
	@JsonIgnore
	private List<Cajero> cajero;
}