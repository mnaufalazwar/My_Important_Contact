package com.naufal.myimportantcontact;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ApplicationClass extends Application {

    public static ArrayList<Person> people;

    public static final String NAME_FILE = "com.naufal.myimportantcontact.NAME_FILE";
    public static final String RELATION_FILE = "com.naufal.myimportantcontact.RELATION_FILE";
    public static final String NUMBER_FILE = "com.naufal.myimportantcontact.NUMBER_FILE";
    public static final String EMAIL_FILE = "com.naufal.myimportantcontact.EMAIL_FILE";
    public static final String ADDRESS_FILE = "com.naufal.myimportantcontact.ADDRESS_FILE";

    public static final int RESULT_CODE_DELETE = 11;
    public static final int RESULT_CODE_EDIT = 12;

    SharedPreferences prefName;
    SharedPreferences prefRelation;
    SharedPreferences prefNumber;
    SharedPreferences prefEmail;
    SharedPreferences prefAddress;

    Gson gson;

    ArrayList<String> readName, readRelation, readNumber, readEmail, readAddress;

    @Override
    public void onCreate() {
        super.onCreate();

        people = new ArrayList<>();

        prefName = getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        prefRelation = getSharedPreferences(RELATION_FILE, MODE_PRIVATE);
        prefNumber = getSharedPreferences(NUMBER_FILE, MODE_PRIVATE);
        prefEmail = getSharedPreferences(EMAIL_FILE, MODE_PRIVATE);
        prefAddress = getSharedPreferences(ADDRESS_FILE, MODE_PRIVATE);

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
                people.add(new Person(
                        readName.get(i),
                        readRelation.get(i),
                        readNumber.get(i),
                        readEmail.get(i),
                        readAddress.get(i)
                ));

            }
        }
    }
}
