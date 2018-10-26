package org.texasewh.epionic;

import android.widget.TextView;

import java.util.ArrayList;

class StringAnalyzer {
    private boolean flaggingData;
    private String inputString;
    private String probeSplitter;
    private String subSplitter;
    private ArrayList<String> flaggedData;
    private double[] tempArray;
    private int tempCounter;

    public StringAnalyzer() {
        flaggingData = false;
        inputString = "";
        probeSplitter = "";
        subSplitter = "";
        flaggedData = new ArrayList<String>();
        tempCounter=0;
    }

    public StringAnalyzer(String inputStr, String probeSpli, String subSpli) {
        inputString = inputStr;
        probeSplitter = probeSpli;
        subSplitter = subSpli;
        flaggedData = new ArrayList<String>();
        double[] tempArray = new double[0];
        tempCounter=0;
    }

    public void startParse() {
        String[] theProbes = inputString.split(probeSplitter);
        String BPData = theProbes[0];
        String pulseOxData = theProbes[1];
        String ECGData = theProbes[2];
        String tempData = theProbes[3];
        double[] tempArray = new double[tempData.length()];
        //go through the data and first, loop to see if anything urgent
        //then go through and display one at a time for each data

        parseBP(BPData, subSplitter);
        parsePulseOx(pulseOxData, subSplitter);
        parseECGData(ECGData, subSplitter);
        parseTemp(tempData, subSplitter);
    }

    public void parseBP(String BPData, String subSplitter) {
    }

    public void parsePulseOx(String pulseOxData, String subSplitter) {
    }

    public void parseECGData(String ECGData, String subSplitter) {
    }

    public void parseTemp(String tempData, String subSplitter) {
        String[] stringTemps = tempData.split(subSplitter);
        double[] realTemps = new double[stringTemps.length];
        for (int i = 0; i < stringTemps.length; i++) {
            double tempNow = Double.parseDouble(stringTemps[i]);
            if (tempNow > 100 || tempNow < 90) {
                flagData("Temperature");
                //Should also store time of last flagged data to compare so I can unflag eventually
            }


            if (flaggingData) {
                flaggedData.add("BADTemp#" + i + "#" + tempNow);
                //add other info to this as well
            }

            realTemps[i] = tempNow;
            tempArray = realTemps;
            //Display the realTemps[i] somewhere, upon some interval
        }
    }

    public void flagData(String dataType) {
        flaggingData = true;
        //do something specific to the DataType, whether ECG or temp, here
    }

    public void printTemps() {
        for (double i : tempArray) {
            System.out.println(i);
        }
    }

    public void displayTemps(TextView dataDisplay) {
        //for (int i = 0; i < tempArray.length; i++) {}
        //display it
        tempCounter = tempCounter%(tempArray.length);
        dataDisplay.setText("Reading " + tempCounter+ ": " + tempArray[tempCounter]);
        tempCounter++;

        //wait some time
            /* Look in to this later but not now -- might not be an MBP concern
            https://stackoverflow.com/questions/43382660/how-to-delay-within-a-single-method-while-not-delaying-the-whole-program-in-java
             */
        //Thread.sleep(5000);


    }
}