package Controler;

import Model.*;

import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;

import java.util.LinkedList;

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
    private double centerZ = 0;
    private double radius = 1;
    private Particle intial = null;          // initial position of the Particle
    private Particle stopGap= null;          // position of the Particle before the Chamber

    private double timeEnd= 10;
    private double dtime = 1;
    private int numSteps = 1;




    /** Constructor **/
    public Controler(){
        this.intial = new Particle(Cord.ZERO(),Cord.ZERO(),Cord.ZERO(),1,1);
        this.stopGap = new Particle();
        this.stage=new VoltAccel(0d,1d);
        this.chamber = new WeinFilter(1d,1d);
    }





    /**
     * @return
     *
     * @throws Initialised
     */
    public Coord3d [] run () throws Exception {

        if (! preAcc) {     // for simple filter
            return runSimple();

        }else {             // Cambered filter

            return runChamber();
        }
    }

    public HoldData run( int ignore) throws Exception {

        if (!preAcc) {     // for simple filter
            numSteps = (int) Math.floor(timeEnd / dtime);
            double[] time = new double[numSteps];
            for (int i = 0; i < numSteps; ++i) {
                time[i] = dtime * (double) i;
            }
            return new HoldData(time,run(time));
        } else {             // Cambered filter

            Particle nan = Particle.NaN();
            double time = 0;
            LinkedList<Double> timeFrame = new LinkedList<Double>();
            LinkedList<Particle> traj = new LinkedList<Particle>();
            Particle next = this.intial;
            this.stage.setFleck(this.intial);

            // run though accelerator
            while (!next.equals(nan)) {
                traj.addFirst(next);
                time += dtime;
                timeFrame.addFirst(time);
                next = stage.instant(time);

                if (((AccelBox) stage).collision(next))
                    next = Particle.NaN();
                if (traj.getFirst().equals(next))
                    throw new Exception("No Particle Movement");


            }
            /** check if it is inside of valid regen **/
            this.stopGap = traj.getFirst();
            // get the difference between the hole and the position of the particle in the box
            Cord pos = traj.get(0).getPosition().add(new Cord(this.centerX, 0, this.centerZ).neg());
            if (pos.len() < this.radius) {


                // compute the Filter set
                time = 0;
                chamber.init(this.stopGap);
                next = chamber.instant(time);
                while (!next.equals(nan)) {
                    traj.add(next);
                    time += dtime;
                    timeFrame.add(timeFrame.getFirst()+dtime);
                    next = chamber.instant(time);
                    if (traj.getFirst().equals(next))
                        throw new Exception("No Particle Movement");
                }

            }

            // haft to convert from time lll to array
            timeFrame.toArray(new Double[timeFrame.size()]);
            double[] convert = new double[timeFrame.size()];
            for (int i = 0; i < timeFrame.size(); i++) {
                convert[i]= timeFrame.get(i);
            }

            return new HoldData( convert,
                                traj.toArray(new Particle[traj.size()]));

        }
    }



    public Coord3d [] runSimple() throws Initialised {
        numSteps = (int)Math.floor(timeEnd/dtime);
        double[] time = new double[numSteps];
        for (int i = 0 ; i < numSteps; ++i){
            time[i] = dtime *(double)i;
        }

        return toCoords(run(time));
    }
    public Coord3d[]  runChamber() throws Exception {
        Particle nan = Particle.NaN();
        double time = 0;
        LinkedList <Particle> traj = new LinkedList<Particle>();
        Particle next  = this.intial;
        this.stage.setFleck(this.intial);

        // run though accelerator
        while (!next.equals(nan)) {
            traj.addFirst(next);
            time += dtime;
            next  = stage.instant(time);

            if (((AccelBox)stage).collision(next))
                next = Particle.NaN();
            if (traj.getFirst().equals(next))
                throw new Exception("No Particle Movement");


        }
        /** check if it is inside of valid regen **/
        this.stopGap = traj.getFirst();
        // get the difference between the hole and the position of the particle in the box
        Cord pos = traj.get(0).getPosition().add(new Cord(this.centerX, 0, this.centerZ).neg());
        if ( pos.len() < this.radius ) {


            // compute the Filter set
            time = 0;
            chamber.init(this.stopGap);
            next = chamber.instant(time);
            while ( !next.equals(nan)){
                traj.add(next);
                time += dtime;
                next = chamber.instant(time);
                if (traj.getFirst().equals(next))
                    throw new Exception("No Particle Movement");
            }

        }



        Particle [] test = traj.toArray( new Particle [traj.size()]);
        return toCoords(test);

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
            if (preAcc) {
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
            if (! arr[i].equals(nan)) {
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

    /**
     * @param traj
     *
     * @return
     */
    public Coord3d [] toCoords (Particle [] traj){
        Coord3d [] cTraj = new Coord3d[traj.length];
        for (int i = 0 ; i < traj.length; ++i){
            cTraj[i] = traj[i].pathCord();
        }

        return cTraj;
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
            this.preAcc = true;
            return true;
        }
        else if (this.chamber.getClass() == WeinFilter.class) { // Need to witch
            this.chamber = new WeinChamber(this.chamber,radius,length);
            this.preAcc = true;
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
            this.preAcc = false;
            return true;
        }
        else if ( stage.getClass()== AccelBox.class )    // Need to change
        {
            this.stage = new VoltAccel( this.stage);
            this.preAcc = false;
            return true;
        }
        else if (stage.getClass() == VoltAccel.class)     // no need to change
        {
            this.preAcc = false;
            return true;
        }
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
        this.centerZ = centerY;
        this.radius = rad;
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

    public double getCenterZ() {
        return centerZ;
    }

    public void setCenterZ(double centerZ) {
        this.centerZ = centerZ;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Particle getIntial() {
        return intial;
    }

    public void setIntial(Particle intial) {
        this.intial = intial;
    }


    public void setAccelBox(double vi) {

        float v= (float)vi;
        ((AccelBox)this.stage).setX(new Range(-v, v));
        ((AccelBox)this.stage).setY(new Range(-v, v));
        ((AccelBox)this.stage).setZ(new Range(-v, v));
    }

    public void setChamberRad(double v) {
        ((WeinChamber)this.chamber).setRadius( v);
    }

    public void setChamberLen(double v) {
        ((WeinChamber)this.chamber).setLength( v);
    }

    public void setMag(double val) {
        this.chamber.setMagnetic(val);
    }
    public void setElectric( double val){
        this.chamber.setElectric(val);
    }

    public double getMag(){
        return this.chamber.getMagnetic();
    }
    public double getElectric(){
        return this.chamber.getElectric();
    }

    public double getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(double timeEnd) {
        this.timeEnd = timeEnd;
    }

    public double getDtime() {
        return dtime;
    }

    public void setDtime(double dtime) {
        this.dtime = dtime;
    }
}


