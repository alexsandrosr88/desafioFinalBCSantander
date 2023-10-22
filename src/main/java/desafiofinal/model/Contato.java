package desafiofinal.model;

import desafiofinal.dto.ContatoPostDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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

    public Contato(ContatoPostDTO contatoPostDTO){
        nome = contatoPostDTO.getNome();
        cpf = contatoPostDTO.getCpf();
        telefone = contatoPostDTO.getTelefone();
        email = contatoPostDTO.getEmail();
        endereco = new Endereco(contatoPostDTO.getEndereco());
    }

}
