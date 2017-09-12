package com.ustcsoft.framework.exception;

public class DaoException extends BaseException
{

	private static final long serialVersionUID = 5975513637251194298L;

    public DaoException(String message)
    {
        super(message);
    }

    public DaoException(Throwable cause)
    {
        super(cause);
    }

    public DaoException(String message, Throwable cause)
    {
        super(message, cause);
    }
}