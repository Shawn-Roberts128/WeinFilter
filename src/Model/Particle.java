package Model;

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

    float mass;


    public Particle( Cord position, Cord velocity,Cord acceleration, float mass) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.mass = mass;
    }

    public Particle( Particle from ) {

        this.position = new Cord (from.position);
        this.velocity = new Cord (from.velocity);
        this.acceleration = new Cord ( from.acceleration);
        this.mass = from. mass;

    }
}
