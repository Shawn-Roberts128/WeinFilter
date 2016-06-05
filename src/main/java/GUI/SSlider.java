package GUI;

import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

/**
 * By: Shawn Roberts For: CS 202 Projects
 * <p/>
 * <p/>
 * Purpose ::
 */
public abstract class SSlider extends JPanel implements ChangeListener, ActionListener {

    private StringListener textListener;
    private JSlider value;
    private JPanel panel1;
    private JTextField min;
    private JTextField max;

    private JTextArea test;

    String val;
    private double minv;
    private double maxv;

    /** test Main **/

    static public void main (String [] ignore){

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                JFrame here = new JFrame("Here") {
                    {

                        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        this.getContentPane().setLayout(new BorderLayout());


                        this.getContentPane().setBackground(Color.BLUE);

                        this.getContentPane().add(new SSlider("x"){
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                this.updateUI();
                            }
                            @Override
                            public void stateChanged(ChangeEvent e)
                            {

                                JSlider source = (JSlider) e.getSource();
                                if (source != null) this.sliderChange (source.getValue());
                            }

                        });


                        this.setVisible(true);


                        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                        this.pack();
                        this.revalidate();
                        this.setSize(screenSize.width/4, screenSize.height/4);
                        this.setLocationRelativeTo(null);
                    }

                };


            }
        });


    }


    /** ~~~~~~~~~~~~~~~~~~~ Initialisation and Constructor ~~~~~~~~~~~~~~~~~~~~~ **/


    public SSlider(String title) {
        super(new BorderLayout(5,5));
        val = String.valueOf(title.charAt(0));


        initUI();
    }

    private void initUI() {


        slider();

        minmax();

        test = new JTextArea();

        this.add(test, BorderLayout.CENTER);


        this.setBackground(Color.BLACK);


        this.setBackground(Color.black);
        this.repaint();

        this.setVisible(true);
        this.revalidate();



    }





    /** ~~~~~~~~~~~~~ Content Functions ~~~~~~~~~~~~~~~~~~~~**/

    private void slider() {

        value = new JSlider();
        value.createStandardLabels(1, 0);
        value.setMajorTickSpacing(10);
        value.setMinorTickSpacing(5);
        value.setPaintTicks(true);
        value.setPaintLabels(true);
        value.setValue(120);
        value.setMaximum(1000);
        value.setMinimum(0);
        value.setSize(new Dimension(100, 10));
        value.addChangeListener(this);

        Hashtable labelTable = new Hashtable();
        labelTable.put(0, new JLabel("0.0"));
        labelTable.put(500, new JLabel("0.5"));
        labelTable.put(1000, new JLabel("1.0"));
        value.setLabelTable(labelTable);
        value.setPaintLabels(true);

        this.add(value);
    }

    private void minmax() {

        min = new JTextField("min" + val);
        min.setSize(new Dimension(20, 20));
        max = new JTextField("max" + val);
        max.setSize(new Dimension(20, 20));
        min.addActionListener(this);
        max.addActionListener(this);



        this.add(value, BorderLayout.PAGE_END);
        this.add(min, BorderLayout.LINE_START);
        this.add(max, BorderLayout.LINE_END);


    }

    /** ~~~~~~~~~~~~~ Event Functions ~~~~~~~~~~~~~~~~~~ * */

    public void setTextListener(StringListener textListener) {
        this.textListener = textListener;
    }
    /*
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == this.min) {
                String newString = this.min.getText();
                if (NumberUtils.isNumber(newString)) {

                    minv = NumberUtils.toDouble(newString);
                }

                min.setText("Min: " + minv);
            } else if (e.getSource() == this.max) {
                String newString = this.max.getText();
                if (NumberUtils.isNumber(newString)) {

                    maxv = NumberUtils.toDouble(newString);
                }
                max.setText("Max: " + maxv);
            }
        }

        @Override
        public void stateChanged(ChangeEvent e)
        {

            JSlider source = (JSlider) e.getSource();
            if (source != null) sliderChange(source.getValue());
        }

        */
    @Override
    public void setEnabled ( boolean set) {

        value.setEnabled(set);
        min.setEnabled(set);
        max.setEnabled(set);

        test.setEnabled(set);
    }

    /**
     * @return
     */
    public double valueOf(){
        return (value.getValue()+minv);
    }

    @Override
    public void updateUI(){
        super.updateUI();

        if (this.min != null) {
            String newString = this.min.getText();
            if (NumberUtils.isNumber(newString)) {

                minv = NumberUtils.toDouble(newString);
            }

            min.setText("Min: " + minv);
        } else if (this.max!= null) {
            String newString = this.max.getText();
            if (NumberUtils.isNumber(newString)) {

                maxv = NumberUtils.toDouble(newString);
            }
            max.setText("Max: " + maxv);
        }

    }


    public void sliderChange(int input) {
        System.out.println("\n\nv:" + input +"\tMin :"+minv +"\tMax: "+maxv+ " ::\n");
        test.setText(" vi = " + input+"\tval :"+(input/100)*maxv+"\n");
    }
}
