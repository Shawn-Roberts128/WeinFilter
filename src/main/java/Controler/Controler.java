package Controler;

import Model.*;

import org.jzy3d.maths.Range;

/**
 * By: Shawn Roberts For: CS 202 Projects
 * <p/>
 * <p/>
 * Purpose ::
 */
 public class Controler {
    private WeinFilter chamber = null;
    private VoltAccel stage = null;
    private boolean preAcc = false;
    private double centerX = 0;
    private double centerY = 0;
    private double rad = 1;
    private Particle intial = null;          // initial position of the Particle
    private Particle stopGap= null;          // position of the Particle before the Chamber




    /** Constructor **/
    public Controler(){
        this.intial = new Particle();
        this.stopGap = new Particle();
    }


    /**
     * @return
     */
    public boolean setToIdealChamber(){
        if (this.chamber == null){
            this.chamber = new WeinFilter(0d, 0d);
            return true;
        }
        else if (this.chamber.getClass() == WeinChamber.class) { // needs to switch
            this.chamber = new WeinFilter(this.chamber);
            return true;
        }
        else if (this.chamber.getClass() == WeinFilter.class) // no need to switch
            return true;
        else return false;
    }


    /**
     * @param length
     * @param radius
     *
     * @return
     */
    public boolean setToRealChamber( double length , double radius){
        if (this.chamber == null){
            this.chamber = new WeinChamber(0d,0d,radius,length);
            return true;
        }
        else if (this.chamber.getClass() == WeinFilter.class) { // Need to witch
            this.chamber = new WeinChamber(this.chamber,radius,length);
            return true;
        }
        else if (this.chamber.getClass() == WeinFilter.class) // no need to switch
            return true;
        else return false;
    }


    /**
     * @return
     */
    public boolean setToIdealAcc(){
        if (this.stage == null) {
            this.stage = new VoltAccel(0d, 0d);
            return true;
        }
        else if ( stage.getClass()== AccelBox.class )    // Need to change
        {
            this.stage = new VoltAccel( this.stage);
            return true;
        }
        else if (stage.getClass() == VoltAccel.class)     // no need to change
            return true;
        else
            return false;
    }

    /**
     * @param height
     * @param width
     * @param depth
     *
     * @return
     */
    public boolean setToRealAcc(float height, float width, float depth){
        if (this.stage == null) {
            this.stage = new AccelBox(0d,0d,new Range(-depth,depth), new Range(-width,width), new Range(-height,height) );
            return true;
        }
        else if ( stage.getClass()== VoltAccel.class )    // Need to change
        {
            this.stage = new AccelBox( this.stage,  new Range( -height, height ),
                    new Range( -width, width ),
                    new Range( -depth, depth ));
            return true;
        }
        else if (stage.getClass() == AccelBox.class)     // no need to change
            return true;
        else
            return false;
    }

    /**
     * @param centerX
     * @param centerY
     * @param rad
     */
    public void updateHole( double centerX, double centerY, double rad){
        this.centerX = centerX;
        this.centerY = centerY;
        this.rad = rad;
    }

    /** Run     :: Gets the total trajectory of the Particle through the entire thing
     *
     * @param time          The time that is used to compute the full trajectory of the
     * @return              The total trajectory of the Particle
     * @throws Initialised
     */
    public Particle [] run(double [] time ) throws Initialised {

        try {
            Particle[] stage1 = {};
            Particle[] stage2 = {};
            Particle[] totalTraj = null;


            // check if it use the voltage accelerator or not for the initial stage
            if (!preAcc) {
                this.stage.setFleck(intial);
                stage1 = this.stage.trajectory(time);
                stage1 = prune( stage1);

                stopGap = stage1[stage1.length-1];
            } else stopGap = intial;


            // Compute the second stage of the trajectory
            this.chamber.init(stopGap);
            stage2 = this.chamber.trajectory(time);

            totalTraj = new Particle[stage1.length+stage2.length];

            int count = 0;

            for (int i = 0; i < stage1.length; ++i,++count){
                totalTraj[count]= stage1[i];
            }
            for (int i = 0; i < stage2.length ; ++i, ++count){
                totalTraj[count]= stage2[i];
            }


            return totalTraj;

        // if its not initialised then catch in the UI and print message
        } catch ( Initialised initialised){
            initialised.printStackTrace();
            throw initialised;
        }
    }


    /**
     * @param arr
     *
     * @return
     */
    private int find_end (Particle [] arr) {
        if (arr == null )
            return 0;

        Particle nan = Particle.NaN();
        for (int i = 0; i < arr.length;++i){
            if (! arr[i].isequal(nan)) {
                return i;
            }

        }
        return arr.length;
    }

    /**
     * @param tooBig
     *
     * @return
     */
    private Particle [] prune ( Particle [] tooBig){

        Particle [] temp = tooBig;

        int newEnd = find_end(tooBig);
        tooBig = new Particle[newEnd];
        for (int i = 0; i < newEnd; ++i){
            tooBig[i] = temp[i];
        }

        return tooBig;
    }

    public WeinFilter getChamber() {
        return chamber;
    }

    public void setChamber(WeinFilter chamber) {
        this.chamber = chamber;
    }

    public VoltAccel getStage() {
        return stage;
    }

    public void setStage(VoltAccel stage) {
        this.stage = stage;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getRad() {
        return rad;
    }

    public void setRad(double rad) {
        this.rad = rad;
    }

    public Particle getIntial() {
        return intial;
    }

    public void setIntial(Particle intial) {
        this.intial = intial;
    }


    public void setAccelBox(double v) {//TODO add

    }

    public void setChamberRad(double v) {
        
    }

    public void setChamberLen(double v) {

    }
}


