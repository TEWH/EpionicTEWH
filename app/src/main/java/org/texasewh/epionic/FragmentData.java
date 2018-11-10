package org.texasewh.epionic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentData extends Fragment {


    public FragmentData() {
        // Required empty public constructor
    }

    private TextView mTextMessage;
    private Button btnStringA;
    public  TextView dataDisplay;
    private String inputString = "45,23#23,43#99,99#00,11,22,33,44,55,66,77,88,99,1010";
    private String probeString = "#";
    private String subSplitter = ",";
    private StringAnalyzer firstAnalyzer = new StringAnalyzer(inputString, probeString, subSplitter);



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fragment_data, container, false);
        View view = inflater.inflate(R.layout.fragment_fragment_data, container, false);

        dataDisplay = view.findViewById(R.id.test);

        firstAnalyzer.startParse();
        btnStringA = view.findViewById(R.id.btn_stringTest);
        btnStringA.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               firstAnalyzer.displayTemps(dataDisplay);
               }
        });

        return inflater.inflate(R.layout.fragment_fragment_data, container, false);
    }

}
