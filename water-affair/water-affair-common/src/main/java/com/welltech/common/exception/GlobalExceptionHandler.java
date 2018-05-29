/**
 * 
 */
package com.welltech.common.exception;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Zhujia at 2017年7月24日 下午4:16:44
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(SQLException.class)  
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  
    @ResponseBody  
    public ExceptionResponse handleSQLException(HttpServletRequest request, Exception ex) {  
        String message = ex.getMessage();  
        return ExceptionResponse.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);  
    }  
	
	@ExceptionHandler(UsernameNotFoundException.class)  
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
	public ExceptionResponse handleUsernameException(HttpServletRequest request, Exception ex){
		String message = ex.getMessage();  
        return ExceptionResponse.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);  
	}
       
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="IOException occured")  
    @ExceptionHandler(IOException.class)  
    @ResponseBody  
    public void handleIOException(){  
        //returning 404 error code  
    }  
      
    @ResponseStatus(HttpStatus.BAD_REQUEST)  
    @ResponseBody  
    @ExceptionHandler(SignException.class)  
    public ExceptionResponse signException(SignException ex) {  
        return ex.getEr();  
    }  
    
}
