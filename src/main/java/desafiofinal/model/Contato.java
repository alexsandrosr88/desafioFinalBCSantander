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

    public Contato(ContatoRequestDTO contatoPOSTDTO){
        nome = contatoPOSTDTO.getNome();
        cpf = contatoPOSTDTO.getCpf();
        telefone = contatoPOSTDTO.getTelefone();
        email = contatoPOSTDTO.getEmail();
        endereco = new Endereco(contatoPOSTDTO.getEndereco());
    }
}
