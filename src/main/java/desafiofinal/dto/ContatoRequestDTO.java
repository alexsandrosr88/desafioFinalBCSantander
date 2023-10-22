package desafiofinal.dto;

import lombok.Data;

@Data
public class ContatoRequestDTO {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private EnderecoRequestDTO endereco;

}
