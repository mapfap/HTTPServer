import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.logging.Logger;

public class Server {

	public Server(int port, Process process, Logger logger) {
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("Server is running at port " + port);
			while (true) {
				Socket client = server.accept();
				(new ServerThread(client, process, logger)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class ServerThread extends Thread {

	private Socket client;
	private Process process;
	private Logger logger;

	public ServerThread(Socket client, Process process, Logger logger) {
		this.client = client;
		this.process = process;
		this.logger = logger;
	}
	
	public static String getTimestamp() {
		return Calendar.getInstance().getTime().toString();
	}

	@Override
	public void run() {
		try {
			
			String incomingIP = client.getInetAddress().toString();
			int incomingPort = client.getPort();
			logger.info("Connection from: " + incomingIP + ":" + incomingPort);
			InputStream in = client.getInputStream();
			OutputStream out = client.getOutputStream();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, "UTF-8"));
			  
			for (int i = 0; i < 2000; i++) {      
			      boolean ready = reader.ready();
			     if (ready)  
			        break;  
			    try {  
			        Thread.sleep(1);  
			    } catch (InterruptedException e) {}  
			}
			
			StringBuilder sb = new StringBuilder();
			while (reader.ready()) {
				sb.append((char) reader.read());
			}
			
//			String data = sb.toString().split("\r\n\r\n")[1];
//			data = URLDecoder.decode(data, "UTF-8");
//			System.out.println(data);
			logger.info(sb.toString());

			String response = "HTTP/1.1 200 OK\r\nServer: Mapfap1.0\r\nContent-Type: text/html\r\nAccess-Control-Allow-Origin: *\r\n\r\n";
//			response += process.run(data) + "\r\n";
			response += process.run("") + "\r\n";

			byte[] bytes = response.getBytes();
			out.write(bytes);
			out.flush();
			in.close();
			out.close();
			logger.info("Connection closed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
