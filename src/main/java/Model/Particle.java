package Model;
import org.jzy3d.maths.Coord3d;
/**
 * By: Shawn Roberts
 * For: CS 202 Projects
 * <p/>
 * <p/>
 * Purpose ::
 */
public class Particle {

    protected Cord position;
    protected Cord velocity;
    protected Cord acceleration;

    double mass;
    double charge;

    Particle(){

        this.position = new Cord();
        this.velocity = new Cord();
        this.acceleration = new Cord();
        this.mass = 0;
        this.charge = 0;
    }

    public Particle( Cord position, Cord velocity,Cord acceleration, double mass, double charge) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.mass = mass;
        this.charge = charge;
    }


    public Particle( Particle from ) {

        this.position = new Cord (from.position);
        this.velocity = new Cord (from.velocity);
        this.acceleration = new Cord ( from.acceleration);
        this.mass = from. mass;

    }

    @Override
    public String toString() {
        return super.toString();

    }

    Coord3d pathCord(){

        return new Coord3d(this.position.x, this.position.y,this.position.z);

    }
}
