package com.ustcsoft.framework.exception;

public class ExtFileUploadException extends BaseException
{
	private static final long serialVersionUID = -9073081327456427481L;

    public ExtFileUploadException(String message)
    {
        super(message);
    }

    public ExtFileUploadException(Throwable cause)
    {
        super(cause);
    }

    public ExtFileUploadException(String message, Throwable cause)
    {
        super(message, cause);
    }
}