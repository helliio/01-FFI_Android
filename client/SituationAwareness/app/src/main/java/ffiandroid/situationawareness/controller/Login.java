

package ffiandroid.situationawareness.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import ffiandroid.situationawareness.R;
import ffiandroid.situationawareness.model.UserInfo;
import ffiandroid.situationawareness.model.service.UserService;
import ffiandroid.situationawareness.model.service.impl.SoapUserService;

/**
 * This file is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 2/20/15.
 * <p/>
 * responsible for this file: GuoJunjun & Simen
 */
public class Login extends ActionBarActivity {
    private UserService userService = new SoapUserService();

    public static final String PREFS_NAME = "MyPrefsFile";
    protected static final String PREF_USERNAME = "username";
    protected static final String PREF_PASSWORD = "password";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("ACTION_LOGOUT"));
        UserInfo.setMyAndroidID(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        autoLogin();
        softkeyboardDone();
    }


    /**
     * action on soft keyboard done clicked
     */
    public void softkeyboardDone() {
        EditText editTextpass = (EditText) findViewById(R.id.editTextLoginPass);
        editTextpass.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                }
                return false;
            }
        });
    }

    /**
     * navigate to Map view if login is clicked and user name and password is valid
     *
     * @param view
     */
    public void loginClicked(View view) {
        //        Toast.makeText(this, "Login ....." + hasinput(), Toast.LENGTH_SHORT).show();
        if (hasinput()) {
            login();
        } else {
            Toast.makeText(getApplicationContext(), "Check your input !", Toast.LENGTH_SHORT);
        }
    }

    private boolean hasinput() {
        return (((EditText) findViewById(R.id.editTextLoginID)).getText().toString().length() > 0 &&
                ((EditText) findViewById(R.id.editTextLoginPass)).getText().toString().length() > 0);
    }

    /**
     * go to register screen
     *
     * @param view
     */
    public void registerClicked(View view) {
        startActivity(new Intent(this, Register.class));
    }

    /**
     * Go to MapView window
     */
    private void toMapWindow() {
        startActivity(new Intent(this, MapActivity.class));
    }

    private void login() {
        new Thread(loginThread).start();
    }

    private Runnable loginThread = new Runnable() {
        @Override public void run() {
            Looper.prepare();
            String userName = ((EditText) findViewById(R.id.editTextLoginID)).getText().toString();
            String userPass = ((EditText) findViewById(R.id.editTextLoginPass)).getText().toString();
            if (isOnline()) {
                try {
                    String message = userService.login(userName, UserInfo.getMyAndroidID(), userPass);
                    JSONObject jsonMessage = new JSONObject(message);

                    if (message != null && jsonMessage.get("desc").equals("success")) {
                        UserInfo.setUserID(userName);
                        rememberMe(userName, userPass);
                        toMapWindow();
                    } else {
                        Toast.makeText(getBaseContext(), "Login failed, please try again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
                Looper.loop();
            } else {
                Toast.makeText(getBaseContext(), "Need network connection for first time login!", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    };

    /**
     * remember user and user pass
     *
     * @param userName
     * @param userPass
     */
    private void rememberMe(String userName, String userPass) {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().putString(PREF_USERNAME, userName)
                .putString(PREF_PASSWORD, userPass).commit();
    }

    /**
     * auto login from remember me
     */
    private void autoLogin() {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String username = pref.getString(PREF_USERNAME, null);
        //        String password = pref.getString(PREF_PASSWORD, null);
        if (username != null) {
            UserInfo.setUserID(username);
            toMapWindow();
        }
    }


    /**
     * @return true is there is a network connection
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_register:
                startActivity(new Intent(this, Register.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * receive broadcast for logout
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            //            if (!getIntent().getExtras().getBoolean("LOGIN_ON")) {
            //                Intent i = new Intent(getBaseContext(), Login.class);
            //                i.putExtra("LOGIN_ON", true);
            //                startActivity(i);
            //            }
            //            startActivity(new Intent(getBaseContext(), Login.class));

            finish();
        }
    };

    @Override protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}