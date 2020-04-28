package com.cm.dialer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Dialer extends AppCompatActivity {
    EditText editNumber;
    private static final int REQUEST_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);

        editNumber = (EditText) findViewById(R.id.editNumber);
        final Button buttonOne = (Button)findViewById(R.id.btnOne);
        buttonOne.setOnLongClickListener(
            new Button.OnLongClickListener() {
                public boolean onLongClick (View V){
                    longclick(buttonOne);
                    return true;
                }
            }
        );
        final Button buttonTwo = (Button)findViewById(R.id.btnTwo);
        buttonTwo.setOnLongClickListener(
                new Button.OnLongClickListener() {
                    public boolean onLongClick (View V){
                        longclick(buttonTwo);
                        return true;
                    }
                }
        );
        final Button buttonThree = (Button)findViewById(R.id.btnThree);
        buttonThree.setOnLongClickListener(
                new Button.OnLongClickListener() {
                    public boolean onLongClick (View V){
                        longclick(buttonThree);
                        return true;
                    }
                }
        );
    }

    public void buttonClickEvent(View v) {
        String phoneNumber = editNumber.getText().toString();
        try {

            switch (v.getId()) {
                case R.id.btnAterisk:
                    phoneNumber += "*";
                    editNumber.setText(phoneNumber);
                    break;
                case R.id.btnHash:
                    phoneNumber += "#";
                    editNumber.setText(phoneNumber);
                    break;
                case R.id.btnZero:
                    phoneNumber += "0";
                    editNumber.setText(phoneNumber);
                    break;
                case R.id.btnOne:
                    phoneNumber += "1";
                    editNumber.setText(phoneNumber);
                    break;
                case R.id.btnTwo:
                    phoneNumber += "2";
                    editNumber.setText(phoneNumber);
                    break;
                case R.id.btnThree:
                    phoneNumber += "3";
                    editNumber.setText(phoneNumber);
                    break;
                case R.id.btnFour:
                    phoneNumber += "4";
                    editNumber.setText(phoneNumber);
                    break;
                case R.id.btnFive:
                    phoneNumber += "5";
                    editNumber.setText(phoneNumber);
                    break;
                case R.id.btnSix:
                    phoneNumber += "6";
                    editNumber.setText(phoneNumber);
                    break;
                case R.id.btnSeven:
                    phoneNumber += "7";
                    editNumber.setText(phoneNumber);
                    break;
                case R.id.btnEight:
                    phoneNumber += "8";
                    editNumber.setText(phoneNumber);
                    break;
                case R.id.btnNine:
                    phoneNumber += "9";
                    editNumber.setText(phoneNumber);
                    break;
                case R.id.delete:
                    if (phoneNumber != null && phoneNumber.length() > 0) {
                        phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
                    }

                    editNumber.setText(phoneNumber);
                    break;
                case R.id.call:
                    makeCall();
            }

        } catch (Exception ex) {

        }
    }

    public void longclick(Button btn)
    {
        Toast.makeText(this, "You pressed "+ btn.getText()+"!!", Toast.LENGTH_LONG).show();
        editNumber.setText("96000000"+btn.getText());
    }
    private void makeCall(){
        String phoneNumber = editNumber.getText().toString();
        if (phoneNumber.trim().length() > 0) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }
            else{
                String callInfo = "tel:" + phoneNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(callInfo)));
            }
        }
        else{
            Toast.makeText(this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                  makeCall();
            }
            else{
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
