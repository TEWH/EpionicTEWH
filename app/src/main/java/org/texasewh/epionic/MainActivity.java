package org.texasewh.epionic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_notes:
                    mTextMessage.setText(R.string.title_notes);
                    return true;
                case R.id.navigation_settings:
                    mTextMessage.setText(R.string.title_settings);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        dataDisplay = findViewById(R.id.test);

        firstAnalyzer.startParse();

        btnStringA = findViewById(R.id.btn_stringTest);
        btnStringA.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               firstAnalyzer.displayTemps(dataDisplay);


           }
       });
    }


}
