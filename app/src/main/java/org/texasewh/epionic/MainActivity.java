package org.texasewh.epionic;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import android.app.Fragment;

public class MainActivity extends AppCompatActivity {
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

    //TextView status;
   // private TextView mTextMessage;
    private Button btnStringA;
   // int waitCounter=0;
    private boolean MainFragVis;
    private ArrayList dataListParser;
    private Bundle mBundle;
    private FragmentMain fragmentMain;
    private String currentBP="0";
    private String currentPO="0";
    private String currentTP="0";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    MainFragVis =true;
                    setTitle("Epionic"); // sets title of action bar
                    FragmentMain fragmentMain = new FragmentMain();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.fram, fragmentMain, "Fragment Name"); //fram is layout id in activity_main.xml
                    fragmentTransaction1.commit();
                    return true;
                case R.id.navigation_notes:
                    MainFragVis = false;
                    setTitle("Notes"); // sets title of action bar
                    FragmentNotes fragmentNotes = new FragmentNotes();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.fram, fragmentNotes, "Fragment Name"); //fram is layout id in activity_main.xml
                    fragmentTransaction2.commit();
                    return true;
                case R.id.navigation_settings:
                    MainFragVis = false;
                    setTitle("Settings"); // sets title of action bar
                    FragmentSettings fragmentSettings = new FragmentSettings();
                    FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.fram, fragmentSettings, "Fragment Name"); //fram is layout id in activity_main.xml
                    fragmentTransaction3.commit();
                    return true;
                case R.id.navigation_data:
                    MainFragVis = false;
                    setTitle("Data Transfer"); // sets title of action bar
                    FragmentData fragmentData = new FragmentData();
                    FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction4.replace(R.id.fram, fragmentData, "Fragment Name"); //fram is layout id in activity_main.xml
                    fragmentTransaction4.commit();
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mBundle = new Bundle();
        mBundle.putString("edttext", "Hello this is Nikhil nikhil nikhil 737238789");
        mBundle.putString("POString", currentPO);
        mBundle.putString("TPString", currentTP);
        mBundle.putString("BPString", currentBP);
      //  ArrayList NikhilArray = new ArrayList<String>();
       // NikhilArray.add("This is NikhilArray :)");
       // mBundle.putParcelable("myObject", NikhilArray );
        //To do this with an object, you need to use bundle.putParcelable

        // set Fragmentclass Arguments
      //  Fragmentclass fragobj = new Fragmentclass();
       // fragobj.setArguments(bundle);

        // Displays main fragment on app launch
        setTitle("Epionic"); // sets title of action bar
       // fragmentMain = new FragmentMain();
       // fragmentMain.setArguments(mBundle); // Solid! This is what should work
      //  mBundle.putString("dataInput", "");
      //  fragmentMain.setArguments(mBundle);



        //Calls begins data transfer on start up
        onClickStart();

        /* Data transfer initialized on start up does appear to be causing delay to launch time
        I tried to solve this by adding back in the button like before however there is a slight issue
        now that the data transfer code all lives in the activity. When i put the onClickListener here
        I get the null pointer error since the button does not exhisit yet
        When i put it in the fragment and call the method by creating an instance of the main activity
        nothing happens
         */
        //code for button to start data transfer
//        btnStringA = findViewById(R.id.start_button); //(TextView) findViewById(R.id.t_changing); // click temp button for it to work //findViewById(R.id.btn_stringTest);
//        btnStringA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickStart();
//            }
//        });


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
        // status.append(" entered bTconnect");
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
    public void onClickStart() {
        //status.append("clicked on start");
        if(BTinit())
        {
            if(BTconnect())
            {
                //TextView status = findViewById(R.id.statusmessage);
                // setUiEnabled(true);
                deviceConnected=true;
                beginListenForData();
               // status.append("\nConnection Opened!\n");
            }

        }
    }
    void beginListenForData()
    {
        //status.append("start beginListen method");
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
                                public void run() {
//
                                    if (MainFragVis) {
                                        fragmentMain = new FragmentMain();

                                        if (string.length() == 9) {
                                            String[] dataInput = string.split("#");
                                            dataParser myParser = new dataParser(dataInput[0], dataInput[1].split(","));
                                            mBundle.putString("dataInput", string);
                                            mBundle.putString("POString", currentPO);
                                            mBundle.putString("TPString", currentTP);
                                            mBundle.putString("BPString", currentBP);
                                            fragmentMain.setArguments(mBundle);
                                            if (MainFragVis) {
                                                dataListParser = myParser.displayParsedData();
                                                displayData();
                                            }

                                        } else {
                                            mBundle.putString("dataInput", "");
                                            fragmentMain.setArguments(mBundle);
                                        }


                                        mBundle.putString("POString", currentPO);
                                        mBundle.putString("TPString", currentTP);
                                        mBundle.putString("BPString", currentBP);
                                        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction1.replace(R.id.fram, fragmentMain, "Fragment Name"); //fram is layout id in activity_main.xml
                                        fragmentTransaction1.commit();
                                        // MainFragVis = true;
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

    // Need to fix bug of elements 0 and 1 displaying twice
    private void displayDataInTextView(TextView textView, ArrayList arrayList){
        textView.append("");
        //textView.setText("Blood Pressure: ");
        for (int i = 0; i < arrayList.size(); i++) {
            textView.append( (String) arrayList.get(i) );
            if (i < arrayList.size() - 1) textView.append(", ");
         }

    }

    private void displayData() {

        String label = dataListParser.get(0).toString();
        dataListParser.remove(0); // Now the first element won't be the same
     /*   switch  (label) {
            case "1":
                String myStorer="";
               // for (String i: dataListParser){ myStorer = myStorer+ i; }
               // currentBP = myStorer;
                break;
            case "2":
                String myStorer1="";
               // for (String i: dataListParser){ myStorer1 = myStorer1+ i; }
             //   currentTP = myStorer1;
                break;
            case "3":
                String myStorer2="";
                //for (String i: dataListParser){ myStorer2 = myStorer2+ i; }
               // currentPO = myStorer2;
                break;
            }*/

        TextView textViewBP = findViewById(R.id.bp_changing);
        TextView textViewTP = findViewById(R.id.t_changing);
        TextView textViewO = findViewById(R.id.o_changing);

        switch  (label) {
            case "1":
                currentBP=dataListParser.toString();
              //  textViewBP.setText("Blood Pressure: " + currentBP);
              //  textViewTP.setText("Temperature: "+currentTP);
              //  textViewO.setText("Oxygen Level: "+currentPO);
                break;
            case "2":
                currentTP=dataListParser.toString();
              //  textViewBP.setText("Blood Pressure: " + currentBP);
              //  textViewTP.setText("Temperature: "+currentTP);
              //  textViewO.setText("Oxygen Level: "+currentPO);
                break;
            case "3":
                currentPO=dataListParser.toString();
              //  textViewBP.setText("Blood Pressure: " + currentBP);
              //  textViewTP.setText("Temperature: "+currentTP);
              //  textViewO.setText("Oxygen Level: "+currentPO);
                break;
        }

    }


}




