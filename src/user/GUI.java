package user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener {
    ImageIcon background;
    JPanel myPanel;
    JLabel label;

    public GUI() {
        background = new ImageIcon("背景.jpeg");    //创建一个背景图片
        label = new JLabel(background);        //把背景图片添加到标签里
        label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());    //把标签设置为和图片等高等宽
        myPanel = (JPanel) this.getContentPane();        //把我的面板设置为内容面板
        myPanel.setOpaque(false);                    //把我的面板设置为不可视
        myPanel.setLayout(new FlowLayout());        //把我的面板设置为流动布局
        //标签
        JLabel label1 = new JLabel("欢迎使用烟大校园导航系统");
        label1.setFont(new Font("宋体", 1, 21));
        label1.setBounds(100, 75, 300, 400);
        //设置字体颜色
        label1.setForeground(Color.RED);
        //左上角设置为校徽
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("校徽.jpeg");
        //设置按钮
        JButton logon = new JButton("进入系统");
        logon.setBounds(100, 200, 160, 90);
        logon.setContentAreaFilled(false);//设置按钮透明
        logon.setBorder(null);//取消边框
        logon.setFont(new Font("黑体", Font.LAYOUT_LEFT_TO_RIGHT, 18));

        this.setLayout(null);//关闭布局管理器
        this.setIconImage(image);
        this.getLayeredPane().setLayout(null);        //把分层面板的布局置空
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));        //把标签添加到分层面板的最底层
        //设置界面属性
        this.setTitle("烟大导航系统");
        this.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(label1);
        this.add(logon);
        this.setResizable(false);
        logon.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        if (str.equals("进入系统")) {
            //
            this.setVisible(false);
            try {
                select Select = new select();
                MainFrame mainFrame=new MainFrame();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}