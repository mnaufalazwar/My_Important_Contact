package com.naufal.myimportantcontact;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PersonAdapter.ItemClick {

    //TRY TO EDIT AND COMMIT IN GIT

    //declare const
    final int DETAIL_ACTIVITY = 1;

    //declare view
    Button btnAdd, btnCancelAdd;
    TextView tvAddContact;
    EditText etName, etRelation, etNumber, etEmail, etAddress;

    //declare fragment
    FragmentManager fragmentManager;
    MainContactListFrag listFrag;

    //declare file name
    public static final String NAME_FILE = ApplicationClass.NAME_FILE;
    public static final String RELATION_FILE = ApplicationClass.RELATION_FILE;
    public static final String NUMBER_FILE = ApplicationClass.NUMBER_FILE;
    public static final String EMAIL_FILE = ApplicationClass.EMAIL_FILE;
    public static final String ADDRESS_FILE = ApplicationClass.ADDRESS_FILE;

    //declare list for read and save item
    ArrayList<String> saveName, saveRelation, saveNumber, saveEmail, saveAddress;
    ArrayList<String> readName, readRelation, readNumber, readEmail, readAddress;

    //declare gson
    Gson gson;

    //declare shared preference
    SharedPreferences prefName;
    SharedPreferences prefRelation;
    SharedPreferences prefNumber;
    SharedPreferences prefEmail;
    SharedPreferences prefAddress;

    //declare recycleview
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connect view to id
        tvAddContact = findViewById(R.id.tvAddContact);
        etName = findViewById(R.id.etName);
        etRelation = findViewById(R.id.etRelation);
        etNumber = findViewById(R.id.etNumber);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancelAdd = findViewById(R.id.btnCancelAdd);

        fragmentManager = this.getSupportFragmentManager();
        listFrag = (MainContactListFrag)fragmentManager.findFragmentById(R.id.contactListFrag);

        prefName = getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        prefRelation = getSharedPreferences(RELATION_FILE, MODE_PRIVATE);
        prefNumber = getSharedPreferences(NUMBER_FILE, MODE_PRIVATE);
        prefEmail = getSharedPreferences(EMAIL_FILE, MODE_PRIVATE);
        prefAddress = getSharedPreferences(ADDRESS_FILE, MODE_PRIVATE);

        saveName = new ArrayList<>();
        saveRelation = new ArrayList<>();
        saveNumber = new ArrayList<>();
        saveEmail = new ArrayList<>();
        saveAddress = new ArrayList<>();

        readName = new ArrayList<>();
        readRelation = new ArrayList<>();
        readNumber = new ArrayList<>();
        readEmail = new ArrayList<>();
        readAddress = new ArrayList<>();

        gson = new Gson();

        String getFName = prefName.getString("setFName", "");
        String getLName = prefRelation.getString("setLName", "");
        String getNumber = prefNumber.getString("setNumber", "");
        String getEmail = prefEmail.getString("setEmail", "");
        String getAddress = prefAddress.getString("setAddress", "");

        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        readName = gson.fromJson(getFName, type);
        readRelation = gson.fromJson(getLName, type);
        readNumber = gson.fromJson(getNumber, type);
        readEmail = gson.fromJson(getEmail, type);
        readAddress = gson.fromJson(getAddress, type);

        if(!getFName.isEmpty()){

            for(int i = 0 ; i < readName.size() ; i ++){

                saveName.add(readName.get(i));
                saveRelation.add(readRelation.get(i));
                saveNumber.add(readNumber.get(i));
                saveEmail.add(readEmail.get(i));
                saveAddress.add(readAddress.get(i));

            }
        }

        recyclerView = findViewById(R.id.mainContactList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PersonAdapter(this, ApplicationClass.people);
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etName.getText().toString().isEmpty()
                || etRelation.getText().toString().isEmpty()
                || etNumber.getText().toString().isEmpty()
                || etEmail.getText().toString().isEmpty()
                || etAddress.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                }
                else{
                    //String fullName = etFirstName.getText().toString().trim() + " " + etLastName.getText().toString().trim();

                    String fName = etName.getText().toString();
                    String lName = etRelation.getText().toString();
                    String number = etNumber.getText().toString();
                    String email = etEmail.getText().toString();
                    String address = etAddress.getText().toString();

                    ApplicationClass.people.add(new Person(
                            fName,
                            lName,
                            number,
                            email,
                            address));

                    //listFrag.notifyDataChanged();
                    adapter.notifyDataSetChanged();

                    SharedPreferences.Editor editorFirstName = prefName.edit();
                    SharedPreferences.Editor editorLastName = prefRelation.edit();
                    SharedPreferences.Editor editorNumber = prefNumber.edit();
                    SharedPreferences.Editor editorEmail = prefEmail.edit();
                    SharedPreferences.Editor editorAddress = prefAddress.edit();

                    saveName.add(fName);
                    saveRelation.add(lName);
                    saveNumber.add(number);
                    saveEmail.add(email);
                    saveAddress.add(address);

                    String jsonFName = gson.toJson(saveName);
                    String jsonLName = gson.toJson(saveRelation);
                    String jsonNumber = gson.toJson(saveNumber);
                    String jsonEmail = gson.toJson(saveEmail);
                    String jsonAddress = gson.toJson(saveAddress);

                    editorFirstName.putString("setFName", jsonFName);
                    editorFirstName.commit();
                    editorLastName.putString("setLName", jsonLName);
                    editorLastName.commit();
                    editorNumber.putString("setNumber", jsonNumber);
                    editorNumber.commit();
                    editorEmail.putString("setEmail", jsonEmail);
                    editorEmail.commit();
                    editorAddress.putString("setAddress", jsonAddress);
                    editorAddress.commit();

                    Toast.makeText(MainActivity.this, "Successfully Added!", Toast.LENGTH_SHORT).show();

                    etName.setText(null);
                    etRelation.setText(null);
                    etNumber.setText(null);
                    etEmail.setText(null);
                    etAddress.setText(null);

                    fragmentManager.beginTransaction()
                            .hide(fragmentManager.findFragmentById(R.id.addFrag))
                            .commit();

                }

            }
        });

        fragmentManager.beginTransaction()
                .hide(fragmentManager.findFragmentById(R.id.addFrag))
                .commit();

        tvAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction()
                        .show(fragmentManager.findFragmentById(R.id.addFrag))
                        .addToBackStack(null)
                        .commit();

            }
        });

        btnCancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction()
                        .hide(fragmentManager.findFragmentById(R.id.addFrag))
                        .commit();

            }
        });

    }

    @Override
    public void onItemClick(int index) {

        Intent intent;
        intent = new Intent(this, com.naufal.myimportantcontact.DetailActivity.class);
        intent.putExtra("index", index);
        startActivityForResult(intent, DETAIL_ACTIVITY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == DETAIL_ACTIVITY){

            if(resultCode == ApplicationClass.RESULT_CODE_DELETE){

                int toDelete = data.getIntExtra("index", 0);

                ApplicationClass.people.remove(toDelete);
                //listFrag.notifyDataChanged();
                adapter.notifyDataSetChanged();

                saveName.remove(toDelete);
                saveRelation.remove(toDelete);
                saveNumber.remove(toDelete);
                saveEmail.remove(toDelete);
                saveAddress.remove(toDelete);

                SharedPreferences.Editor editorFirstName = prefName.edit();
                SharedPreferences.Editor editorLastName = prefRelation.edit();
                SharedPreferences.Editor editorNumber = prefNumber.edit();
                SharedPreferences.Editor editorEmail = prefEmail.edit();
                SharedPreferences.Editor editorAddress = prefAddress.edit();

                String jsonFName = gson.toJson(saveName);
                String jsonLName = gson.toJson(saveRelation);
                String jsonNumber = gson.toJson(saveNumber);
                String jsonEmail = gson.toJson(saveEmail);
                String jsonAddress = gson.toJson(saveAddress);

                editorFirstName.putString("setFName", jsonFName);
                editorFirstName.commit();
                editorLastName.putString("setLName", jsonLName);
                editorLastName.commit();
                editorNumber.putString("setNumber", jsonNumber);
                editorNumber.commit();
                editorEmail.putString("setEmail", jsonEmail);
                editorEmail.commit();
                editorAddress.putString("setAddress", jsonAddress);
                editorAddress.commit();

            }

            if(resultCode == ApplicationClass.RESULT_CODE_EDIT) {

                int toEdit = data.getIntExtra("index", 0);
                String newName = data.getStringExtra("newName");
                String newRelation = data.getStringExtra("newRelation");
                String newNumber = data.getStringExtra("newNumber");
                String newEmail = data.getStringExtra("newEmail");
                String newAddress = data.getStringExtra("newAddress");

                ApplicationClass.people.set(toEdit, new Person(
                        newName,
                        newRelation,
                        newNumber,
                        newEmail,
                        newAddress
                ));

                //listFrag.notifyDataChanged();
                adapter.notifyDataSetChanged();

                saveName.set(toEdit, newName);
                saveRelation.set(toEdit, newRelation);
                saveNumber.set(toEdit, newNumber);
                saveEmail.set(toEdit, newEmail);
                saveAddress.set(toEdit, newAddress);

                SharedPreferences.Editor editorFirstName = prefName.edit();
                SharedPreferences.Editor editorLastName = prefRelation.edit();
                SharedPreferences.Editor editorNumber = prefNumber.edit();
                SharedPreferences.Editor editorEmail = prefEmail.edit();
                SharedPreferences.Editor editorAddress = prefAddress.edit();

                String jsonFName = gson.toJson(saveName);
                String jsonLName = gson.toJson(saveRelation);
                String jsonNumber = gson.toJson(saveNumber);
                String jsonEmail = gson.toJson(saveEmail);
                String jsonAddress = gson.toJson(saveAddress);

                editorFirstName.putString("setFName", jsonFName);
                editorFirstName.commit();
                editorLastName.putString("setLName", jsonLName);
                editorLastName.commit();
                editorNumber.putString("setNumber", jsonNumber);
                editorNumber.commit();
                editorEmail.putString("setEmail", jsonEmail);
                editorEmail.commit();
                editorAddress.putString("setAddress", jsonAddress);
                editorAddress.commit();

            }
        }
    }
}
