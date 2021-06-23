package br.com.alura.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroDeValidacaoControler {

	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDTO> handle(MethodArgumentNotValidException ex) {
		
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		List<ErroDeFormularioDTO> errosDto = new ArrayList<ErroDeFormularioDTO>();
		
		fieldErrors.forEach(erro -> {
			String message = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
			ErroDeFormularioDTO erroDto = new ErroDeFormularioDTO(erro.getField(), message);
			errosDto.add(erroDto);
		});
		
		return errosDto;
	}
}
