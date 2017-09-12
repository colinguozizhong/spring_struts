package com.ustcsoft.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter
  implements Filter
{
  protected FilterConfig filterConfig = null;

  protected String encoding = null;

  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
    throws IOException, ServletException
  {
    if (this.encoding != null)
      servletRequest.setCharacterEncoding(this.encoding);
    servletResponse.setCharacterEncoding(this.encoding);
    filterChain.doFilter(servletRequest, servletResponse);
  }

  public void destroy()
  {
    this.filterConfig = null;
    this.encoding = null;
  }

  public void init(FilterConfig filterConfig)
    throws ServletException
  {
    this.filterConfig = filterConfig;
    this.encoding = filterConfig.getInitParameter("encoding");
  }
}