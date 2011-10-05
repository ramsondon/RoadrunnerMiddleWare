package at.fhv.roadrunner;

import java.util.List;

import spider.prototype.services.Controller;
import spider.prototype.services.modules.IDataRequester;
import spider.prototype.services.yellowpage.ServiceDescription;
import spider.prototype.services.yellowpage.YellowPage;
import spider.prototype.utils.BinaryTransformer;

public class ServiceRequest {

	/**
	 * The Spider Protocol Controller
	 */
	private Controller mController;

	/**
	 * The data byte array
	 */
	private int mData;

	/**
	 * Flag for marking if data have been received
	 */
	private boolean mDataReceived;

	private String mSensor;

	private AddressMapper mAddressMapper;

	public ServiceRequest(Controller controller, String sensor,
			AddressMapper addressMapper) {
		mController = controller;
		mDataReceived = false;
		mAddressMapper = addressMapper;
		mSensor = sensor;
	}

	/**
	 * Sends a request to receive current data of the Sensor of this
	 * ServiceRequest
	 */
	public void sendRequest() {
		List<YellowPage> pages = mController
				.getYellowPage(ServiceDescription.Temperature);

		for (YellowPage page : pages) {

			if (mAddressMapper.isEqual(mSensor, page)) {

				mController.sendDataRequest(new IDataRequester() {

					@Override
					public void onDataResponse(ServiceDescription serviceDesc,
							int serviceId, byte[] response) {
						mData = BinaryTransformer.toInt(response);
						//mData = response;
						mDataReceived = true;
						System.out.println("onDataResponse: " + serviceId);
					}

				}, page.getAddress(), page.getServiceDescription(), page
						.getServiceId());
			}
		}
	}

	/**
	 * Checks if data have been received
	 * 
	 * @return true if data have been received, else false
	 */
	public boolean dataReceived() {
		return mDataReceived;
	}

	public char[] getData() {
		
		System.out.println(mData);
		return Integer.toString(mData).toCharArray();
	}

}
