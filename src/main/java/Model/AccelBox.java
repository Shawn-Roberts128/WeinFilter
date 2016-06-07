package Model;

import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.primitives.Parallelepiped;

import java.util.Collection;

/**
 * By: Shawn Roberts For: CS 202 Projects
 * <p/>
 * <p/>
 * Purpose ::
 */
public class AccelBox extends VoltAccel{


    private Range x;
    private Range y;
    private Range z;


    /** Constructors **/

    public AccelBox(double leftVolt, double rightVolt, Range x, Range y, Range z) {
        super(leftVolt, rightVolt);
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public AccelBox(Range x, Range y, Range z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public AccelBox( VoltAccel from, Range x, Range y, Range z) {
        super(from);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean collision (Particle particle){
        Cord pos = particle.position;

        if (!this.x.contains((float)particle.position.x))
            return true;

        if (!this.y.contains((float)particle.position.y))
            return true;

        if (!this.z.contains((float)particle.position.z))
            return true;
        
        return false;
    }

    @Override
    public Particle [] trajectory (double [] time)throws Initialised{


        if ( !checkInit() ) throw new Initialised("Values not Initialised");

        Particle traj [] = new Particle[ time.length ];
        traj[0]= new Particle( fleck );

        for ( int i = 1 ; i < time.length ; ++i ){

            traj[i] = instant( time[i]);
            if(collision(traj[i])) {
                traj[i]= Particle.NaN();

                Particle [] hit = new Particle[i+1];
                for (int j = 0;j <i ; ++j)
                    hit[j]=traj[j];

                return hit;
            }

        }

        return traj;
    }

    public void setX(Range x) {
        this.x = x;
    }

    public void setY(Range y) {
        this.y = y;
    }

    public void setZ(Range z) {
        this.z = z;
    }
}

