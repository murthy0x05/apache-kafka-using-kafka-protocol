package java.main;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class Main {
	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		int port = 9092;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setReuseAddress(true);
			clientSocket = serverSocket.accept();

			InputStream is = clientSocket.getInputStream();
			OutputStream os = clientSocket.getOutputStream();

			DataInputStream dis = new DataInputStream(is);
			DataOutputStream dos = new DataOutputStream(os);

			int message_size = dis.readInt();
			short request_api_version = dis.readShort();
			short request_api_key = dis.readShort();
			int correlation_id = Integer.parseInt(dis.readNBytes(4).toString(), 16);

			dos.writeInt(message_size);
			dos.writeInt(correlation_id);

			dos.flush();

			dis.close();
			dos.close();
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		} finally {
			try {
				if (clientSocket != null) {
					clientSocket.close();
				}
			} catch (IOException e) {
				System.out.println("IOException: " + e.getMessage());
			}
		}
	}
}
