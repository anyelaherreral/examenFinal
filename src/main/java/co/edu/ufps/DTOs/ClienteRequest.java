package co.edu.ufps.DTOs;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import co.edu.ufps.entities.TipoDocumento;
import lombok.Data;

@Data
public class ClienteRequest {
	private String documento;
    private String nombre;
    @JsonProperty("tipo_documento")
    private String  tipoDocumento;
}
