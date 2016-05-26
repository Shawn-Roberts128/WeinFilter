package Model;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.rendering.canvas.Quality;

/**
 * By: Shawn Roberts For: CS 202 Projects
 * <p/>
 * <p/>
 * Purpose ::
 */
public class WeinChamber extends WeinFilter{

    private double length = Double.NaN; // in y
    private double radius = Double.NaN; // in x and z

     /** Test Main **/
    public static void main(String [] ignore) throws Exception {

        AnalysisLauncher.open(new AbstractAnalysis() {
            @Override
            public void init() throws Exception {

                // Initialisation
                int size = 1000000;

                double electric = 1.0;
                // double magnetic = 0.1; // for testing radius
                double magnetic = 1.0; // for testing length

                double mass = 1.0;
                double charge = 1.0;
                double rad = 50;
                double leng = 100;


                Cord not = new Cord(0, 0, 0);

                // Cord vel = new Cord(0, 1, 0); // number 1 test should be a line should stop after length
                // Cord vel = new Cord(0, 1, 1); // test should be a "bouncing ball" should stop if too big
                 Cord vel = new Cord(10, 10, 10);    // test should be a spiral should stop if too big

                // Make filter object
                WeinChamber filter = new WeinChamber(magnetic, electric,rad,leng);
                filter.init(new Particle(not, vel, not, mass, charge));

                // Time and trajectory
                double[] timeLine = new double[size];
                double time = 0.01;

                for (int i = 0; i < size; ++i) {
                    timeLine[i] = i * time;
                }

                Particle[] traj = filter.trajectory(timeLine);
                Coord3d[] points = new Coord3d[size];

                // convert the trajectory to cords to be plotted
                for (int i = 0; i < size; ++i) {

                    points[i] = traj[i].pathCord();

                    // used to output the points
                    // System.out.println("T: "+ timeLine[i]+"\tP: "+points[i]);
                }

                // Plot the trajectory
                Scatter scatter = new Scatter(points);
                chart = AWTChartComponentFactory.chart(Quality.Advanced, "newt");
                chart.getScene().add(scatter);
            }
        });

    }


    public WeinChamber(double magnetic, double eletric, double radius, double length){
        super (magnetic,eletric);
        this.radius= radius;
        this.length = length;
    }

    /** Trajectory :: takes in an array of floats and computes the trajectory over that time interval
     *      This outputs an array of points, representing the particle's information at that given moment.
     *      If there is a collision with a wall then it stops and outputs NaN for the remaining values
     *
     * @param time [] An array of floats, should start at 0 and end at some given length
     * @return an array of particles resulting in the acceleration, velocity, and position of the particle
     *      at any given moment.
     * @throws Initialised
     */
    public Particle[] trajectory( double [] time )  throws Initialised {

        if (!this.init) throw new Initialised(" Constants not initialised");


        int i = 0; // needs function scope to use in catch
        try {

            Particle traj[] = new Particle[time.length];
            traj[0] = new Particle(fleck);
            double rad =0 ;

            // compute the values at the given time
            for (i = 0; i < time.length; ++i) {

                traj[i] = instant(time[i]);

                // Check for collision
                rad = Math.sqrt(Math.pow(traj[i].position.x,2)+Math.pow(traj[i].position.z,2));
                if ((traj[i].position.y > length) || ( rad > radius))
                    throw new Colision(traj);
            }

            return traj;

            // hit wall or got to end of tube need to set rest of array to null;
        } catch (Colision colision) {

            for (; i < time.length; ++i){
                colision.traj[i]= new Particle(); // default particle is a Nan Filled
            }
            return colision.traj;
        }

    }
}
