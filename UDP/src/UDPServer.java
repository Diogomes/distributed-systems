import java.io.IOException;
import java.net.*;

/**
 * Created by Diogo Gomes on 8/8/2021.
 */
public class UDPServer implements Runnable {
    /**
     * The port where the client is listening.
     */
    private final int clientPort;

    public UDPServer(int clientPort) {
        this.clientPort = clientPort;
    }

    @Override
    public void run() {
        /**
         * Create a new server socket and bind it to a free port. I have chosen
         * one in the 49152 - 65535 range, which are allocated for internal applications
         */
        try (DatagramSocket serverSocket = new DatagramSocket(50000)) {
            // The server will generate 3 messages and send them to the client
            for (int i = 0; i < 3; i++) {
                String message = "Message number " + i;
                DatagramPacket datagramPacket = new DatagramPacket(
                        message.getBytes(),
                        message.length(),
                        InetAddress.getLocalHost(),
                        clientPort
                );
                serverSocket.send(datagramPacket);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}