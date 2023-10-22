package desafiofinal.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ContatoPostDTO {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private EnderecoPostDTO endereco;

}
