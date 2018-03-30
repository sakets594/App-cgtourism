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


public class OneFragment extends Fragment {
    TextView adr,phone,postal;
    int addr,ph,pstl;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_one, container, false);
        RelativeLayout rel=root.findViewById(R.id.rel);
         adr=rel.findViewById(R.id.textView2);
        phone=rel.findViewById(R.id.phone);
        postal=rel.findViewById(R.id.postal);
        get();
        set();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        get();
        set();
    }

    void get()
    {
        String id=MainActivity.order.trim().substring(2,MainActivity.order.length()-2);
         addr=MainActivity.RESOURCE.getIdentifier("x_"+id,"string",MainActivity.PACKAGE_NAME);
         ph=MainActivity.RESOURCE.getIdentifier("z_"+id,"string",MainActivity.PACKAGE_NAME);
         pstl=MainActivity.RESOURCE.getIdentifier("y_"+id,"string",MainActivity.PACKAGE_NAME);
    }
    void set()
    {
       adr.setText(addr);
        phone.setText(ph);
        postal.setText(pstl);
    }

}