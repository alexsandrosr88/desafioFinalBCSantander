package desafiofinal.dto;

import desafiofinal.model.Contato;
import lombok.Data;

@Data
public class ContatoResponseDTO {
    private Integer id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private EnderecoResponseDTO endereco;

    public ContatoResponseDTO(Contato contato){
        id = contato.getId();
        nome = contato.getNome();
        cpf = contato.getCpf();
        telefone = contato.getTelefone();
        email = contato.getEmail();
        endereco = new EnderecoResponseDTO(contato.getEndereco());
    }
}
