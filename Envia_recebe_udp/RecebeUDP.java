
import java.io.*; import java.net.*;
public class RecebeUDP
{ public static void main(String[] args) throws Exception
  { byte[] buffer = new byte[1024];
    String s;
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    DatagramSocket socket = new DatagramSocket(1234);

    try
    { socket.setSoTimeout(10000);
      for(int i = 0; i < 5; i++)
      { try
        { socket.receive(packet);
          s = new String(buffer,0, packet.getLength());
          System.out.println("From: "+ packet.getAddress().getHostName()+":"+ packet.getPort()+":"+s);
        }
        catch(InterruptedIOException e)
        { System.out.println("TimeOut: " + e); }
        // Podemos fazer algo antes de tentar de novo
      }
    }
    catch(SocketException e)
{ System.out.println("Erro de Socket: " + e); }

  }
}

