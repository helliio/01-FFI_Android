

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import ffiandroid.situationawareness.R;
import ffiandroid.situationawareness.model.UserInfo;
import ffiandroid.situationawareness.model.service.UserService;
import ffiandroid.situationawareness.model.service.impl.SoapUserService;
import ffiandroid.situationawareness.model.util.Constant;

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
    protected static final String PREF_NAME = "name";
    protected static final String PREF_SERVERIP = "serverIP";

    /**
     * Is part of android apps life cycle and is called automatically
     * by the android system. For more info see the android developer manual
     * on activity life cycle
     * @param  savedInstanceState
     */
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("ACTION_LOGOUT"));
        UserInfo.setMyAndroidID(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        autoLogin();
        if(Register.server_ip != Constant.DEFAULT_SERVICE_URL)
        {
            ((CheckBox) findViewById(R.id.server_ip_checkbox)).setChecked(false);
            ((EditText) findViewById(R.id.server_ip_edit_text)).setText(Register.server_ip);
            findViewById(R.id.server_ip_edit_text).setEnabled(true);
        }
        softkeyboardDone();
    }


    /**
     * action on soft keyboard done clicked
     */
    public void softkeyboardDone() {
        EditText editTextpass = (EditText) findViewById(R.id.editTextLoginPass);
        editTextpass.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                }
                return false;
            }
        });
    }

    /**
     * Is called when the checkbox related to ip address preference is clicked
     *
     * @param view
     */
    public void onIPCheckClicked(View view)
    {
        CheckBox ipCheckBox = ((CheckBox) findViewById(R.id.server_ip_checkbox));
        EditText ipEditText = ((EditText) findViewById(R.id.server_ip_edit_text));
        if(ipCheckBox.isChecked())
        {
            ipEditText.getText().clear();
            ipEditText.setEnabled(false);
        }
        else
        {
            ipEditText.setEnabled(true);
        }
    }

    /**
     * Called when login button is clicked, and checks that
     * the input is valid (Except ip address) and tries to
     * login via {@link #login()}
     *
     * @param view
     */
    public void loginClicked(View view) {
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
     * Called when register button is clicked and
     * navigates to the register screen
     *
     * @param view
     */
    public void registerClicked(View view) {
        startActivity(new Intent(this, Register.class));
    }

    /**
     * Starts a new activity {@link MapActivity} and navigates to this view.
     */
    private void toMapWindow() {
        startActivity(new Intent(this, MapActivity.class));
    }


    /**
     * Starts a login thread that tries to connect to the server
     * and verify this user. If this is successful the user is
     * navigated to the MapView
     */
    private void login() {
        new Thread(loginThread).start();
    }

    private Runnable loginThread = new Runnable() {
        @Override public void run() {
            Looper.prepare();
            String userName = ((EditText) findViewById(R.id.editTextLoginID)).getText().toString();
            String userPass = ((EditText) findViewById(R.id.editTextLoginPass)).getText().toString();
            EditText ipEditText = ((EditText) findViewById(R.id.server_ip_edit_text));
            if (isOnline()) {
                try {
                    if(ipEditText.getText().length() > 0)
                    {
                        Constant.SERVICE_URL = "http://" + ipEditText.getText().toString() + ":8080/";
                    }
                    else
                    {
                        Constant.SERVICE_URL = Constant.DEFAULT_SERVICE_URL;
                    }

                    String message = userService.login(userName, UserInfo.getMyAndroidID(), userPass);
                    JSONObject jsonMessage = new JSONObject(message);

                    if (message != null && jsonMessage.get("desc").equals("success")) {
                        String name = new JSONObject(jsonMessage.getString("obj")).getString("name");
                        System.out.println(Constant.SERVICE_URL);
                        UserInfo.setName(name);
                        UserInfo.setUserID(userName);
                        rememberMe(userName, userPass, name, Constant.SERVICE_URL);
                        toMapWindow();
                    } else {
                        Toast.makeText(getBaseContext(), "Login failed, please try again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Looper.loop();
            } else {
                Toast.makeText(getBaseContext(), "Need network connection for first time login!", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    };

    /**
     * Saves the username, user password, username and ip setting
     * to use later when trying to automatically login
     *
     * @param userName user's username
     * @param userPass user's password
     * @param name full name of the user
     * @param serverIP the server ip address this user is trying to connect to
     */
    private void rememberMe(String userName, String userPass, String name, String serverIP) {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().putString(PREF_USERNAME, userName)
                .putString(PREF_PASSWORD, userPass).putString(PREF_NAME, name).putString(PREF_SERVERIP, serverIP).commit();
    }

    /**
     * checks to see if the user can automatically login.
     * If this is true, that the user is automatically taken
     * to the MapView instead of the login screen
     */
    private void autoLogin() {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String username = pref.getString(PREF_USERNAME, null);
        String name = pref.getString(PREF_NAME, null);
        String serverIP = pref.getString(PREF_SERVERIP, null);
        if (username != null && name != null && serverIP != null) {
            UserInfo.setUserID(username);
            UserInfo.setName(name);
            Constant.SERVICE_URL = serverIP;
            toMapWindow();
        }
    }


    /**
     * Checks to see if the user has a network connection
     *
     * @return true is there is a network connection
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    /**
     * Is called automatically by android.
     * For more info see the android developer guide or
     * API specification
     *
     * @param menu
     */
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    /**
     * Is called automatically by android.
     * For more info see the android developer guide or
     * API specification
     *
     * @param item the menu item selected
     * @return boolean
     */
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
     * Receive broadcast for logout
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    /**
     * Is part of android apps life cycle and is called automatically
     * by the android system. For more info see the android developer manual
     * on activity life cycle
     */
    @Override protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}