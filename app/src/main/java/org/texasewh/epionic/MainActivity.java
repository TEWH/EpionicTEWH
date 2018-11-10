package org.texasewh.epionic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button btnStringA;
    public  TextView dataDisplay;
    private String inputString = "45,23#23,43#99,99#00,11,22,33,44,55,66,77,88,99,1010";
    private String probeString = "#";
    private String subSplitter = ",";
    private StringAnalyzer firstAnalyzer = new StringAnalyzer(inputString, probeString, subSplitter);
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("Epionic"); // sets title of action bar
                    FragmentMain fragmentMain = new FragmentMain();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.fram, fragmentMain, "Fragment Name"); //fram is layout id in activity_main.xml
                    fragmentTransaction1.commit();
                    return true;
                case R.id.navigation_notes:
                    setTitle("Fragment Notes"); // sets title of action bar
                    FragmentNotes fragmentNotes = new FragmentNotes();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.fram, fragmentNotes, "Fragment Name"); //fram is layout id in activity_main.xml
                    fragmentTransaction2.commit();
                    return true;
                case R.id.navigation_settings:
                    setTitle("Settings"); // sets title of action bar
                    FragmentSettings fragmentSettings = new FragmentSettings();
                    FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.fram, fragmentSettings, "Fragment Name"); //fram is layout id in activity_main.xml
                    fragmentTransaction3.commit();
                    return true;
                case R.id.navigation_data:
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

        // Displays main fragment on app launch
        setTitle("Fragment Epionic"); // sets title of action bar
        FragmentMain fragmentMain = new FragmentMain();
        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.fram, fragmentMain, "Fragment Name"); //fram is layout id in activity_main.xml
        fragmentTransaction1.commit();

//        dataDisplay = findViewById(R.id.test);
//
//        firstAnalyzer.startParse();


//        btnStringA = findViewById(R.id.btn_stringTest);
//        btnStringA.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//
//
//               firstAnalyzer.displayTemps(dataDisplay);
//
//
//           }
//       });
    }


}
