package Model;

/**
 * By: Shawn Roberts
 * For: CS 202 Projects
 * <p/>
 * <p/>
 * Purpose ::
 */
public class Particle {

    private Cord position;
    private Cord velocity;
    private Cord acceleration;

    int t;


    public Particle( Cord position, Cord velocity,Cord acceleration) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this .t = 0;
    }
}
