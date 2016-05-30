package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * By: Shawn Roberts For: CS 202 Projects
 * <p/>
 * <p/>
 * Purpose ::
 */
public class tst {
    private JPanel panel1;
    private SSlider SSlider1;
    private JSlider slider1;
    private JSlider slider2;
    private JSlider slider3;
    private JSlider slider4;
    private JSlider slider5;
    private JSlider slider6;
    private JTextField textField1;
    private JButton position;

    public tst() {
        position.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getSource() == position ) {
                    Axis position = new Axis("Position");
                    System.out.println("press");
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
