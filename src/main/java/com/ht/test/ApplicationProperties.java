package com.ht.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
 

/**
 * 
 * @author Michail.Bousios
 *
 */
public class ApplicationProperties {
 
	private Properties props;
 
	private final String databaseFile;
	private final String databasePath;
	private final String databaseTable;
	private final String rabbitmqConnectionString;
	private final String rabbitmqTestQueue;
	private final String vehicleId;
	private final String configUrl;
	private final String version;
	private final String soundRelay;
	private final String amplifyRelay;
	private final boolean soundUseMpd;
	private final String soundMpdHostname;
	private final int soundMpdPort;
	private final String testMp3FileName;
	private final String nxtComPort;
	
	
	public ApplicationProperties() throws IOException {
		this("conf/application.properties");
	}

	public ApplicationProperties(String applicationPropertiesPath) throws IOException {
		props = loadProperties(applicationPropertiesPath);
		this.databaseFile = props.getProperty("db.file");
		this.databasePath = props.getProperty("db.file.path");
		this.databaseTable = props.getProperty("db.mandatory.tables");
		this.rabbitmqConnectionString = props.getProperty("RABBIT_CONNECTION_STRING");
		this.rabbitmqTestQueue = props.getProperty("RABBIT_TEST_QUEUE_NAME");
		this.vehicleId  = props.getProperty("VEHICLE_ID");
		this.configUrl  = props.getProperty("CONFIG_URI");
		this.version  = props.getProperty("VERSION");
		this.soundRelay  = props.getProperty("SOUND_RELAY");
		this.amplifyRelay  = props.getProperty("AMPLIFY_RELAY");
		this.soundUseMpd  =Boolean.valueOf( props.getProperty("SOUNDPLAYER_USE_MPD"));
		this.soundMpdHostname  = props.getProperty("SOUNDPLAYER_MPD_HOSTNAME");
		this.soundMpdPort  = Integer.parseInt( props.getProperty("SOUNDPLAYER_MPD_PORT") );
		this.testMp3FileName =  props.getProperty("TEST_MP3_FILE");
		this.nxtComPort =  props.getProperty("NXT_Com_Port");
	} 
	
	
	public String getNxtComPort() {
		return nxtComPort;
	}

	public String getTestMp3FileName() {
		return testMp3FileName;
	}

	public String getRabbitmqTestQueue() {
		return rabbitmqTestQueue;
	}

	public String getRabbitmqConnectionString() {
		return rabbitmqConnectionString;
	}

	public String getDatabaseFile() {
		return databaseFile;
	}

	public String getDatabasePath() {
		return databasePath;
	}

	public String getDatabaseTable() {
		return databaseTable;
	}

	
	public String getVehicleId() {
		return vehicleId;
	}

	public String getConfigUrl() {
		return configUrl;
	}

	public String getVersion() {
		return version;
	}

	public String getSoundRelay() {
		return soundRelay;
	}
	
	public int getSoundRelayAsInt() {
		return Integer.valueOf(soundRelay);
	}

	public String getAmplifyRelay() {
		return amplifyRelay;
	}
	
	public int getAmplifyRelayAsInt() {
		return Integer.valueOf(amplifyRelay);
	}


	public boolean isSoundUseMpd() {
		return soundUseMpd;
	}

	public String getSoundMpdHostname() {
		return soundMpdHostname;
	}

	public int getSoundMpdPort() {
		return soundMpdPort;
	}

	private static Properties loadProperties(String applicationPropertiesPath) throws IOException {
		Properties props = new Properties();
		try (InputStream input = new FileInputStream(applicationPropertiesPath)) {
			props.load(input);
		} catch (IOException error) {
			System.out.println("Failed to load {}" +  applicationPropertiesPath);
			throw error;
		}
		return props;
	}
}
