package desafiofinal.dto;

import desafiofinal.model.Endereco;
import lombok.Data;

@Data
public class EnderecoResponseDTO {
    private Long id;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

    public EnderecoResponseDTO(Endereco endereco){
        id = endereco.getId();
        cep = endereco.getCep();
        logradouro = endereco.getLogradouro();
        numero = endereco.getNumero();
        complemento = endereco.getComplemento();
        bairro = endereco.getBairro();
        cidade = endereco.getCidade();
        estado = endereco.getEstado();
    }

}
