import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        ServerSocket serverSocket = new ServerSocket(10000);
        System.out.println("Server startet");
        while (true) {
            Socket socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            int intFromClient = dataInputStream.readInt();

            dataOutputStream.writeInt(intFromClient);



        }
    }
}
