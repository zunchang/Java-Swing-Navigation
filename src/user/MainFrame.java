package user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class MainFrame extends JFrame implements ActionListener {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image imager = toolkit.getImage("校徽.jpeg");
    JButton jbt1, jbt2;
    JTextField starting;
    JTextField destination;
    JLabel jLabel=new JLabel();
    final JTextArea text = new JTextArea();
    public MainFrame() throws IOException {
        //地图图片
        JPanel titlePanel = new JPanel();
        //地图图片导入
        ImageIcon icon = new ImageIcon("烟大地图.jpg");
        JLabel titleLabel = new JLabel();
        titlePanel.setBounds(0, 0, 865, 800);
        //设置图像大小
        icon.setImage(icon.getImage().getScaledInstance(titlePanel.getWidth(), titlePanel.getHeight(), Image.SCALE_DEFAULT));
        titleLabel.setIcon(icon);
        titlePanel.add(titleLabel);

        //起点框
        starting = new JTextField();
        //设置边框透明
        // starting.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        starting.setBounds(1000, 150, 150, 30);
        //设为透明
        starting.setOpaque(false);
        //终点框
        destination = new JTextField();
        destination.setBounds(1000, 185, 150, 30);
        destination.setOpaque(false);
        //点标记
        JLabel Lstarting = new JLabel("起点");
        Lstarting.setFont(new Font("宋体", 1, 20));
        Lstarting.setForeground(Color.ORANGE);
        Lstarting.setBounds(950, 150, 60, 30);
        JLabel Ldestination = new JLabel("终点");
        Ldestination.setFont(new Font("宋体", 1, 20));
        Ldestination.setForeground(Color.ORANGE);
        Ldestination.setBounds(950, 186, 60, 30);
        //按钮
        jbt1 = new JButton("查询");
        jbt1.setBounds(955, 232, 75, 35);
        jbt1.setContentAreaFilled(false);//设置按钮透明
        jbt1.setBorder(null);//取消边框
        jbt1.setFont(new Font("黑体", Font.LAYOUT_LEFT_TO_RIGHT, 18));
        jbt1.setForeground(Color.ORANGE);
        jbt2 = new JButton("退出");
        jbt2.setBounds(1105, 232, 75, 35);
        jbt2.setContentAreaFilled(false);//设置按钮透明
        jbt2.setBorder(null);//取消边框
        jbt2.setFont(new Font("黑体", Font.LAYOUT_LEFT_TO_RIGHT, 18));
        jbt2.setForeground(Color.ORANGE);
        //最短路径

        jLabel.setBounds(955,300,200,35);
        //背景图片
        JPanel titlePanel1 = new JPanel();
        ImageIcon background = new ImageIcon("背景2.jpg");    //创建一个背景图片
        JLabel label1 = new JLabel(background);        //把背景图片添加到标签里
        label1.setBounds(865, 0, 535, 800);    //把标签设置为和图片等高等宽
        titlePanel1 = (JPanel) this.getContentPane();        //把我的面板设置为内容面板
        titlePanel1.setOpaque(false);                    //把我的面板设置为不可视
        titlePanel1.setLayout(new FlowLayout());        //把我的面板设置为流动布局

        this.getLayeredPane().add(label1, new Integer(Integer.MIN_VALUE));        //把标签添加到分层面板的最底层
        //关闭布局管理器
        this.setLayout(null);
        this.add(jbt1);
        this.add(jbt2);
        this.add(jLabel);
        this.add(Lstarting);
        this.add(Ldestination);
        this.add(starting);
        this.add(destination);
        this.add(titlePanel);
        this.setIconImage(imager);       //设置窗口图标
        this.setTitle("烟大导航系统");     //设置窗体标题
        this.setSize(1400, 800);        //设置窗体大小
        this.setLocation(0, 0);        //设置位置
        this.setVisible(true);
        this.setResizable(false);
        jbt1.addActionListener(this);
        jbt2.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取按钮文本
        String str = e.getActionCommand();
        if (str.equals("查询")) {
            String nstarting = starting.getText().trim();//起点
            String ndestination = destination.getText().trim();//终点
            journey journey=new journey();
            int key[][]=journey.journey(nstarting,ndestination);
            if(key==null){
                JOptionPane pane = new JOptionPane("目的地就在您的周围，请认真观察四周");
                JDialog dialog = pane.createDialog(this, "提示");
                dialog.setVisible(true);
            }
            else {
                //定义绘图画布
                Graphics graphics=this.getGraphics();
                //进行画线操作
                brush(graphics,key);
            }
            jLabel.setText(journey.distance);
            jLabel.setFont(new Font("宋体", 1, 15));
            jLabel.setForeground(Color.ORANGE);
            query qy=new query();
            String QUERY=qy.introduce(ndestination);
            JOptionPane pane = new JOptionPane(QUERY);
            JDialog dialog = pane.createDialog(this, "介绍");
            dialog.setVisible(true);
        }
        if (str.equals("退出")) {
            this.setVisible(false);
            JOptionPane pane = new JOptionPane("感谢您的使用");
            JDialog dialog = pane.createDialog(this, "提示");
            dialog.setVisible(true);
        }
    }

    public void brush(Graphics graphics,int[][] arr){
        //设置Graphicsd的副本
        Graphics2D g2 = (Graphics2D)graphics;
        this.paint(graphics);
        this.paint(g2);
        //设置画笔颜色
        g2.setColor(Color.GREEN);
        //设置画笔的宽度
        g2.setStroke(new BasicStroke(5));
        g2.fillOval(574,727,1,1);
        for (int i = 0; i < arr[0].length; i++) {
            if(arr[0][i]==0||arr[0][i+1]==0)
                break;
            g2.drawLine(arr[0][i],arr[1][i],arr[0][i+1],arr[1][i+1]);
        }
    }
}


