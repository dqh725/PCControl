package alba.office.pccontrol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Receiver extends Thread {
	protected final static JSONParser parser = new JSONParser();
	Bitmap image=null;
	private DatagramSocket aSocket;
	boolean running =false;
	public Receiver(){
		
	}
	
	public void setSocket(DatagramSocket aSocket){
		this.aSocket = aSocket;
	}
	
	@Override
	public void run(){
		while(running){
			image = getImage();
		}
	}
	
	public void stopRun(){
		running = false;
	}
	public void startRun(){
		running = true;
	}
	private Bitmap getImage(){
		JSONObject obj=new JSONObject();
		byte[] buffer = new byte[500];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		try {
			aSocket.receive(reply);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int realSize = reply.getLength(); //Method suggested by Stephen C.
		Log.i("json",Integer.toString(realSize));
		byte[] realPacket = new byte[realSize];
		
		System.arraycopy(buffer, 0, realPacket, 0, realSize);
		Log.i("json","point1");
		
		try {
			obj = (JSONObject) parser.parse(new String(realPacket));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("json","point1");
		buffer = null;
		Log.i("json","again:"+(String)(obj.get("Type")));
		byte[] image = (byte[]) obj.get("Value");
		
		return BitmapFactory.decodeByteArray(image,0,image.length);
	}
}
