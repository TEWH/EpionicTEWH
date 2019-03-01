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

import com.jjoe64.graphview.*;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.util.Random;


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
    Button notesBtn;
    GraphView graph;
    LineGraphSeries<DataPoint> mSeries;
    final Handler mHandler = new Handler();
    double graph2LastXValue = 5d;
    Runnable mTimer1;
    Runnable mTimer2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_fragment_main, container, false);
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

        notesBtn = view1.findViewById(R.id.btn_Notes);
        notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NotesActivity.class);
                startActivity(intent);
            }
        });

        // Code for graph
        graph = (GraphView) view1.findViewById(R.id.graph);
        mSeries = new LineGraphSeries<>();
        if (mSeries != null && graph != null) {
            graph.addSeries(mSeries);
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(40);
        }
        return view1;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimer1 = new Runnable() {
            @Override
            public void run() {
                //mSeries1.resetData(generateData());
                mHandler.postDelayed(this, 300);
            }
        };
        mHandler.postDelayed(mTimer1, 300);

        mTimer2 = new Runnable() {
            @Override
            public void run() {
                graph2LastXValue += 1d;
                mSeries.appendData(new DataPoint(graph2LastXValue, getRandom()), true, 40);
                mHandler.postDelayed(this, 200);
            }
        };
        mHandler.postDelayed(mTimer2, 1000);
    }

    @Override
    public void onPause() {
        // graph only works when arduino doesn't send data
    //        int delay = 1000000000;
    //
    //        while (delay > 0) {
    //            delay--;
    //
            mHandler.removeCallbacks(mTimer1);
            mHandler.removeCallbacks(mTimer2);
            super.onPause();
    }

    private DataPoint[] generateData() {
        int count = 30;
        DataPoint[] values = new DataPoint[count];
        for (int i=0; i<count; i++) {
            double x = i;
            double f = mRand.nextDouble()*0.15+0.3;
            double y = Math.sin(i*f+2) + mRand.nextDouble()*0.3;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }

    double mLastRandom = 2;
    Random mRand = new Random();
    private double getRandom() {
        return mLastRandom += mRand.nextDouble()*0.5 - 0.25;
    }
}