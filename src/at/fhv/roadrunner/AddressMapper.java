package at.fhv.roadrunner;

import java.util.ArrayList;

import spider.prototype.services.yellowpage.ServiceDescription;
import spider.prototype.services.yellowpage.YellowPage;

/**
 * @author matthias schmid
 * @date 02.10.2011
 */
public class AddressMapper {

	private ArrayList<Mapping> mMappings;

	public AddressMapper() 
	{
		mMappings = new ArrayList<Mapping>();

		// Temperature Sensors
		mMappings.add(new Mapping("1", ServiceDescription.Temperature, 1));
		mMappings.add(new Mapping("2", ServiceDescription.Temperature, 2));
		mMappings.add(new Mapping("3", ServiceDescription.Temperature, 3));
		mMappings.add(new Mapping("4", ServiceDescription.Temperature, 4));
		mMappings.add(new Mapping("5", ServiceDescription.Temperature, 5));

		// GPS Sensors
		mMappings.add(new Mapping("6", ServiceDescription.GPSPosition, 1));
	}

	/**
	 * Compares a sensor name with a corresponding YellowPage Entry
	 * 
	 * @param sensor
	 * @param page
	 * @return true if the Sensor is equal to the YellowPage's
	 *         ServiceDescription and ServiceId
	 */
	public boolean isEqual(String sensor, YellowPage page) {

		Mapping map = new Mapping(sensor, page.getServiceDescription(),
				page.getServiceId());
		for (Mapping m : mMappings) {
			if (m.compareTo(map) == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Class Mapping
	 * 
	 * A Comparable class for Address Mapping
	 */
	private class Mapping implements Comparable<Mapping> {
		private String mSensor;
		private ServiceDescription mSd;
		private int mSid;

		public Mapping(String sensor, ServiceDescription sd, int sid) {
			mSensor = sensor;
			mSd = sd;
			mSid = sid;
		}

		@Override
		public int compareTo(Mapping arg0) {
			return (this.mSd == arg0.mSd
					&& this.mSensor.compareTo(arg0.mSensor) == 0 && this.mSid == arg0.mSid) ? 0
					: -1;
		}
	}
}
