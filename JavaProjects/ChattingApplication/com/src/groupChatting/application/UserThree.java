package groupChatting.application;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.net.Socket;

public class UserThree extends JFrame implements ActionListener, Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JPanel upperPanel;
    JTextField textField;
    JButton sendButton;
    static JTextArea textArea;

    BufferedReader reader;
    BufferedWriter writer;
    boolean typing;

    public UserThree() {
        super();

        upperPanel = new JPanel();
        upperPanel.setLayout(null);
        upperPanel.setBackground(new Color(7, 94, 84));
        upperPanel.setBounds(0, 0, 450, 70);
        add(upperPanel);

        ImageIcon backButton = new ImageIcon(
                ClassLoader.getSystemResource("groupChatting/application/icons/backButton.png"));
        Image img1 = backButton.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        JLabel label1 = new JLabel(new ImageIcon(img1));
        label1.setBounds(7, 20, 30, 30);
        upperPanel.add(label1);

        label1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon userPhotoIcon = new ImageIcon(
                ClassLoader.getSystemResource("groupChatting/application/icons/hacker.png"));
        Image img2 = userPhotoIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        JLabel label2 = new JLabel(new ImageIcon(img2));
        label2.setBounds(40, 5, 60, 60);
        upperPanel.add(label2);

        ImageIcon videoCall = new ImageIcon(ClassLoader.getSystemResource("groupChatting/application/icons/video.png"));
        Image img3 = videoCall.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        JLabel label3 = new JLabel(new ImageIcon(img3));
        label3.setBounds(290, 20, 30, 30);
        upperPanel.add(label3);

        ImageIcon phoneCall = new ImageIcon(ClassLoader.getSystemResource("groupChatting/application/icons/phone.png"));
        Image img4 = phoneCall.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        JLabel label4 = new JLabel(new ImageIcon(img4));
        label4.setBounds(350, 20, 35, 30);
        upperPanel.add(label4);

        ImageIcon menuBar = new ImageIcon(
                ClassLoader.getSystemResource("groupChatting/application/icons/threeDot.png"));
        Image img5 = menuBar.getImage().getScaledInstance(13, 25, Image.SCALE_DEFAULT);
        JLabel label5 = new JLabel(new ImageIcon(img5));
        label5.setBounds(410, 20, 13, 25);
        upperPanel.add(label5);

        JLabel userName = new JLabel("Arijit");
        userName.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        userName.setForeground(Color.WHITE);
        userName.setBounds(110, 15, 100, 20);
        upperPanel.add(userName);

        JLabel status = new JLabel("Arijit, Amit, Subham");
        status.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        status.setForeground(Color.WHITE);
        status.setBounds(110, 35, 100, 20);
        upperPanel.add(status);

        Timer timer = new Timer(1, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!typing)
                    status.setText("Arijit, Amit, Subham");
            }       
        });
        timer.setInitialDelay(1000);

        textArea = new JTextArea();
        textArea.setBounds(5, 75, 440, 575);
        textArea.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        add(textArea);

        textField = new JTextField();
        textField.setBounds(5, 655, 310, 40);
        textField.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        add(textField);

        textField.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                status.setText("Arijit typing...");
                timer.stop();
                typing = true;
            }
            
            public void keyReleased(KeyEvent e) {
                typing = false;
                if (!timer.isRunning())
                    timer.start();
            }
        });

        sendButton = new JButton("Send");
        sendButton.setBounds(320, 655, 123, 40);
        sendButton.setBackground(new Color(7, 94, 84));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        add(sendButton);
        sendButton.addActionListener(this);

        getContentPane().setBackground(Color.white);
        setLayout(null);
        setSize(450, 700);
        setLocation(940, 50);
        setUndecorated(true);
        setVisible(true);

        Socket socket = null;
        try {
            socket = new Socket("localhost", 2020);

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e1) {
            try {
                socket.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            e1.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String message = "Arijit:\n" + textField.getText();
        try {
            writer.write(message);
            writer.write("\r\n\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        textField.setText("");
    }

    @Override
    public void run() {
        String message = "";
        try {
            while ((message = reader.readLine()) != null) {
                textArea.append(message + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new UserThree());
        thread.start();
    }
}