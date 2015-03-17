package alba.office.pccontrol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


public class InstrcutionFactory {
	
	protected final static JSONParser parser = new JSONParser();
	

	String IP;
	DatagramSocket aSocket = null;
	public InstrcutionFactory(){}
	
	public InstrcutionFactory(String IP){
		this.IP = IP;
	}
	
	public void sendPacket(String message, String IP){
		Log.i("info1","point1");
		try {

			aSocket = new DatagramSocket();

			byte [] m = message.getBytes();// data initialization
			//System.out.print(new String(m));
			InetAddress aHost = InetAddress.getByName(IP); //host IP

			int serverPort = 6789;//port bundle
			DatagramPacket request =
					new DatagramPacket(m, m.length, aHost, serverPort);//create a packet

			aSocket.send(request); //socket.send packet
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	@SuppressWarnings("unchecked")
	public void sendMoveMsg(float deltaX, float deltaY) {
		JSONObject obj = new JSONObject();
		obj.put("Type", "Move");
		obj.put("SpeedX", Integer.toString((int)deltaX));
		obj.put("SpeedY", Integer.toString((int)deltaY));
		String msg = obj.toJSONString();
		sendPacket(msg,IP);
	}
	
	@SuppressWarnings("unchecked")
	public void sendClickMsg(){	
		JSONObject obj2 = new JSONObject();
		obj2.put("Type", "Click");
		String msg= obj2.toJSONString();
		sendPacket(msg, IP);
		//Log.i("info", msg);
	}
	
	@SuppressWarnings("unchecked")
	public void sendLockMsg(){
		JSONObject obj2 = new JSONObject();
		obj2.put("Type", "Lock");
		String msg= obj2.toJSONString();
		sendPacket(msg, IP);
	}
	@SuppressWarnings("unchecked")
	public void sendUnlockMsg(){
		JSONObject obj2 = new JSONObject();
		obj2.put("Type", "Unlock");
		String msg= obj2.toJSONString();
		sendPacket(msg, IP);
	}
	




}
