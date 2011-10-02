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
@WebServlet({ "/" })
public class RoadrunnerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Controller mController;

	private AddressMapper mAddressMapper;
	
	/**
	 * Default constructor.
	 */
	public RoadrunnerController() {
		mController = Controller.getInstance();
		mAddressMapper = new AddressMapper();
		
		if (getServletContext().getAttribute("servicefinder") == null) {
			
			new Thread(new ServiceFinder(Controller.getInstance())).start();
			getServletContext().setAttribute("servicefinder", new Boolean(true));
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String sensor = parseSensor(request);
		
		
		ServiceRequest req = new ServiceRequest(mController, sensor, mAddressMapper);
//		req.sendRequest();
		
		// implement notify sleep
//		while (!req.dataReceived()) {
//			// sleep while no data received
//		}
		
		response.getWriter().write(req.getData().toString());
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
		
		System.out.println(request.getContextPath());
		
		return null;
	}
	
}
