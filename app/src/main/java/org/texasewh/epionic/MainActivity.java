package org.texasewh.epionic;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    final Handler mHandler = new Handler();
    private final String DEVICE_ADDRESS="98:D3:71:FD:4C:B6";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    boolean deviceConnected=false;
    //Thread thread;
    byte buffer[];
    //int bufferPosition;
    boolean stopThread;
    int myGlob=0;
    // Graph initialization
    GraphView graph;
    LineGraphSeries<DataPoint> mSeries;
    double graph2LastXValue = 5d;
    Runnable mTimer1;
    Runnable mTimer2;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    //TextView status;
    // private TextView mTextMessage;
    private Button btnStringA;
    // int waitCounter=0;
    private boolean MainFragVis;
//    ArrayList <Integer> ourValues = new ArrayList<Integer>(){{
//        ourValues.add( (Integer) 0);
//    }};
    private ArrayList dataListParser;
    private Bundle mBundle;
    private FragmentMain fragmentMain;
    private String currentBP="0";
    private String currentPO="0";
    private String currentTP="0";
 /*   private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
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
//                    setTitle("Notes"); // sets title of action bar
//                    FragmentNotes fragmentNotes = new FragmentNotes();
//                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction2.replace(R.id.fram, fragmentNotes, "Fragment Name"); //fram is layout id in activity_main.xml
//                    fragmentTransaction2.commit();
//                    return true;
                    Intent intent = new Intent(getBaseContext(), NotesActivity.class);
                    startActivity(intent);

                case R.id.navigation_settings:
                    MainFragVis = false;
                    setTitle("SettingsActivity"); // sets title of action bar
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
    };*/
    private int x = 0;
    private String bp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
       // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        NavigationView navigationView= findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mBundle = new Bundle();
        mBundle.putString("edttext", "Hello this is Nikhil nikhil nikhil 737238789");
        mBundle.putString("POString", currentPO);
        mBundle.putString("TPString", currentTP);
        mBundle.putString("BPString", currentBP);


        // Displays main fragment on app launch
        setTitle("Epionic"); // sets title of action bar
         fragmentMain = new FragmentMain();
         fragmentMain.setArguments(mBundle); // Solid! This is what should work
         mBundle.putString("dataInput", "");
         fragmentMain.setArguments(mBundle);

       // Calls begins data transfer on start up
       // onClickStart();

        // Graph stuff
        graph = (GraphView) findViewById(R.id.graph);
        mSeries = new LineGraphSeries<>();

        if (mSeries != null && graph != null) {
            graph.addSeries(mSeries);
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(20);
        }
    }


  /*  public boolean BTinit()
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
    void beginListenForData() {
        //status.append("start beginListen method");
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];

        Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopThread) {
                    try {
                        int byteCount = inputStream.available();

                        if(byteCount > 1) {
                            final byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);
                            final String string=new String(rawBytes,"UTF-8");

                            handler.post(new Runnable() {
                                public void run() {
//
                                   myGlob = myGlob+1;

                                    if (MainFragVis) {
                                        fragmentMain = new FragmentMain();

//                                        if (string.length() == 9) {
//                                            String[] dataInput = string.split("#");
//                                            //dataParser myParser = new dataParser(dataInput[0], dataInput[1].split(","));
//                                            mBundle.putString("dataInput", string);
//                                            mBundle.putString("POString", currentPO);
//                                            mBundle.putString("TPString", currentTP);
//                                            mBundle.putString("BPString", currentBP);
//                                            //myGlob = Integer.parseInt(currentPO.substring(1,2));
//
////                                                    for (String i: dataInput[1].split(",")){
////                                                        ourValues.add(Integer.parseInt(i));
////                                                    }
//
//
//                                            fragmentMain.setArguments(mBundle);
//                                            if (MainFragVis) {
//                                                //dataListParser = myParser.displayParsedData();
//                                                //displayData();
//                                            }
//
//                                        } else {
//                                            mBundle.putString("dataInput", "");
//                                            fragmentMain.setArguments(mBundle);
//                                        }

                                        fragmentMain.setArguments(mBundle);


                                        // display stuff
                                        String dataArray[] = deserialize(rawBytes);

                                        switch (dataArray[0]) {
                                            case "BP":
                                                if (bp == "") {
                                                    bp = dataArray[1];
                                                } else {
                                                    bp += "/" + dataArray[1];
                                                    mBundle.putString("BPString", bp);
                                                    // graph test
                                                    mSeries.appendData(new DataPoint(x, Integer.parseInt(dataArray[1])), true, 1000);
                                                    x++;
                                                    bp = "";
                                                }
                                                break;
                                            case "ECG":
                                                // graph stuff
                                                break;
                                            case "PO":
                                                mBundle.putString("POString", dataArray[1]);
                                                break;
                                            case "Temp":
                                                mBundle.putString("TPString", dataArray[1]);
                                                break;
                                        }

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
    }*/

    // Need to fix bug of elements 0 and 1 displaying twice
    private void displayDataInTextView(TextView textView, ArrayList arrayList){
        textView.append("");
        //textView.setText("Blood Pressure: ");
        for (int i = 0; i < arrayList.size(); i++) {
            textView.append( (String) arrayList.get(i) );
            if (i < arrayList.size() - 1) textView.append(", ");
        }

    }


    // Code for graph
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
//                if (ourValues.size()>=1) {
//                    for (int i : ourValues) {
//                        mSeries.appendData(new DataPoint(graph2LastXValue, i), true, 40);
//                    }
//                }
                //mSeries.appendData(new DataPoint(graph2LastXValue, myGlob), true, 40);
