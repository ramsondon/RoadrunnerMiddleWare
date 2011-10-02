package at.fhv.roadrunner;

import java.io.IOException;
import java.util.Date;

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
public class SensorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String CONTEXT_ATTR_SERVICE_FINDER = "servicefinder";

	private AddressMapper mAddressMapper;

	/**
	 * Default constructor.
	 */
	public SensorController() {
		mAddressMapper = new AddressMapper();
	}

	@Override
	public void init() throws ServletException {
		super.init();

		if (Controller.getInstance() == null) {
			Controller.setInstance(new Controller());
		}

		if (getServletContext().getAttribute(CONTEXT_ATTR_SERVICE_FINDER) == null) {
			System.out
					.println("Starting service finder");
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
		System.out.println("Request Sensor: " + sensor);
		
		ServiceRequest req = new ServiceRequest(Controller.getInstance(),
				sensor, mAddressMapper);
		try {
			req.sendRequest();

			// set timeout to 5 seconds
			long timeout = 5 * 1000 + (new Date()).getTime();

			while (!req.dataReceived() && timeout > (new Date()).getTime()) {
				// sleep while no data received
			}
		} catch (Exception e) {
			System.err.println("ASYNC SERVICE NOT RESPONDING");
		}
		if (!req.dataReceived()) {
			response.sendError(500, "SERVICE NOT AVAILABLE");
		} else {
			response.getWriter().write(req.getData());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * @param request
	 * @return
	 */
	private String parseSensor(HttpServletRequest request) {
		String sensor = request.getPathInfo();
		if (sensor != null) {
			sensor = sensor.replace('/', ' ').trim();
		}
		return sensor;
	}

}
