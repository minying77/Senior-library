package cn.edu.scnu.exception;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException{

    private String code;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }
}
