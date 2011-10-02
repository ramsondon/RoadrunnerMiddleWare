package at.fhv.roadrunner;

import java.util.ArrayList;

import spider.prototype.services.yellowpage.ServiceDescription;
import spider.prototype.services.yellowpage.YellowPage;

public class AddressMapper {

	private ArrayList<Mapping> mMappings;

	public AddressMapper() {
		mMappings = new ArrayList<Mapping>();
		
		mMappings.add(new Mapping("1", ServiceDescription.Temperature, 1));
		mMappings.add(new Mapping("2", ServiceDescription.Temperature, 2));
		mMappings.add(new Mapping("3", ServiceDescription.GPSPosition, 1));
	}

	public boolean isEqual(String sensor, YellowPage page) {

		Mapping map = new Mapping(sensor, page.getServiceDescription(), page.getServiceId()); 
		for (Mapping m : mMappings) {
			if (m.compareTo(map) == 0) {
				return true;
			}
		}
		return false;
	}

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