//                for (Object i: ourValues) {
//                    mSeries.appendData(new DataPoint(graph2LastXValue, (Integer) i), true, 40);
//                    graph2LastXValue += 1d;
//                }
                mHandler.postDelayed(this, 200);
            }
        };
        mHandler.postDelayed(mTimer2, 1000);
    }

    @Override
    public void onPause() {
        mHandler.removeCallbacks(mTimer1);
        mHandler.removeCallbacks(mTimer2);
        super.onPause();
    }

    private DataPoint[] generateGraph() {
        int count = 3;
        DataPoint[] points = new DataPoint[count];

//        for (int i = 0; i < count; i++) {
//            DataPoint point = new DataPoint(i, ourValues.get(i));
//            points[i] = point;
//        }
//        return points;
        return null;
    }

    // code below for serial communication
/*
  Serial Protocol (16 bits) for receiving data
  max value = 2^11 - 1 = 2047

  bit 15 (MSB) = start bit = 0;
  bits 14-13 = data type (BP = 00; ECG = 01; PO = 10; temp = 11)
  bits 12-2 = data * 10 (to preserve 1 decimal place)
  bit 1 = odd parity bit (gets set to either 0 or 1 to always make serialized data an odd amount of ones; used for error checking)
  bit 0 (LSB) = stop bit = 1

  returns dataArray [type, data] (e.g. [BP, ##])
*/
    String[] deserialize(byte serialBuffer[]) {
        String[] dataArray = new String[2];
        int twosComp = 0;
        int serialData = 0;

        // parse serial buffer into a single int
//        for (int i = 0; i < 2; i++) {
//            // bit shift stuff over when necessary
//            serialData |= serialBuffer[i] << (8 - (8 * i));
//        }

        // place first element of serialBuffer into serialData bits 15-8
        serialData = serialBuffer[0] << 8;

        // check if second element of serialBuffer is negative and perform 2's complement
        if (serialBuffer[1] < 0) {
            twosComp = serialBuffer[1] + 256;
        } else {
            twosComp = serialBuffer[1];
        }

        // combine both elements of serialBuffer together
        serialData += twosComp;

//        System.out.println(Arrays.toString(serialBuffer));
//        System.out.println(serialData);

        // ensures that start bit is detected, serialData passes odd parity check, and stop bit is detected
        if (((serialData & 0x8000) == 0) && isDataOdd(serialData) && ((serialData & 0x0001) == 1)) {
            // isolate bits 14-13 to determine data type
            int dataType = (serialData & 0x6000) >> 13;
            // initialize to null string
            String type = "";
            // isolate bits 12-2 to determine data
            int data = (serialData & 0x1FFC) >> 2;

            switch (dataType) {
                // BP
                case 0b00:
                    type = "BP";
                    break;

                // ECG
                case 0b01:
                    type = "ECG";
                    break;

                // PO
                case 0b10:
                    type = "PO";
                    break;

                // temp
                case 0b11:
                    type = "Temp";
                    break;
            }

            dataArray[0] = type;
            dataArray[1] = Integer.toString(data);

            return dataArray;
        } else {
            dataArray[0] = "error";
            dataArray[1] = Integer.toString((serialData & 0x1FFC) >> 2);
            System.out.println("error: " + Integer.toString((serialData & 0x1FFC) >> 2));
            return dataArray;
        }
    }

    // returns false if even or true if odd number of ones appear in serial data
    // data is corrupted if this returns false
    boolean isDataOdd(int serialData) {
        int onesCounter = 0;

        for (int i = 0; i < 16; i++) {
            onesCounter += (serialData >> i) & 0x0001;
        }

        return (onesCounter % 2 != 0);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            startActivity(new Intent(MainActivity.this, MainActivity.class));

        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));

        } else if (id == R.id.nav_notes) {
            startActivity(new Intent(MainActivity.this, NotesActivity.class));

        } else if (id == R.id.nav_patientInfo) {
            startActivity(new Intent(MainActivity.this, PatientInfoActivity.class));

        } else if (id == R.id.nav_adinfo) {
            startActivity(new Intent(MainActivity.this, AdInfoActivity.class));

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
