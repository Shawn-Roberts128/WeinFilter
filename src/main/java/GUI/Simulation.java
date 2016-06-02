package GUI;


import org.jzy3d.chart.Chart;
import org.jzy3d.chart.SwingChart;

import org.jzy3d.chart.factories.SwingChartComponentFactory;
import org.jzy3d.colors.*;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.*;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.CanvasSwing;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import javax.swing.*;

import java.awt.*;
import java.awt.Color;


/**
 * By: Shawn Roberts For: CS 202 Projects
 * <p/>
 * <p/>
 * Purpose ::
 */
public class Simulation extends JPanel{
    private LineStrip time;
    private Chart space;




    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Test Main ~~~~~~~~~~~~~~~~~~~~~~~~ **/
     static public void main (String [] ignore){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                new JFrame("Here") {
                    {

                        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        this.getContentPane().setLayout(new BorderLayout());


                        this.getContentPane().setBackground(Color.BLUE);


                        Simulation sim = new Simulation();
                        Coord3d [] line  = new Coord3d[3];
                        line[0] = new Coord3d(0,0,0);
                        line[1] = new Coord3d(0,0,1);
                        line[2] = new Coord3d(0,1,1);
                        //sim.setTime(line);
                        sim.draw();

                        this.getContentPane().add(new Simulation(),BorderLayout.CENTER);


                        this.setVisible(true);


                        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                        this.pack();
                        this.revalidate();
                        this.setSize(screenSize.width / 4, screenSize.height / 4);
                        this.setLocationRelativeTo(null);
                    }

                };


            }
        });


    }

    private void draw() {
         // Define a function to plot
        Mapper mapper = new Mapper() {
            public double f(double x, double y) {
                return 10 * Math.sin(x / 10) * Math.cos(y / 20) * x;
            }
        };

// Define range and precision for the function to plot
        Range range = new Range(-150, 150);
        int steps = 50;

// Create a surface drawing that function
        Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
        surface.setColorMapper(new ColorMapper());
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);
        surface.setWireframeColor(org.jzy3d.colors.Color.BLACK);


        space.getScene().getGraph().add(surface);
        this.add((javax.swing.JComponent)space.getCanvas());
    }

    public Simulation() {
        super( new BorderLayout());
        space = SwingChartComponentFactory.chart("swing");//new SwingChart(Quality.Nicest);
        this.time = new LineStrip();
        this.add((javax.swing.JComponent) space.getCanvas());
    }



    public void setTime(Coord3d [] time){

        this.space.removeDrawable(this.time);
        this.time.clear();

        for (int i = 0; i < time.length; ++i){
            this.time.add(new Point(time[i]));
        }

        space.getScene().add(this.time);
        space.render();

    }



}
