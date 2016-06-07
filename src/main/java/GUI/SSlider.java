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
public abstract class SSlider extends JPanel implements ChangeListener, ActionListener, DoubleListener {

    private JSlider value;
    private VarTag titleValue;
    private VarTag minV;
    private VarTag maxV;


    String title;
    private double min;
    private double max;
    private double val;
    private double  di; // tick value that updates as the things change


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

                        this.getContentPane().add(new SSlider("X",5d,0d,10d){

                            @Override
                            public void doubleEmmited(double value, ActionEvent event) {
                                System.out.println(value);
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


    public SSlider(String title, double val, double min, double max) {
        super(new BorderLayout(5,5));
        this.title = title;
        this.val = val;
        this.min = min;
        this.max = max;
        this.updateDi();

        initUI();
    }

    private void initUI() {


        slider();

        minmax();

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
        this.setMaximumSize(new Dimension(100, 100));

        makeTableLabels();

        this.add(value,BorderLayout.PAGE_END);
    }

    private void makeTableLabels() {
        double midPoint = (this.max-this.min)/2;
        Hashtable labelTable = new Hashtable();
        labelTable.put(0   , new JLabel(String.valueOf(      min)));
        labelTable.put(500 , new JLabel(String.valueOf( midPoint)));
        labelTable.put(1000, new JLabel(String.valueOf(      max)));
        value.setLabelTable(labelTable);
        value.setPaintLabels(true);
    }

    private void minmax() {

        titleValue = new VarTag(title,this.val) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (NumberUtils.isNumber(this.input.getText().trim())) {
                    valChange();
                    doubleEmmited(val, actionEvent);
                }
                else {
                    this.input.setText(String.valueOf(val));
                    this.updateUI();
                }
            }
        };
        titleValue.setMaximumSize(new Dimension(20,20));



        minV = new VarTag("Min: ",this.min) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if (NumberUtils.isNumber(this.input.getText().trim())) {
                    minChange();
                }
                else
                    this.input.setText(String.valueOf(min));

            }
        };
        minV.setMaximumSize(new Dimension(20, 20));


        maxV = new VarTag("Max: ",this.max) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if (NumberUtils.isNumber(this.input.getText().trim())) {
                   maxChange();
                    updateUI();
                }
                else
                    this.input.setText(String.valueOf(max));
                this.updateUI();
            }
        };

        maxV.setMaximumSize(new Dimension(20, 20));

        this.add(titleValue, BorderLayout.CENTER);
        this.add(minV, BorderLayout.LINE_START);
        this.add(maxV, BorderLayout.LINE_END);


    }

    /** ~~~~~~~~~~~~~ Event Functions ~~~~~~~~~~~~~~~~~~ * */

    @Override
    public void setEnabled ( boolean set) {

        value     .setEnabled(set);
        minV      .setEnabled(set);
        maxV      .setEnabled(set);
        titleValue.setEnabled(set);
    }

    /**
     * @return
     */
    public double valueOf(){
        return  this.min + this.value.getValue() * this.di;
    }

    @Override
    public void updateUI(){
        super.updateUI();

        if (this.minV != null)
            minV.input.setText(String.valueOf(min));


        if (this.maxV != null)
            maxV.input.setText(String.valueOf(max));

        if (this.titleValue != null)
            titleValue.input.setText(String.valueOf(val));
        if (this.value != null )
            this.makeTableLabels();

        this.updateDi();

    }
    private double updateDi(){
        this.di = (this.max-this.min)/1000;
        return this.di;
    }
    private void minChange(){
        this.min = this.minV.getVal();
    }

    private void maxChange(){
        this.max = this.maxV.getVal();
    }

    private void valChange(){
        val = (this.titleValue.getVal());
        this.updateUI();
        int slide = (int)((val - min) / di);
        value.setValue(slide);
    }

    public void sliderChange() {
        int valI = this.value.getValue();
        this.val = min+ valI*di;
        this.titleValue.input.setText(String.valueOf(val));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        this.updateUI();
        this.doubleEmmited(this.val,e);
    }
    @Override
    public void stateChanged(ChangeEvent e)
    {

        this.sliderChange();
        this.updateUI();
        this.doubleEmmited(this.val, new ActionEvent(this,e.hashCode(),"Command"));
    }

}
