

package ffiandroid.situationawareness;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
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

import ffiandroid.situationawareness.model.UserInfo;
import ffiandroid.situationawareness.service.UserService;
import ffiandroid.situationawareness.service.impl.SoapUserService;

/**
 * This file is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 2/20/15.
 * <p/>
 * responsible for this file: GuoJunjun & Simen
 */
public class Login extends ActionBarActivity {
    private UserService userService = new SoapUserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        softkeyboardDone();
        UserInfo.setMyAndroidID(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
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
     * navigate to Map view if login is clicked and user name and password is valid
     *
     * @param view
     */
    public void loginClicked(View view) {
        Toast.makeText(this, "Login ....." + hasinput(), Toast.LENGTH_SHORT).show();
        if (hasinput()) {
            login();
        } else {
            Toast.makeText(getApplicationContext(), "Check you input !", Toast.LENGTH_SHORT);
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
        @Override
        public void run() {
            Looper.prepare();
            try {
                String userName = ((EditText) findViewById(R.id.editTextLoginID)).getText().toString();
                String message = userService.login(userName, UserInfo.getMyAndroidID(),
                        ((EditText) findViewById(R.id.editTextLoginPass)).getText().toString());
                JSONObject jsonMessage = new JSONObject(message);
                if (message != null && jsonMessage.get("desc").equals("success")) {
                    Toast.makeText(getBaseContext(), "Welcome back!", Toast.LENGTH_SHORT);
                    UserInfo.setUserID(userName);
                    toMapWindow();
                } else {
                    Toast.makeText(getBaseContext(), "Login failed, please try again !", Toast.LENGTH_SHORT);
                }
            } catch (Exception e) {
            }
            Looper.loop();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
}