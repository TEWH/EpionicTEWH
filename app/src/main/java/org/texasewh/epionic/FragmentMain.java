package org.texasewh.epionic;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMain extends Fragment {


    public FragmentMain() {
        // Required empty public constructor
    }

    private final String DEVICE_ADDRESS = "98:D3:71:FD:4C:B6";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    boolean deviceConnected = false;
    //Thread thread;
    byte buffer[];
    //int bufferPosition;
    boolean stopThread;

    TextView status;
    private TextView mTextMessage;
    private Button btnStringA;
    //String holder = "";
    int waitCounter = 0;
   String newDataInput="nothing rn";
   String receivedPO = "nothing rn";
   String receivedBP = "nothing rn";
   String receivedTP = "nothing rn";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_fragment_main2, container, false);
        // Change to fragment_fragment_main2 for better UI on tablet
        Bundle arguments = getArguments();
        if (arguments != null) {
             newDataInput = getArguments().getString("dataInput");

            receivedPO = getArguments().getString("POString");
            receivedBP = getArguments().getString("BPString");
            receivedTP = getArguments().getString("TPString");
        }

       // String strtext = getArguments().getString("edttext");
        status = view1.findViewById(R.id.statusmessage);
        status.setText(newDataInput);
        //return inflater.inflate(R.layout.fragment_fragment_main, container, false);


        //onClickStart();

        /*bluetooth methods here*/
        //setUiEnabled(false);

        /*bluetooth methods above*/
        /*firstAnalyzer.startParse();*/
        final TextView oxygenField1 = view1.findViewById(R.id.o_changing);
        final TextView bpField1 = view1.findViewById(R.id.bp_changing);
        final TextView tpField1 = view1.findViewById(R.id.t_changing);


        //I NEED TO COMMENT OUT THE BELOW METHODS /UNTIL I SEND THE DATA ON display STRINGS FROM THE ACTIVITY TO THE FRAGMENT

        oxygenField1.setText("Oxygen Level: " + receivedPO + " [frag]");
        bpField1.setText("Blood Pressure: " + receivedBP + " [frag]");
        tpField1.setText("Temperature: " +receivedTP + " [frag]");

      /*  oxygenField1.setText("Oxygen Level: " + receivedPO + " [this is inside fragment not activity]");
        bpField1.setText("Blood Pressure: " + receivedBP + " [this is inside fragment not activity]");
        tpField1.setText("Temperature: " +receivedTP + " [this is inside fragment not activity]");*/

        //ABOVE IS IMPT MAYBE

    //   Code for button to start data transfer, see not in main activity
        // MainActivity activity = this.super();
        btnStringA = view1.findViewById(R.id.start_button); //(TextView) findViewById(R.id.t_changing); // click temp button for it to work //findViewById(R.id.btn_stringTest);
        btnStringA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activity.onClickStart();
            }
        });
        return view1;
    }
}

