package at.fhv.roadrunner;

import java.util.List;

import spider.prototype.services.Controller;
import spider.prototype.services.communication.addresses.Address;
import spider.prototype.services.modules.IDataRequester;
import spider.prototype.services.yellowpage.ServiceDescription;
import spider.prototype.services.yellowpage.YellowPage;

public class ServiceRequest {

	private Controller mController;

	private byte[] mData;

	private boolean mDataReceived;

	private String sensor;

	private AddressMapper mAddressMapper;

	public ServiceRequest(Controller controller, String sensor,
			AddressMapper addressMapper) {
		mController = controller;
		mData = new byte[0];
		mDataReceived = false;
		mAddressMapper = addressMapper;
	}

	public void sendRequest() {
		List<YellowPage> pages = mController
				.getYellowPage(ServiceDescription.Temperature);
		
		for (YellowPage page : pages) {

			if (mAddressMapper.isEqual(sensor, page)) {

				mController.sendDataRequest(new IDataRequester() {

					@Override
					public void onDataResponse(ServiceDescription serviceDesc,
							int serviceId, byte[] response) {
						mData = response;
						mDataReceived = true;
					}

				}, page.getAddress(), page.getServiceDescription(), page
						.getServiceId());
			}
		}
	}

	public boolean dataReceived() {
		return mDataReceived;
	}

	public byte[] getData() {
		mData = new byte[2];
		mData[0] = '1';
		mData[1] = '7';

		
		return mData;
	}

}
