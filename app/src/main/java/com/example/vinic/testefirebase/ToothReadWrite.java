package com.example.vinic.testefirebase;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class ToothReadWrite {

    private static BluetoothAdapter adaptador = BluetoothAdapter.getDefaultAdapter();
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static BluetoothSocket btSocket = null;
    private static BluetoothDevice btDevice = null;
    private static OutputStream outstream = null;
    private static InputStream instream = null;

    public ArrayAdapter<String> deviceslist;

    //--------- Ver dispositivos Pareados -------//
    public static Set<BluetoothDevice> Pareados()
    {
        return adaptador.getBondedDevices();
    }

    private static void close(Closeable aConnectedObject) {
        if ( aConnectedObject == null ) return;
        try {
            aConnectedObject.close();
        } catch ( IOException e ) {
        }
        aConnectedObject = null;
    }

    private UUID getSerialPortUUID() {
        return MY_UUID;
    }

    //------ Conectar com dispositivos  pareados -------//
    public static void Connect(String DeviceMac) {
        try
        {
            btDevice = adaptador.getRemoteDevice(DeviceMac.trim());
            btSocket = btDevice.createRfcommSocketToServiceRecord(MY_UUID);
            adaptador.cancelDiscovery();
            btSocket.connect();
            outstream = btSocket.getOutputStream();
            instream = btSocket.getInputStream();

        }
        catch (Exception e) {
            Log.v("Erro:",e.toString());
            close( instream );
            close( btSocket );
        }

    }
    //--------- BLUETOOTH STATUS ----------//
    public static boolean statusTooth(){

        return adaptador.isEnabled();
    }


    //------- ISSO AQUI ERA PRA ESCREVER MAS NAO SEI SE FUNCIONA ------/
    public static void WriteBuffer(String msg)
    {
        byte[] Buffer=msg.getBytes();

        try {
            outstream.write(Buffer);
        }
        catch (Exception e)
        {
            Log.v("ERRO:",e.toString());
        }

    }

    public static void WriteBuffer(byte msg)
    {
        try
        {
            outstream.write(msg);
        }
        catch (Exception e)
        {
            Log.v("ERRO:",e.toString());
        }

    }

    public static String ReadBuffer() {
        byte[] buffer = new byte[1024];
        int bytes;
        try
        {
            if (instream.available()>0) {
                bytes = ToothReadWrite.instream.read(buffer);
                String str = new String(buffer);
                Log.v("BUFFER:", str);
                return str;
            }
            else {
                SystemClock.sleep(100);}


        } catch (Exception e) {

        }
        return null;
    }
    //----- disconnect-----//
    public static void disconnect()
    {
        try {
            btSocket.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}