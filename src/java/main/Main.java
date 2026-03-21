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
			int correlation_id = dis.readInt();

			dos.writeInt(0);
			dos.writeInt(correlation_id);
			if (request_api_version < 0 || request_api_version > 4) {
				dos.writeShort(35);
			} else {
				dos.writeShort(0);
			}

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
