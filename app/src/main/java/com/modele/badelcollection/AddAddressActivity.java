package com.modele.badelcollection;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class AddAddressActivity extends AppCompatActivity {

    private EditText city;
    private EditText locality;
    private EditText flatNo;
    private EditText pincode;
    private EditText landmarck;
    private EditText name;
    private EditText mobileNo;
    private EditText alternateMobileNo;
    private Spinner stateSpinner;
    private Button saveBtn;

    private String []stateList;

    private String selectedState;

    private Dialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        Toolbar toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Add a new address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ///// loading Dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.slider_background);
        //loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background)); // valab aparti de api 21
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ///// loading Dialog
        stateList = getResources().getStringArray(R.array.india_states);


        saveBtn = findViewById(R.id.save_btn);
        city = findViewById(R.id.city);
        locality = findViewById(R.id.locality);
        flatNo = findViewById(R.id.flat_no);
        pincode = findViewById(R.id.pincode);
        landmarck = findViewById(R.id.landmark);
        name = findViewById(R.id.name);
        mobileNo = findViewById(R.id.mobile_no);
        alternateMobileNo = findViewById(R.id.alternate_mobile_no);
        stateSpinner = findViewById(R.id.stateSpinner);


        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,stateList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stateSpinner.setAdapter(spinnerAdapter);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedState = stateList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(city.getText())){
                    if(!TextUtils.isEmpty(locality.getText())){
                        if(!TextUtils.isEmpty(flatNo.getText())){
                            if(!TextUtils.isEmpty(pincode.getText()) && pincode.getText().length() == 6){
                                if(!TextUtils.isEmpty(name.getText())){
                                    if(!TextUtils.isEmpty(mobileNo.getText()) && mobileNo.getText().length() == 10){
                                        loadingDialog.show();

                                        final String fullAddress = flatNo.getText().toString()+" "+locality.getText().toString()+" "+landmarck.getText().toString()+" "+city.getText().toString()+" "+selectedState;

                                        Map<String,Object> addAddress = new HashMap<>();
                                        addAddress.put("list_size",(long)DBqueries.addressesModelList.size() + 1);

                                        if(TextUtils.isEmpty(alternateMobileNo.getText())) {

                                            addAddress.put("fullname_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), name.getText().toString() + "-" + mobileNo.getText().toString());
                                        }else {
                                            addAddress.put("fullname_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), name.getText().toString() + "-" + mobileNo.getText().toString()+"or"+ alternateMobileNo.getText().toString());

                                        }
                                        addAddress.put("address_"+String.valueOf((long)DBqueries.addressesModelList.size() + 1),fullAddress);
                                        addAddress.put("pincode_"+String.valueOf((long)DBqueries.addressesModelList.size() + 1),pincode.getText().toString());
                                        addAddress.put("selected_"+String.valueOf((long)DBqueries.addressesModelList.size() + 1),true);
                                        if(DBqueries.addressesModelList.size() > 0) {
                                            addAddress.put("selected_" + (DBqueries.selectedAddress + 1), false);
                                        }

                                        FirebaseFirestore.getInstance().collection("USERS")
                                                .document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                                                .document("MY_ADDRESSES")
                                                .update(addAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){

                                                    if(DBqueries.addressesModelList.size() > 0) {
                                                        DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelected(false);
                                                    }
                                                    if (TextUtils.isEmpty(alternateMobileNo.getText())) {
                                                        DBqueries.addressesModelList.add(new AddressesModel(name.getText().toString() + " - " + mobileNo.getText().toString(), fullAddress, pincode.getText().toString(), true));

                                                    }else {
                                                        DBqueries.addressesModelList.add(new AddressesModel(name.getText().toString() + " - " + mobileNo.getText().toString()+" "+alternateMobileNo.getText().toString(), fullAddress, pincode.getText().toString(), true));
                                                    }
                                                    if(getIntent().getStringExtra("INTENT").equals("deliveryIntent")) {
                                                        Intent deliveryIntente = new Intent(AddAddressActivity.this, DeliveryActivity.class);
                                                        startActivity(deliveryIntente);
                                                    }else {
                                                        MyAddressesActivity.refreshItem(DBqueries.selectedAddress,DBqueries.addressesModelList.size() - 1);
                                                    }
                                                    DBqueries.selectedAddress = DBqueries.addressesModelList.size() - 1;
                                                    finish();

                                                }else {
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(AddAddressActivity.this, error, Toast.LENGTH_SHORT).show();
                                                }
                                                loadingDialog.dismiss();
                                            }
                                        });


                                    }else {
                                        mobileNo.requestFocus();
                                        Toast.makeText(AddAddressActivity.this, "please provide valid Nomber", Toast.LENGTH_SHORT).show();

                                    }

                                }else {
                                    name.requestFocus();
                                }

                            }else {
                                pincode.setSelected(true);
                                Toast.makeText(AddAddressActivity.this, "please provide valid pincode", Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            flatNo.requestFocus();
                        }

                    }else {
                        locality.requestFocus();
                    }
                }else {
                    city.requestFocus();
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
