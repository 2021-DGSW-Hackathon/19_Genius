package db;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class Connect {
	static Socket socket = null;
	static PrintWriter writer = null;
	static InputStream input = null;
	main.Main main = new main.Main();
	public static boolean stop = false;
	
	public static void main(String[] args) {
	}
	
	public void connect() {
		try {
			socket = new Socket("192.168.43.2", 13000);
			input = socket.getInputStream();
			writer = new PrintWriter(socket.getOutputStream(), true);
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while(!stop) {						
							threadRun();
						}
					} catch (IOException e) {
						System.exit(0);
					}
				}
			}).start();
			
		} catch (Exception e) {
			connect();
		}
	}
	
	private void threadRun() throws UnsupportedEncodingException, IOException {
		String message = receiveMessage();
		if(message == null) return;
		message = message.trim();
		
		System.out.println(message);
		if(message.split("\t")[0].equals("temp")) {
			main.setTempCard(message.split("\t")[1]);
		} else if(message.split("\t")[0].equals("hum")) {
			main.setHumidCard(message.split("\t")[1]);
		} else if(message.split("\t")[0].equals("chair")) {
			main.setChairPanel(message.split("\t")[1]);
		}
	}
	
	private static String receiveMessage() {
		try {
			byte[] bytes = new byte[1024];
			return new String(bytes, 0, input.read(bytes), "ASCII");			
		} catch (Exception e) {
			return null;
		}
	}
}
