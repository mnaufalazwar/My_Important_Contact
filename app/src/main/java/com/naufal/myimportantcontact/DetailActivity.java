package com.naufal.myimportantcontact;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    TextView tvName, tvNumber, tvEmail, tvAddress, tvRelation;
    Button btnDelete, btnEdit, btnSave, btnCancelEdit;
    EditText etName, etRelation, etNumber, etEmail, etAddress;
    CardView cvCall, cvVisit;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvName = findViewById(R.id.tvName);
        tvNumber = findViewById(R.id.tvNumber);
        tvEmail = findViewById(R.id.tvEmail);
        tvAddress = findViewById(R.id.tvAddress);
        tvRelation = findViewById(R.id.tvRelation);

        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);
        btnSave = findViewById(R.id.btnSave);
        btnCancelEdit = findViewById(R.id.btnCancelEdit);

        etName = findViewById(R.id.etName);
        etRelation = findViewById(R.id.etRelation);
        etNumber = findViewById(R.id.etNumber);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);

        cvCall = findViewById(R.id.cvCall);
        cvVisit = findViewById(R.id.cvVisit);

        final int index = getIntent().getIntExtra("index", 0);

        tvName.setText(ApplicationClass.people.get(index).getName());
        tvNumber.setText(ApplicationClass.people.get(index).getNumber());
        tvEmail.setText(ApplicationClass.people.get(index).getEmail());
        tvAddress.setText(ApplicationClass.people.get(index).getAddress());
        tvRelation.setText(ApplicationClass.people.get(index).getRelation());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailActivity.this);
                alertDialogBuilder.setMessage("Are you sure you want to delete this contact?");

                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent();
                                intent.putExtra("index", index);

                                setResult(ApplicationClass.RESULT_CODE_DELETE, intent);

                                DetailActivity.this.finish();

                            }
                        });

                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .hide(fragmentManager.findFragmentById(R.id.editFrag))
                .commit();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction()
                        .show(fragmentManager.findFragmentById(R.id.editFrag))
                        .commit();

            }
        });

        btnCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction()
                        .hide(fragmentManager.findFragmentById(R.id.editFrag))
                        .commit();

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etName.getText().toString().isEmpty()
                        || etRelation.getText().toString().isEmpty()
                        || etNumber.getText().toString().isEmpty()
                        || etEmail.getText().toString().isEmpty()
                        || etAddress.getText().toString().isEmpty()){
                    Toast.makeText(DetailActivity.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                }
                else {

                    Intent intent = new Intent();

                    intent.putExtra("newName", etName.getText().toString());
                    intent.putExtra("newRelation", etRelation.getText().toString());
                    intent.putExtra("newNumber", etNumber.getText().toString());
                    intent.putExtra("newEmail", etEmail.getText().toString());
                    intent.putExtra("newAddress", etAddress.getText().toString());
                    intent.putExtra("index", index);

                    setResult(ApplicationClass.RESULT_CODE_EDIT, intent);

                    tvName.setText(etName.getText().toString());
                    tvNumber.setText(etNumber.getText().toString());
                    tvEmail.setText(etEmail.getText().toString());
                    tvAddress.setText(etAddress.getText().toString());
                    tvRelation.setText(etRelation.getText().toString());

                    Toast.makeText(DetailActivity.this, "Successfully Edited!", Toast.LENGTH_SHORT).show();

                    fragmentManager.beginTransaction()
                            .hide(fragmentManager.findFragmentById(R.id.editFrag))
                            .commit();

                }
            }
        });

        cvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tel = "tel:" + ApplicationClass.people.get(index).getNumber();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(tel));
                startActivity(intent);

            }
        });

        cvVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loc = ApplicationClass.people.get(index).getAddress();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + loc));
                startActivity(intent);

            }
        });

    }
}
