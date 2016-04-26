package Controler;

import javax.swing.*;
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
public class Axis extends JPanel{

    private StringListener textListener;
    private JSlider Value;
    private JPanel panel1;
    private JTextField Min;
    private JTextField valMaxTextField;


    public static void main (String [] ignore) {
        Axis test = new Axis( "test");
    }

    public Axis(String title){
        //super(title);

        
        Hashtable labelTable = new Hashtable();
        labelTable.put( new Integer( 0 ), new JLabel("0.0") );
        labelTable.put( new Integer( 500 ), new JLabel("0.5") );
        labelTable.put( new Integer( 1000 ), new JLabel("1.0") );
        Value.setLabelTable( labelTable );

        Value.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                textListener.textEmmited( Integer.toString(Value.getValue()) ) ;
                System.out.println(Value.getValue()) ;
            }
        });
    }

    public void setTextListener(StringListener textListener) {
        this.textListener = textListener;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
