package Proxy;

import pattern.MainWindow;
import pattern.Window;
import pattern.WindowImp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ProtectionProxy extends Window {
    private JOptionPane jOptionPane;
    private JDialog jDialog;
    private HashMap<String,String> authorizeUsers = new HashMap<>();
    private MainWindow mainWindow;
    private Boolean isTest=false;
    private Boolean isLogin;
    private String username,password;

    public ProtectionProxy(WindowImp impl) {
        super(impl);
        addAuthorizeUser("1111","Daniel");
        mainWindow = new MainWindow(impl);
    }

    public void addAuthorizeUser(String name,String password){
        authorizeUsers.put(name,password);
    }

    @Override
    public boolean run() {
        showLoginDialog();
        try {
            if (isLogin == true) {
                mainWindow.run();
                Object[] options = {"yes"};
                jOptionPane.showOptionDialog(null, "Welcome to editor, " + username, "Text editor", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                return true;
            } else {
                Object[] options = {"yes"};
                jOptionPane.showOptionDialog(null, "password is wrong", "Text editor", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                return false;
            }
        }catch(Exception e){
            Object[] options = {"yes"};
            jOptionPane.showOptionDialog(null, "account is wrong", "Text editor", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            return false;
        }
    }

    public boolean compare(String password,String result){
            byte[] p_byte = password.getBytes();
            byte[] r_byte = result.getBytes();
            //result = 2222
            //password = 2222
            for (int i = 0; i < result.length(); i++) {
                if ( p_byte.length <= i)
                    if(r_byte[i] != 32)
                        return false;
                    else{
                        return true;
                    }

                if (p_byte[i] != r_byte[i] )
                    return false;
            }
            return true;
    }

    public void showLoginDialog(){
        jDialog = new JDialog();
        JTextField jtf1,jtf2;
        jDialog.setTitle("Text editor");
        jDialog.setModal(true);
        jDialog.setSize(400,150);
        jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jDialog.setLocationRelativeTo(null);
        JLabel jl1 = new JLabel("Please input account :");
        jtf1 = new JTextField(8);
        JLabel jl2 = new JLabel("Please input password :");
        jtf2 = new JTextField(8);
        JLabel jl3 = new JLabel("Sign up");
        JLabel jl4 = new JLabel("hint:");
        jl3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jDialog.setVisible(false);
                showRegisterDialog();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }


        });
        JPanel jp = new JPanel(new GridLayout(3,2));
        jp.add(jl1);
        jp.add(jtf1);
        jp.add(jl2);
        jp.add(jtf2);
        jp.add(jl4);
        jp.add(jl3,BorderLayout.LINE_END);
        JButton jb = new JButton("Login");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SQLCmd getUser;
                username = jtf1.getText();
                password = jtf2.getText();
                getUser = new GetUser(username);
                String result = (String) getUser.excute();
                if(result != null) {
                    if (compare(password, result)) {
                        isLogin = true;
                        jDialog.setVisible(false);
                    } else {
                        isLogin = false;
                        jl4.setText("hint : password is wrong");
                    }
                }else{
                    jl4.setText("hint : account is wrong");
                }
            }
        });
        jDialog.add(jp);
        jDialog.add(jb,BorderLayout.SOUTH);
        jDialog.setVisible(true);
    }

    public void showRegisterDialog(){
        jDialog = new JDialog();
        JTextField jtf1,jtf2;
        jDialog.setTitle("Text editor");
        jDialog.setModal(true);
        jDialog.setSize(400,150);
        jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jDialog.setLocationRelativeTo(null);
        JLabel jl1 = new JLabel("Please input account :");
        jtf1 = new JTextField(8);
        JLabel jl2 = new JLabel("Please input password :");
        jtf2 = new JTextField(8);
        JLabel jl3 = new JLabel("hint:");
        JButton jb = new JButton("Sign up");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SQLCmd signUpUser;
                username = jtf1.getText();
                password = jtf2.getText();
                signUpUser = new SignUpUser(username,password);
                signUpUser.excute();
                jDialog.setVisible(false);
                Object[] options = { "yes" };
                jOptionPane.showOptionDialog(null, "Sing up successfully.", "Text editor",  JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,  null, options, options[0]);
                run();
            }
        });
        jtf1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                SQLCmd getAllUser = new GetAllUser();
                ArrayList<String> usernames = (ArrayList<String>) getAllUser.excute();
                for(String username : usernames){
                    System.out.println(compare(jtf1.getText(),username));
                    if(compare(jtf1.getText(),username)){
                        jl3.setText("hint : account is duplicated");
                        jb.setEnabled(false);
                        break;
                    }else{
                        jl3.setText("hint :");
                        jb.setEnabled(true);
                    }
                }
            }
        });
        JPanel jp = new JPanel(new GridLayout(3,2));
        jp.add(jl1);
        jp.add(jtf1);
        jp.add(jl2);
        jp.add(jtf2);
        jp.add(jl3);
        jDialog.add(jp);
        jDialog.add(jb,BorderLayout.SOUTH);
        jDialog.setVisible(true);
    }
}
