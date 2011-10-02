package at.fhv.roadrunner;

import spider.prototype.services.Controller;
import spider.prototype.services.communication.addresses.TCPAddress;
import spider.prototype.services.communication.mappinglayer.TCPMapper;
import spider.prototype.services.yellowpage.ServiceDescription;

public class ServiceFinder implements Runnable {

	private Controller mSpiderController;
	private int mSpiderPort = 10034;
	private int mMaxHops = 5;
	private String mSpiderInetAddress = "172.102.16.182";

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
				System.out.println("SERVICEFINDER BROADCAST FAILED");
				e.printStackTrace();
			}
		}
	}

}
