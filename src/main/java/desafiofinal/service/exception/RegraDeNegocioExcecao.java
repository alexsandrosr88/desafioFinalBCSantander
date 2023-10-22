package desafiofinal.service.exception;

import java.io.Serial;


public class RegraDeNegocioExcecao extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
        public RegraDeNegocioExcecao(String mensagem){
        super(mensagem);
    }
}
