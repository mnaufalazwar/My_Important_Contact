package com.naufal.myimportantcontact;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainContactListFrag extends Fragment {

    View view;
//    RecyclerView recyclerView;
//    RecyclerView.LayoutManager layoutManager;
//    RecyclerView.Adapter adapter;

    public MainContactListFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_contact_list, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        recyclerView = view.findViewById(R.id.mainContactList);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(this.getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//
//        adapter = new PersonAdapter(this.getActivity(), ApplicationClass.people);
//
//        recyclerView.setAdapter(adapter);

    }

//    public void notifyDataChanged(){
//        adapter.notifyDataSetChanged();
//    }

}
