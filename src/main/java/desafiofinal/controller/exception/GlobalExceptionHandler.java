package desafiofinal.controller.exception;

import desafiofinal.service.exception.RecursoNaoEncontrado;
import desafiofinal.service.exception.RegraDeNegocioExcecao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(RegraDeNegocioExcecao.class)
    public ResponseEntity<String> handleBusinessException(RegraDeNegocioExcecao ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(RecursoNaoEncontrado.class)
    public ResponseEntity<String> handleNoContentException() {
        return new ResponseEntity<>("O recurso informado não foi encontrado!", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleUnexpectedException(Throwable unexpectedException) {
        String message = "Ops...Aconteceu um erro inesperado no servidor.";
        LOGGER.error(message, unexpectedException);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
