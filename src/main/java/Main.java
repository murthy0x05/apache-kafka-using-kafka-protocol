import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class Main {
  public static void main(String[] args){

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

      dos.writeInt(0);
      dos.writeInt(Integer.parseInt("00000007", 8));

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
