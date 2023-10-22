package desafiofinal.dto;

import lombok.Data;

@Data
public class EnderecoRequestDTO {
    private String cep;
    private String numero;
    private String complemento;
}
