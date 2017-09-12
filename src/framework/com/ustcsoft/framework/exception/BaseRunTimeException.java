package com.ustcsoft.framework.exception;


public class BaseRunTimeException extends RuntimeException
{
	private static final long serialVersionUID = -2192268543117351714L;

    public BaseRunTimeException(String message)
    {
        super(message);
    }

    public BaseRunTimeException(Throwable cause)
    {
        super(cause);
    }

    public BaseRunTimeException(String message, Throwable cause)
    {
        super(message, cause);
    }
}