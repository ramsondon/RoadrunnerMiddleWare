package at.fhv.roadrunner;

import spider.prototype.services.Controller;
import spider.prototype.services.communication.addresses.TCPAddress;
import spider.prototype.services.communication.mappinglayer.TCPMapper;
import spider.prototype.services.yellowpage.ServiceDescription;

public class ServiceFinder implements Runnable {

	private Controller mProtocolController;
	private Configuration mConfig;

	public ServiceFinder(Controller controller, Configuration config) {
		mProtocolController = controller;
		mConfig = config;

		TCPMapper tcpMapper = new TCPMapper(mConfig.getLocalServerPort());
		tcpMapper.setAddress(new TCPAddress(mConfig.getLocalServerAddress()));

		System.out.println("TcpMapper address: " + tcpMapper.getAddress());

		mProtocolController.getCommunicationService().addMapper(tcpMapper);
		mProtocolController.getCommunicationService().addNeighbour(
				new TCPAddress(mConfig.getNeighbourAddress()));
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Broadcast ServiceRequest");
				mProtocolController.broadcastServiceRequest(
						ServiceDescription.Temperature, 0, mConfig.getMaxHops());

				Thread.sleep(5 * 60 * 1000);
			} catch (InterruptedException e) {
				System.out.println("SERVICEFINDER BROADCAST FAILED");
				e.printStackTrace();
			}
		}
	}

}
