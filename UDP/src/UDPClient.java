import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Diogo Gomes on 8/8/2021.
 */
public class UDPClient implements Runnable {
    private final int port;

    public UDPClient(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        /**
         * Bind the client socket to the port on which you expect to
         * read incoming messages
         */
        try (DatagramSocket clientSocket = new DatagramSocket(port)) {
            /**
             * Create a byte array buffer to store incoming data. If the message length
             * exceeds the length of your buffer, then the message will be truncated. To avoid this,
             * you can simply instantiate the buffer with the maximum UDP packet size, which
             * is 65506
             */

            byte[] buffer = new byte[65507];

           
            clientSocket.setSoTimeout(3000);
            while (true) {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);

                clientSocket.receive(datagramPacket);

                String receivedMessage = new String(datagramPacket.getData());
                System.out.println(receivedMessage);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Timeout. Client is closing.");
        }
    }
}