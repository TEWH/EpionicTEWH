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

    private final String DEVICE_ADDRESS="98:D3:71:FD:4C:B6";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    boolean deviceConnected=false;
    //Thread thread;
    byte buffer[];
    //int bufferPosition;
    boolean stopThread;

    TextView status;
    private TextView mTextMessage;
    private Button btnStringA;
    //String holder = "";
    int waitCounter=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fragment_data, container, false);
        View view1 = inflater.inflate(R.layout.fragment_fragment_main, container, false);

        status = view1.findViewById(R.id.statusmessage);

        //onClickStart();

        /*bluetooth methods here*/
        //setUiEnabled(false);

        /*bluetooth methods above*/
        /*firstAnalyzer.startParse();*/
        final TextView oxygenField1 = view1.findViewById(R.id.o_changing);
        final TextView bpField1 = view1.findViewById(R.id.bp_changing);
        final TextView tpField1 = view1.findViewById(R.id.t_changing);
        btnStringA = view1.findViewById(R.id.start_button); //(TextView) findViewById(R.id.t_changing); // click temp button for it to work //findViewById(R.id.btn_stringTest);
        btnStringA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStart(oxygenField1, bpField1, tpField1);
            }
        });
        return view1;
    }


    public boolean BTinit()
    {
        // status.append("entered BTinit");
        boolean found=false;
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        //  status.append("test");
        if (bluetoothAdapter == null) {
            //Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();
            // status.append("bluetooth not supported");
        }
        if(!bluetoothAdapter.isEnabled())
        {
            //status.append("bluetooth adapter not enabled");
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if(bondedDevices.isEmpty())
        {
            //Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();
            //status.append("pair device");

        }
        else
        {
            for (BluetoothDevice iterator : bondedDevices)
            {
                if(iterator.getAddress().equals(DEVICE_ADDRESS))
                {
                    // status.append("checked address");
                    device=iterator;
                    found=true;
                    break;
                }
            }
        }
        return found;
    }

    public boolean BTconnect()
    {
        status.append(" entered bTconnect");
        boolean connected=true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected=false;
        }
        if(connected)
        {
            try {
                outputStream=socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream=socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return connected;
    }
    public void onClickStart(TextView text1, TextView text2, TextView text3) {
        status.append("clicked on start");
        if(BTinit())
        {
            if(BTconnect())
            {
                // setUiEnabled(true);
                deviceConnected=true;
                beginListenForData(text1, text2, text3);
                status.append("\nConnection Opened!\n");
            }

        }
    }
    void beginListenForData(final TextView oxygenField, final TextView bpField, final TextView tpField)
    {
        status.append("start beginListen method");
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];

        Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopThread)
                {
                    try
                    {
                        int byteCount = inputStream.available();
                        if(byteCount > 0)
                        {
                            byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);
                            final String string=new String(rawBytes,"UTF-8");

                            handler.post(new Runnable() {
                                public void run()
                                {
//                                    TextView oxygenField = findViewById(R.id.o_changing);
//                                    TextView bpField = findViewById(R.id.bp_changing);
//                                    TextView tpField = findViewById(R.id.t_changing);

                                    if (string.length()==9){
                                        String [] dataInput = string.split("#");
                                        dataParser myParser = new dataParser(dataInput[0], dataInput[1].split(","), tpField, bpField, oxygenField);
                                        myParser.displayParsedData();
                                    }
                                }

                            });

                        }
                    }
                    catch (IOException ex)
                    {
                        stopThread = true;
                    }
                }
            }
        });

        thread.start();
    }

}


