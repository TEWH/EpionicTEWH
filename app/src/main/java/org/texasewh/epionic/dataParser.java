package org.texasewh.epionic;

import android.widget.TextView;

import java.util.ArrayList;

class dataParser {
    String myDataType;
    String[] myDataArray;
    TextView t_changing;
    TextView bp_changing;
    TextView o_changing;
    ArrayList  data_List = new ArrayList<Double>();

    public dataParser(String datatype, String[] dataArray){
        myDataType = datatype;
        myDataArray = dataArray;
    }

    public dataParser(String datatype, String[] dataArray, TextView tempView, TextView bpView, TextView poView) {
        myDataType = datatype;
        myDataArray = dataArray;
        t_changing = tempView;
        bp_changing = bpView;
        o_changing = poView;
    }

    public ArrayList displayParsedData() {
        switch (myDataType) {
            case "BP":
                //parseBP();
                data_List.add(0,"1");
                parseData(data_List);
                break;
            case "TP":
                //parseTemp();
                data_List.add(0,"2");
                parseData(data_List);
                break;
            //case "EC": parseECGData(data); break;
            case "PO":
                data_List.add(0,"3");
                parseData(data_List);
                break;
            // default: status.append("The error is with parseTemp if this is reached");
        }
    return data_List;
    }

    public void parseBP() {
        bp_changing.setText("Blood Pressure: ");
        for (int i = 0; i < myDataArray.length; i++) {
            //if (i % 2 == 1) bp_changing.append("\n");
            //bp_changing.append(myDataArray[i]);
            //if (i < myDataArray.length - 1) bp_changing.append(", ");
           // bp_List.add(myDataArray[i]);
            //if (i < myDataArray.length - 1) bp_List.add(myDataArray[i]);
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

    public ArrayList parseData(ArrayList data_List){
        for (int i = 0; i < myDataArray.length; i++) {
            data_List.add(myDataArray[i]);
            //if (i < myDataArray.length - 1) data_List.add(myDataArray[i]);
        }
        return data_List;
    }

}
