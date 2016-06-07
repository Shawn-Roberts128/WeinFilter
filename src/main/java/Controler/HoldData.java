package Controler;

import Model.Particle;

/**
 * By: Shawn Roberts For: CS 202 Projects
 * <p/>
 * <p/>
 * Purpose ::
 */
public class HoldData {
    public double [] time;
    public Particle [] traj;

    public HoldData(double[] time, Particle[] traj) {
        this.time = time;
        this.traj = traj;
    }
}
