package org.texasewh.epionic;

import android.widget.TextView;

class dataParser {
    String myDataType;
    String[] myDataArray;
    TextView t_changing;
    TextView bp_changing;
    TextView o_changing;

    public dataParser(String datatype, String[] dataArray, TextView tempView, TextView bpView, TextView poView) {
        myDataType = datatype;
        myDataArray = dataArray;
        t_changing = tempView;
        bp_changing = bpView;
        o_changing = poView;
    }

    public void displayParsedData() {
        switch (myDataType) {
            case "BP":
                parseBP();
                break;
            case "TP":
                parseTemp();
                break;
            //case "EC": parseECGData(data); break;
            case "PO":
                parsePulseOx();
                break;
            // default: status.append("The error is with parseTemp if this is reached");
        }

    }

    public void parseBP() {
        bp_changing.setText("Blood Pressure: ");
        for (int i = 0; i < myDataArray.length; i++) {
            if (i % 2 == 1) bp_changing.append("\n");
            bp_changing.append(myDataArray[i]);
            if (i < myDataArray.length - 1) bp_changing.append(", ");
        }
    }

    public void parseTemp() {
        t_changing.setText("Temperature: ");
        for (int i = 0; i < myDataArray.length; i++) {
            if (i % 2 == 1) t_changing.append("\n");
            t_changing.append(myDataArray[i]);
            if (i < myDataArray.length - 1) t_changing.append(", ");
        }
    }

    public void parsePulseOx() {
        o_changing.setText("Pulse Ox: ");
        for (int i = 0; i < myDataArray.length; i++) {
            if (i % 2 == 1) o_changing.append("\n");
            o_changing.append(myDataArray[i]);
            if (i < myDataArray.length - 1) o_changing.append(", ");
        }

    }

}
