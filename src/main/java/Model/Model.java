package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by llama on 3/15/16.
 */
public class Model {

    private int[] box;      // box dimentions (depth, width, hights) (x,y,z)
    private int[] opening;  // size and position of the opening from the
                            //      voltage accelerator to the filter.
    private int size;

    private VoltAccel stage1;
    private WeinFilter stage2;




    public void saveFile(File saveTo) throws IOException {

        FileWriter writer = new FileWriter(saveTo);


        // Number of rows
        for (int i = 0 ; i < size; ++i) {

            // number of columns in set in this function
            writer = writeLine(writer);
        }


    }

    private FileWriter writeLine(FileWriter writer) throws IOException {
        String dlim = "\t";
        String endL = "\n";

        // this is the number of columns
        writer.append("1"+dlim);
        writer.append("2"+dlim);
        writer.append("3"+dlim);
        writer.append("4"+dlim);

        writer.append(endL);

        return writer;
    }

    /** TODO add the write the volageAcc and te Wien Filter
     *
     */



}
