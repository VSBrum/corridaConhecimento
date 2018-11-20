package com.example.vinic.testefirebase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.List;
import java.util.Set;

public class MainMenu extends AppCompatActivity {

    Button comecar;
    RadioButton statusBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        statusBluetooth = findViewById(R.id.statusBluetooth);
        statusBluetooth.setEnabled(false);
        comecar = findViewById(R.id.btnComecar);

        //Botão para ir para tela de pergunta e resposta
        comecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }


    //Menu para conectar com o carrinho, porém primeiro precisa parear o aparelho com o carrinho
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.conectar: {
                if (!ToothReadWrite.statusTooth()) {
                    liga_bluetooth();
                    comecar.setEnabled(false);
                } else {
                    comecar.setEnabled(true);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Dispositivos Pareados");
                    final EditText input = new EditText(this);
                    int i = 0;
                    final Set<BluetoothDevice> pareados = ToothReadWrite.Pareados();
                    if (pareados.size() > 0) {
                        for (BluetoothDevice device : pareados) {
                            i++;
                        }
                    }
                    String address[] = new String[i];
                    final String names[] = new String[i];
                    i = 0;
                    if (pareados.size() > 0) {
                        for (BluetoothDevice device : pareados) {
                            names[i] = device.getName() + "#" + device.getAddress();
                            i++;
                        }
                    }
                    builder.setItems(names, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            String[] mac = names[which].toString().split("#");
                            try {
                                ToothReadWrite.Connect(mac[1].trim());
                                statusBluetooth.setEnabled(true);
                                statusBluetooth.setChecked(true);
                                statusBluetooth.setText("Conectado: " + names[which].toString());

                            } catch (Exception e) {

                            }
                        }
                    });
                    builder.show();
                }
                break;
            }
            case R.id.disconectar: {
                if(statusBluetooth.getText().equals("Conectado")){
                    ToothReadWrite.disconnect();
                    statusBluetooth.setEnabled(false);
                    statusBluetooth.setChecked(false);
                    statusBluetooth.setText("Desconectado");
                }
                break;
            }
        }
        return true;
    }

    public void liga_bluetooth() {
        if (!ToothReadWrite.statusTooth()) {
            Intent liga_blu_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(liga_blu_intent, 1);
        }
    }
}