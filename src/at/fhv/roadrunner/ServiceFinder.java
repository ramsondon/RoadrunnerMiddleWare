package at.fhv.roadrunner;

import spider.prototype.services.Controller;
import spider.prototype.services.communication.addresses.TCPAddress;
import spider.prototype.services.communication.mappinglayer.TCPMapper;
import spider.prototype.services.yellowpage.ServiceDescription;

public class ServiceFinder implements Runnable {

	private Controller mSpiderController;
	private int mSpiderPort = Config.LOCAL_PORT;
	private int mMaxHops = Config.MAX_HOPS;
	private String mSpiderInetAddress = Config.NEIGHBOUR_ADDRESS;

	public ServiceFinder(Controller controller) {
		mSpiderController = controller;

		TCPMapper tcpMapper = new TCPMapper(mSpiderPort);
		tcpMapper.setAddress(new TCPAddress(Config.LOCAL_ADDRESS));

		System.out.println("TcpMapper address: " + tcpMapper.getAddress());

		mSpiderController.getCommunicationService().addMapper(tcpMapper);
		mSpiderController.getCommunicationService().addNeighbour(
				new TCPAddress(mSpiderInetAddress));
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Broadcast ServiceRequest");
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
