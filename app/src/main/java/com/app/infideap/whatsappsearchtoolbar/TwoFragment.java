package com.app.infideap.whatsappsearchtoolbar;

/**
 * Created by dell on 8/3/18.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TwoFragment extends Fragment {
TextView t1,t2;
    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        get();
        set();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_two, container, false);
        RelativeLayout rel=root.findViewById(R.id.rel);
        t1=rel.findViewById(R.id.text1);
        t2=rel.findViewById(R.id.section_label);
        get();
        set();
        return root;
    }
  void get()
    {
        String id=MainActivity.order.trim().substring(2,MainActivity.order.length()-2);
        t2.setText(MainActivity.RESOURCE.getIdentifier("d_"+id,"string",MainActivity.PACKAGE_NAME));
        t1.setText("About "+MainActivity.title);

    }

    void set()
    {}

}