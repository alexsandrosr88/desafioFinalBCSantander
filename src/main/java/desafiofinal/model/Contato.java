package desafiofinal.model;

import desafiofinal.dto.ContatoRequestDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50)
    private String nome;
    @Column(length = 14, unique = true)
    private String cpf;
    @Column(length = 17, unique = true)
    private String telefone;
    @Column(length = 50, unique = true)
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    public Contato(ContatoRequestDTO contatoRequestDTO){
        nome = contatoRequestDTO.getNome();
        cpf = contatoRequestDTO.getCpf();
        telefone = contatoRequestDTO.getTelefone();
        email = contatoRequestDTO.getEmail();
        endereco = new Endereco(contatoRequestDTO.getEndereco());
    }

    public Contato (Contato contato){
        nome = contato.getNome();
        cpf = contato.getCpf();
        telefone = contato.getTelefone();
        email = contato.getEmail();
        endereco.setCep(contato.getEndereco().getCep());
        endereco.setNumero(contato.getEndereco().getNumero());
        endereco.setComplemento(contato.getEndereco().getComplemento());
    }

}
