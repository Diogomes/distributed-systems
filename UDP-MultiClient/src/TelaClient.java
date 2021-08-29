import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TelaClient {
    public static JFrame window;
    public static JButton connect, sair;
    public static JTextArea textMessage;
    public static Socket socket = null;
    public static JList<String> user;

    JTextField nomeUsuario, porta, message, msgPriv;
    JButton msgPrivada, enviar;

    //main
    public static void main(String[] args) {
        new TelaClient();
    }

    public TelaClient() {
        init();
    }

    public void init() {
        window = new JFrame("Bate Papo | Nota Final");
        window.setLayout(null);
        window.setBounds(400, 400, 530, 420);
        window.setResizable(true);

        JLabel label_nomeUsuario = new JLabel("Usu�rio:");
        label_nomeUsuario.setBounds(10, 28, 70, 30);
        window.add(label_nomeUsuario);

        nomeUsuario = new JTextField();
        nomeUsuario.setBounds(80, 28, 70, 30);
        window.add(nomeUsuario);

        JLabel label_porta = new JLabel("porta:");
        label_porta.setBounds(180, 28, 50, 30);
        window.add(label_porta);

        porta = new JTextField();
        porta.setBounds(230, 28, 50, 30);
        window.add(porta);

        connect = new JButton("Entrar");
        connect.setBounds(300, 28, 90, 30);
        window.add(connect);

        sair = new JButton("Sair");
        sair.setBounds(400, 28, 90, 30);
        window.add(sair);

        textMessage = new JTextArea();
        textMessage.setBounds(10, 70, 340, 220);
        textMessage.setEditable(false);

        textMessage.setLineWrap(true);
        textMessage.setWrapStyleWord(true);

        JLabel label_text = new JLabel("�rea de Mensagens");
        label_text.setBounds(100, 58, 200, 50);
        window.add(label_text);

        JScrollPane paneText = new JScrollPane(textMessage);
        paneText.setBounds(10, 90, 360, 240);
        window.add(paneText);

        JLabel label_listaUsuario = new JLabel("Lista de Usu�rios");
        label_listaUsuario.setBounds(380, 58, 200, 50);
        window.add(label_listaUsuario);

        user = new JList<String>();
        JScrollPane paneUser = new JScrollPane(user);
        paneUser.setBounds(375, 90, 140, 240);
        window.add(paneUser);

        JLabel label_Alerta = new JLabel("Digite Mgs para o grupo");
        label_Alerta.setBounds(10, 320, 180, 50);
        window.add(label_Alerta);

        message = new JTextField();
        message.setBounds(10, 355, 188, 30);
        message.setText(null);
        window.add(message);

        JLabel label_Aviso = new JLabel("Add usu�rio para msg privada");
        label_Aviso.setBounds(272, 320, 250, 50);
        window.add(label_Aviso);

        msgPriv = new JTextField();
        msgPriv.setBounds(272, 355, 100, 30);
        window.add(msgPriv);

        msgPrivada = new JButton("Msg Privada");
        msgPrivada.setBounds(376, 355, 140, 30);
        window.add(msgPrivada);

        enviar = new JButton("Grupo");
        enviar.setBounds(190, 355, 77, 30);
        window.add(enviar);

        myEvent();  // add conectados/ouvindo a porta
        window.setVisible(true);
    }

    public void myEvent() {
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (socket != null && socket.isConnected()) {
                    try {
                        new ClientEnvia(socket, getnomeUsuario(), "3", "");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                System.exit(0);
            }
        });

        sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (socket == null) {
                    JOptionPane.showMessageDialog(window, "A conex�o foi encerrada!");
                } else if (socket != null && socket.isConnected()) {
                    try {
                        new ClientEnvia(socket, getnomeUsuario(), "3", "");
                        connect.setText("Entrar");
                        sair.setText("saiu!");
                        socket.close();
                        socket = null;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (socket != null && socket.isConnected()) {
                    JOptionPane.showMessageDialog(window, "Conectado!");
                } else {
                    String ipString = "127.0.0.1";
                    String portaClinet = porta.getText();
                    String name = nomeUsuario.getText();

                    if ("".equals(name) || "".equals(portaClinet)) {
                        JOptionPane.showMessageDialog(window, "O usu�rio ou a portaa n�o podem estar vazios!");
                    } else {
                        try {
                            int portas = Integer.parseInt(portaClinet);
                            socket = new Socket(ipString, portas);
                            connect.setText("Entrou");
                            sair.setText("sair");
                            new ClientEnvia(socket, getnomeUsuario(), "2", "");
                            new Thread(new ClientRecebe(socket)).start();
                        } catch (Exception e2) {
                            JOptionPane.showMessageDialog(window, "falha em conectar-se, verifique o ip e a portaa");
                        }
                    }
                }
            }
        });

        msgPrivada.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarMsgmsgPriv();
            }
        });

        enviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarMsg();
            }
        });
    }

    public void enviarMsg() {
        String messages = message.getText();
        if ("".equals(messages)) {
            JOptionPane.showMessageDialog(window, "N�o h� nada para enviar");
        } else if (socket == null || !socket.isConnected()) {
            JOptionPane.showMessageDialog(window, "Sem conex�o");
        } else {
            try {
                new ClientEnvia(socket, getnomeUsuario() + ": " + messages, "1", "");
                message.setText(null);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(window, "falha ao enviar!");
            }
        }
    }

    public void enviarMsgmsgPriv() {
        String messages = message.getText();
        if ("".equals(messages)) {
            JOptionPane.showMessageDialog(window, "N�o h� nada para enviar!");
        } else if (socket == null || !socket.isConnected()) {
            JOptionPane.showMessageDialog(window, "Sem conex�o");
        } else {
            try {
                new ClientEnvia(socket, getnomeUsuario() + ": " + messages, "4", getmsgPrivada());
                TelaClient.textMessage.append(getnomeUsuario() + ": " + messages + "\r\n");
                message.setText(null);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(window, "Mensagem privada n�o enviado!");
            }
        }
    }

    public String getnomeUsuario() {
        return nomeUsuario.getText();
    }

    public String getmsgPrivada() {
        return msgPriv.getText();
    }
}