package com.example.a25908.partybuild.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.a25908.partybuild.Activitys.AboutsActivity;
import com.example.a25908.partybuild.Activitys.MyFilesActivity;
import com.example.a25908.partybuild.Activitys.MydataActivity;
import com.example.a25908.partybuild.Activitys.OpinionActivity;
import com.example.a25908.partybuild.R;
/**
 * @author yusi
 * 个人中心
 */
public class Fragment4 extends Fragment {
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_fragment4, container, false);
        show();
        return v;
    }
    public void show(){
        RelativeLayout my1= (RelativeLayout) v.findViewById(R.id.my1);
        RelativeLayout my2= (RelativeLayout) v.findViewById(R.id.my2);
        RelativeLayout my3= (RelativeLayout) v.findViewById(R.id.my3);
        RelativeLayout my4= (RelativeLayout) v.findViewById(R.id.my4);
        RelativeLayout my5= (RelativeLayout) v.findViewById(R.id.my5);
        my1.setOnClickListener(listener);
        my2.setOnClickListener(listener);
        my3.setOnClickListener(listener);
        my4.setOnClickListener(listener);
        my5.setOnClickListener(listener);
    }
    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.my1:
                    startActivity(new Intent(getActivity(), MydataActivity.class));
                    break;
                case R.id.my2:
                    startActivity(new Intent(getActivity(), MyFilesActivity.class));
                    break;
                case R.id.my3:
                    break;
                case R.id.my4:
                    startActivity(new Intent(getActivity(), AboutsActivity.class));
                    break;
                case R.id.my5:
                    startActivity(new Intent(getActivity(), OpinionActivity.class));
                    break;
            }
        }
    };
}
