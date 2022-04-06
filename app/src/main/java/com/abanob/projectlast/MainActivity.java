package com.abanob.projectlast;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

@SuppressLint("SetTextI18n")

public class MainActivity extends AppCompatActivity {
    Thread Thread1 = null;
    EditText etIP;
    EditText etPort;
    EditText etmssg;
    Button b;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etIP = (EditText) findViewById(R.id.etIP);
        etPort = (EditText) findViewById(R.id.etPort);
        etmssg = (EditText) findViewById(R.id.etmssg);
        b=(Button) findViewById(R.id.button1);
        b.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread( new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Socket s =new Socket(etIP.getText().toString(),Integer.parseInt( etPort.getText().toString() ));
                            DataOutputStream dos=new DataOutputStream( s.getOutputStream() );
                            // Create buffered reader
                            BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));

                            dos.write( (etmssg.getText().toString()+"\n").getBytes()  );
                            dos.flush();
                            /// Read respponse
                            String str="";
                            str=br.readLine();
                            dos.write("CLOSE SOCKET".getBytes());
                            dos.flush();
                            dos.close();
                            s.close();
                        }

                        catch(NumberFormatException e){
                            e.printStackTrace();
                        }
                        catch (UnknownHostException e){
                            e.printStackTrace();
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }



                    }
                }).start();

            }
        } );
    }
}