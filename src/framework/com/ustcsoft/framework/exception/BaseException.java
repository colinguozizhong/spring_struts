package com.ustcsoft.framework.exception;


public class BaseException extends Exception
{
	private static final long serialVersionUID = -2939609102111779183L;

    public BaseException(String message)
    {
        super(message);
    }

    public BaseException(Throwable cause)
    {
        super(cause);
    }

    public BaseException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
