package Model;

/**
 * By: Shawn Roberts For: CS 202 Projects
 * <p/>
 * <p/>
 * Purpose ::
 */
public class Colision extends Throwable{
    public Particle [] traj;

    public Colision( Particle [] traj){

        this.traj = traj;
    }
}
