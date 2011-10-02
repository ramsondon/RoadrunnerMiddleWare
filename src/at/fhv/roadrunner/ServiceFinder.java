package at.fhv.roadrunner;

import spider.prototype.services.Controller;
import spider.prototype.services.communication.addresses.TCPAddress;
import spider.prototype.services.communication.mappinglayer.TCPMapper;
import spider.prototype.services.yellowpage.ServiceDescription;

public class ServiceFinder implements Runnable {

	private Controller mSpiderController;
	private int mSpiderPort = 4711;
	private int mMaxHops = 15;
	private String mSpiderInetAddress = "9.9.9.9";

	public ServiceFinder(Controller controller) {
		mSpiderController = controller;
		mSpiderController.getCommunicationService().addMapper(
				new TCPMapper(mSpiderPort));
		mSpiderController.getCommunicationService().addNeighbour(
				new TCPAddress(mSpiderInetAddress));
	}

	@Override
	public void run() {
		while (true) {
			try {
				mSpiderController.broadcastServiceRequest(
						ServiceDescription.Temperature, 0, mMaxHops);
				
				Thread.sleep(5 * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
