package relink.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

import com.MAVLink.MAVLinkPacket;

public class MAVConnection implements Connection, SerialPortEventListener {

	private String telemetryPort;
	private String WHOIPort;
	private int telemetryRate;
	private int whoiRate;
	
	private CommPortIdentifier telemetryID;
	private CommPortIdentifier whoiID;
	
	private SerialPort telemetrySerial;
	private SerialPort whoiSerial;
	
	private InputStream telemetryInput;
	private OutputStream telemetryOutput;
	
	private InputStream whoiInput;
	private OutputStream whoiOutput;
	
	
	public MAVConnection()
	{
		this.telemetryPort = new String();
		this.WHOIPort = new String();
		
		this.telemetryRate = 0;
		this.whoiRate = 0;
	}
	
	
	@Override
	public void sendMAVPacket(MAVLinkPacket p) {
		// TODO Auto-generated method stub
		byte[] b = p.encodePacket();
		// send the byte buffer over serial connection 
	}


	@Override
	public void connectTelemetry(String port, int baudRate) {
		// TODO Auto-generated method stub
		try {
			this.telemetryID = CommPortIdentifier.getPortIdentifier(port);
			this.telemetrySerial = (SerialPort) this.telemetryID.open("RE_LINK", 2000);
			this.telemetrySerial.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			this.telemetrySerial.addEventListener(this);
			this.telemetrySerial.setDTR(false);
			this.telemetrySerial.setRTS(false);
			this.telemetrySerial.notifyOnDataAvailable(true);
			this.telemetryInput=this.telemetrySerial.getInputStream();
			this.telemetryOutput=this.telemetrySerial.getOutputStream();
			
		} catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void disconnectTelemetry() {
		// TODO Auto-generated method stub
		this.telemetrySerial.close();
	}


	@Override
	public void connectWHOI(String port, int baudRate) {
		// TODO Auto-generated method stub
		try {
			this.whoiID = CommPortIdentifier.getPortIdentifier(port);
			this.whoiSerial = (SerialPort) whoiID.open("RE_LINK", 2000);
			this.whoiSerial.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			this.whoiSerial.setDTR(false);
			this.whoiSerial.setRTS(false);
			this.whoiSerial.addEventListener(this);
			this.whoiSerial.notifyOnDataAvailable(true);
			this.whoiInput=this.whoiSerial.getInputStream();
			this.whoiOutput=this.whoiSerial.getOutputStream();
		} catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void disconnectWHOI() {
		// TODO Auto-generated method stub
		this.whoiSerial.close();
	}


	@Override
	public void serialEvent(SerialPortEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
