package servicepoint;

import info.sswap.api.servlet.SimpleSSWAPServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * Servlet implementation class MySSWAPServlet
 */

public class MySSWAPServlet extends SimpleSSWAPServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Class<T> getServiceClass() {
		return (Class<T>) SSWAPService.class;
	}

}