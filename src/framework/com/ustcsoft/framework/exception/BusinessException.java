package com.ustcsoft.framework.exception;


public class BusinessException extends BaseException
{
	private static final long serialVersionUID = -7069524835832284283L;

    public BusinessException(String message)
    {
        super(message);
    }

    public BusinessException(Throwable cause)
    {
        super(cause);
    }

    public BusinessException(String message, Throwable cause)
    {
        super(message, cause);
    }
}