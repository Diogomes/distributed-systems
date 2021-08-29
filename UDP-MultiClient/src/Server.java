import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map;

import javax.swing.JOptionPane;

public class Server implements Runnable {
    private int porta;
    public static ArrayList<Socket> listaUsuario = null;
    public static Vector<String> nomeUsuario = null;    // thread security
    public static Map<String, Socket> map = null;
    public static ServerSocket ss = null;
    public static boolean flag = true;
	

    public Server(int porta) throws IOException {
        this.porta = porta;
    }

    public void run() {
        Socket s = null;
        listaUsuario = new ArrayList<Socket>();   //Contém portaas dos usuários
        nomeUsuario = new Vector<String>();      //contém usuários
        map = new HashMap<String, Socket>();   //name to socket one on one map

        System.out.println("Servidor iniciado!");

        try {
            ss = new ServerSocket(porta);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        while (flag) {
            try {
                s = ss.accept();
                listaUsuario.add(s);
                new Thread(new ServerRecebe(s, listaUsuario, nomeUsuario, map)).start();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(TelaServer.window, "Servidor encerrado！");
            }
        }
    }
}