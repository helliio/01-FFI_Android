package com.aprilchun.androidtest;

import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button but1 = (Button) this.findViewById(R.id.button1);
        Button but2 = (Button) this.findViewById(R.id.button2);
        Button but3 = (Button) this.findViewById(R.id.button3);
        Button but4 = (Button) this.findViewById(R.id.button4);

        but1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.v("MyTag", "111111111111111111111");
                Log.v("MyTag", "onClick");
                new Thread(downloadRun).start();
            }


            Runnable downloadRun = new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    // TODO Auto-generated method stub
                    SoapObject request = new SoapObject("http://service.sair.ntnu.edu/", "sayHello");
                    Toast.makeText(MainActivity.this, "test", Toast.LENGTH_LONG).show();
                    //request.addProperty("sayHi", "");
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.bodyOut = request;
                    HttpTransportSE ht = new HttpTransportSE("http://78.91.48.254:8080/HelloWorld?wsdl");
                    try {

                        System.out.println("aaaaaa");
                        ht.call(null, envelope);
                        System.out.println("bbbbbb");
                        //SoapObject soapObject = (SoapObject) envelope.getResponse();
                        //Log.v("MyTag", soapObject.getNamespace());
                        System.out.println(envelope.getResponse().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Looper.loop();
                }
            };
        });

        but2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.v("MyTag", "111111111111111111111");
                Log.v("MyTag", "onClick");
                new Thread(downloadRun).start();
            }


            Runnable downloadRun = new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    // TODO Auto-generated method stub
                    SoapObject request = new SoapObject("http://service.sair.ntnu.edu/", "sendLocationReport");
                    request.addProperty("arg0", "my dear");
                    request.addProperty("arg1", "I am here");
                    Toast.makeText(MainActivity.this, "test", Toast.LENGTH_LONG).show();
                    //request.addProperty("sayHi", "");
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.bodyOut = request;
                    HttpTransportSE ht = new HttpTransportSE("http://192.168.2.101:8080/ReportService?wsdl");
                    try {

                        System.out.println("aaaaaa");
                        ht.call(null, envelope);
                        System.out.println("bbbbbb");
                        //SoapObject soapObject = (SoapObject) envelope.getResponse();
                        //Log.v("MyTag", soapObject.getNamespace());

                        Toast.makeText(MainActivity.this, envelope.getResponse().getClass().getName(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Looper.loop();
                }
            };
        });

        but3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.v("MyTag", "onClick");
                new Thread(downloadRun).start();
            }

            Runnable downloadRun = new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    new ServiceActivity().register();
                    Looper.loop();
                }
            };
        });

        but4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.v("MyTag", "onClick");
                new Thread(downloadRun).start();
            }

            Runnable downloadRun = new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    String androidId = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    new ServiceActivity().login("chun", androidId, "chunpassword");
                    Looper.loop();
                }
            };
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
