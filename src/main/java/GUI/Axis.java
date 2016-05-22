package GUI;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;

/**
 * By: Shawn Roberts
 * For: CS 202 Projects
 * <p/>
 * <p/>
 * Purpose ::
 */

public class Axis extends JFrame{

    private StringListener textListener;
    private JSlider value;
    private JPanel panel1;
    private JTextField min;
    private JTextField max;

    private JTextArea test ;
    String val;


    public static void main (String [] ignore) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(false);

                Axis ax = new Axis("X Axis");
                ax.getContentPane().setBackground(Color.black);
                ax.setVisible(true);
            }
        });
    }

    public Axis(String title){
        super(title);
        val = String.valueOf(title.charAt(0));


        initUI();
    }

    private void initUI(){

        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.getContentPane().setLayout(new BorderLayout());

        slider();

        minmax();

        test = new JTextArea();

        this.getContentPane().add(test,BorderLayout.CENTER);


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Container c = this.getContentPane();
        c.setBackground( Color.BLACK );
        this.getContentPane().setBackground(Color.black);
        this.repaint();

        this.setVisible(true);
        this.pack();this.revalidate();
        //this.setSize(screenSize.width/4, screenSize.height/4);
        this.setLocationRelativeTo(null);

        value.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                textListener.textEmmited(Integer.toString(value.getValue()));
                System.out.println(value.getValue());
                test.append(String.valueOf(value.getValue()));
            }
        });


    }

    public void setTextListener(StringListener textListener) {
        this.textListener = textListener;
    }

    private void slider(){

        value = new JSlider();
        value.createStandardLabels(1,0);
        value.setMajorTickSpacing(10);
        value.setMinorTickSpacing(5);
        value.setPaintTicks(true);
        value.setPaintLabels(true);
        value.setValue(50);
        value.setSize(new Dimension(100, 10));


        Hashtable labelTable = new Hashtable();
        labelTable.put(0   , new JLabel("0.0") );
        labelTable.put(500 , new JLabel("0.5") );
        labelTable.put(1000, new JLabel("1.0") );
        value.setLabelTable(labelTable);
    }

    private void minmax () {

        min = new JTextField("min" + val);
        min.setSize(new Dimension(20,20));
        max = new JTextField("max" + val);
        max.setSize(new Dimension(20,20));

        this.getContentPane().add(value,BorderLayout.PAGE_END);
        this.getContentPane().add(min,BorderLayout.LINE_START);
        this.getContentPane().add(max,BorderLayout.LINE_END );



    }
}
