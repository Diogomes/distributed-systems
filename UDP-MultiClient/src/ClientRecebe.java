import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientRecebe implements Runnable {
    private Socket s;

    public ClientRecebe(Socket s) {
        this.s = s;
    }

    public void run() {
        try {
            BufferedReader brIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
            while (true) {
                String s = brIn.readLine();
                String[] strs = s.split("\\.");
                String info = strs[0];     
                String name = "", line = "";
                if (strs.length == 2)
                    line = strs[1];
                else if (strs.length == 3) {
                    line = strs[1];
                    name = strs[2];
                }

                if (info.equals("1")) {  // 1 para msg
                    TelaClient.textMessage.append(line + "\r\n");
                    TelaClient.textMessage.setCaretPosition(TelaClient.textMessage.getText().length());
                } else if (info.equals("2") || info.equals("3")) { // 2 para entrar e 3 para sair
                    if (info.equals("2")) {
                        TelaClient.textMessage.append("(Alerta) " + name + " entrou!" + "\r\n");
                        TelaClient.textMessage.setCaretPosition(TelaClient.textMessage.getText().length());
                    } else {
                        TelaClient.textMessage.append("(Alerta) " + name + " saiu!" + "\r\n");
                        TelaClient.textMessage.setCaretPosition(TelaClient.textMessage.getText().length());
                    }
                    String list = line.substring(1, line.length() - 1);
                    String[] data = list.split(",");
                    TelaClient.user.clearSelection();
                    TelaClient.user.setListData(data);
                } else if (info.equals("4")) {  // 4 para alertas
                    TelaClient.connect.setText("entrar");
                    TelaClient.sair.setText("sair");
                    TelaClient.socket.close();
                    TelaClient.socket = null;
                    JOptionPane.showMessageDialog(TelaClient.window, "Alguém já está usando esse nome de usuário");
                    break;
                } else if (info.equals("5")) {   // 5 para fechar o servidor
                    TelaClient.connect.setText("entrou");
                    TelaClient.sair.setText("saiu");
                    TelaClient.socket.close();
                    TelaClient.socket = null;
                    break;
                } else if (info.equals("6")) {  // 6 para msg privada
                    TelaClient.textMessage.append("(Mensagem privada) " + line + "\r\n");
                    TelaClient.textMessage.setCaretPosition(TelaClient.textMessage.getText().length());
                } else if (info.equals("7")) {
                    JOptionPane.showMessageDialog(TelaClient.window, "Esse usuário não está online");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(TelaClient.window, "O usuário saiu");
        }
    }
}