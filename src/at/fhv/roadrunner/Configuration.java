package at.fhv.roadrunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

	private static final String CONFIG_FILE = "/roadrunner.properties";

	private static final String LOCAL_SERVER_ADDRESS = "localserveraddress";
	private static final String NEIGHBOUR_ADDRESS = "neighbouraddress";
	private static final String LOCAL_SERVER_PORT = "localserverport";
	private static final String MAX_HOPS = "maxhops";

	private String mLocalServerAddress;
	private String mNeighbourAddress;
	private int mLocalServerPort;
	private int mMaxHops;

	/**
	 * The Server Properties
	 */
	private Properties mProperties;

	/**
	 * Constructor
	 */
	public Configuration() {
		mProperties = new Properties();
	}

	/**
	 * Loads the Properties for this Service
	 */
	public void load() {

		try {
			InputStream in = getClass().getClassLoader().getResourceAsStream(
					CONFIG_FILE);
			mProperties.load(in);
			in.close();

			mLocalServerAddress = mProperties.getProperty(LOCAL_SERVER_ADDRESS);
			mNeighbourAddress = mProperties.getProperty(NEIGHBOUR_ADDRESS);
			mLocalServerPort = Integer.parseInt(mProperties
					.getProperty(LOCAL_SERVER_PORT));
			mMaxHops = Integer.parseInt(mProperties.getProperty(MAX_HOPS));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the Local Server Address
	 */
	public String getLocalServerAddress() {
		return mLocalServerAddress;
	}

	/**
	 * @return the Neighbour Node's Address
	 */
	public String getNeighbourAddress() {
		return mNeighbourAddress;
	}

	/**
	 * @return the Local Server Port
	 */
	public int getLocalServerPort() {
		return mLocalServerPort;
	}

	/**
	 * @return the maximum amount of Hops to request neighbours
	 */
	public int getMaxHops() {
		return mMaxHops;
	}
}
