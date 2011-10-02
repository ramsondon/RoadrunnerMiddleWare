package at.fhv.roadrunner;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spider.prototype.services.Controller;

/**
 * Servlet implementation class RoadrunnerController
 */
@WebServlet({ "/sensor" })
public class RoadrunnerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String CONTEXT_ATTR_SERVICE_FINDER = "servicefinder";

	private AddressMapper mAddressMapper;

	/**
	 * Default constructor.
	 */
	public RoadrunnerController() {
		mAddressMapper = new AddressMapper();
	}

	@Override
	public void init() throws ServletException {
		super.init();

		if (Controller.getInstance() == null) {
			Controller.setInstance(new Controller());
		}

		if (getServletContext().getAttribute(CONTEXT_ATTR_SERVICE_FINDER) == null) {

			new Thread(new ServiceFinder(Controller.getInstance())).start();
			getServletContext().setAttribute(CONTEXT_ATTR_SERVICE_FINDER,
					new Boolean(true));
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String sensor = parseSensor(request);

		ServiceRequest req = new ServiceRequest(Controller.getInstance(),
				sensor, mAddressMapper);
		 req.sendRequest();

		// implement notify sleep
		 while (!req.dataReceived()) {
		// // sleep while no data received
		 }

		response.getWriter().write(req.getData().toString());
		// response.getWriter().write(sensor);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private String parseSensor(HttpServletRequest request) {
		return request.getQueryString();
	}

}
