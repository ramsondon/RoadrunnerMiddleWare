package at.fhv.roadrunner;

import java.util.List;

import spider.prototype.services.Controller;
import spider.prototype.services.modules.IDataRequester;
import spider.prototype.services.yellowpage.ServiceDescription;
import spider.prototype.services.yellowpage.YellowPage;

public class ServiceRequest {

	/**
	 * The Spider Protocol Controller
	 */
	private Controller mController;

	/**
	 * The data byte array
	 */
	private byte[] mData;

	/**
	 * Flag for marking if data have been received
	 */
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

	public char[] getData() {
		
		char[] data = new char[mData.length];
		for (int i = 0; i < mData.length; i++) {
			data[i] = (char)mData[i];
		}
		return data;
	}

}
