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
    private JButton position;
    private JButton velocity;

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
}
