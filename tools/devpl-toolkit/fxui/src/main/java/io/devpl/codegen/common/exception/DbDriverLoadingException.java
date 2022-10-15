package io.devpl.codegen.common.exception;

/**
 * 数据库驱动加载异常
 */
public class DbDriverLoadingException extends RuntimeException{

    private static final long serialVersionUID = 1L;

	public DbDriverLoadingException(String message){
        super(message);
    }
}
