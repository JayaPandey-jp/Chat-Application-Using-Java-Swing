package ChatingApplication;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.SimpleFormatter;

public class Client extends JFrame implements ActionListener {
    JTextField text;
    static JPanel a1, right;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;

    static JFrame f = new JFrame();

    Client() {
        setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(2, 41, 18));
        p1.setBounds(0, 0, 440, 50);
        p1.setLayout(null);
        add(p1);

        ImageIcon close = new ImageIcon(ClassLoader.getSystemResource("ChatingApplication/icons/3 (1).png"));
        Image close1 = close.getImage().getScaledInstance(25, 30, Image.SCALE_DEFAULT);
        ImageIcon closeIconResized = new ImageIcon(close1);
        JLabel close2 = new JLabel(closeIconResized);
        close2.setBounds(5, 10, 25, 25);
        p1.add(close2);

        close2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon profile = new ImageIcon(ClassLoader.getSystemResource("ChatingApplication/icons/2.png"));
        Image profile2 = profile.getImage().getScaledInstance(30, 35, Image.SCALE_DEFAULT);
        ImageIcon profileIconResized = new ImageIcon(profile2);
        JLabel profile1 = new JLabel(profileIconResized);
        profile1.setBounds(40, 10, 35, 35);
        p1.add(profile1);

        ImageIcon video = new ImageIcon(ClassLoader.getSystemResource("ChatingApplication/icons/video (1).png"));
        Image video1 = video.getImage().getScaledInstance(30, 31, Image.SCALE_DEFAULT);
        ImageIcon videoIconResized = new ImageIcon(video1);
        JLabel video2 = new JLabel(videoIconResized);
        video2.setBounds(250, 15, 35, 35);
        p1.add(video2);

        ImageIcon phone1 = new ImageIcon(ClassLoader.getSystemResource("ChatingApplication/icons/phone (1).png"));
        Image phone2 = phone1.getImage().getScaledInstance(30, 31, Image.SCALE_DEFAULT);
        ImageIcon phoneIconResized = new ImageIcon(phone2);
        JLabel phone = new JLabel(phoneIconResized);
        phone.setBounds(300, 15, 35, 35);
        p1.add(phone);

        ImageIcon dot = new ImageIcon(ClassLoader.getSystemResource("ChatingApplication/icons/3icon (1).png"));
        Image dotImage = dot.getImage().getScaledInstance(10, 21, Image.SCALE_DEFAULT);
        ImageIcon dotIconResized = new ImageIcon(dotImage);
        JLabel dot1 = new JLabel(dotIconResized);
        dot1.setBounds(350, 15, 35, 35);
        p1.add(dot1);

        JLabel name = new JLabel("RANI");
        name.setBounds(80, 5, 60, 20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN SARIF", Font.BOLD, 14));
        p1.add(name);

        JLabel status = new JLabel("Active now");
        status.setBounds(80, 25, 90, 20);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN SARIF", Font.PLAIN, 12));
        p1.add(status);

        a1 = new JPanel();
        a1.setBounds(5, 50, 430, 500);
        add(a1);

        text = new JTextField();
        text.setBounds(5, 560, 300, 50);
        text.setFont(new Font("SAN SARIF", Font.PLAIN, 16));
        add(text);

        JButton send = new JButton("SEND");
        send.setBounds(310, 560, 100, 50);
        send.setBackground(Color.green);
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        add(send);

        setSize(440, 610);
        getContentPane().setBackground(new Color(208, 248, 218));
        setLocation(800, 50);
        setUndecorated(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String out = text.getText();
            JLabel output = new JLabel(out);

            JPanel p2 = formatLabel(out);
            a1.setLayout(new BorderLayout());
            right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);

            text.setText(" ");

            dout.writeUTF(out);

            repaint();
            invalidate();
            validate();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel(out);
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 30));
        panel.add(output);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Client();
        });

        try {
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while (true) {
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);

                SwingUtilities.invokeLater(() -> {
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical, BorderLayout.PAGE_START);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
