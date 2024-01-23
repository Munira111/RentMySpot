package com.example.rentmyspot;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.Serializable;

//
public class profileFragment extends Fragment implements Serializable {
    Button add,delete,logout,rents,returnS;
//    DBHelper DBHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//    1
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        2
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        3
        add = view.findViewById(R.id.add);
        delete = view.findViewById(R.id.delete);
        logout = view.findViewById(R.id.logout);
        rents=view.findViewById(R.id.rents);
        returnS=view.findViewById(R.id.returns);
//        DBHelper = new DBHelper(getActivity());


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = (String) getActivity().getIntent().getSerializableExtra("username");
                Intent intent = new Intent(getActivity(), addActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = (String) getActivity().getIntent().getSerializableExtra("username");
                Intent intent = new Intent(getActivity(), deleteActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        rents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = (String) getActivity().getIntent().getSerializableExtra("username");
                Intent intent = new Intent(getActivity(), Rent2Activity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        returnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = (String) getActivity().getIntent().getSerializableExtra("username");
                Intent intent = new Intent(getActivity(), Return2Activity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });










    }
}