package ffiandroid.situationawarenessuitest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by liang on 18/03/15.
 */

public class Login extends ActionBarActivity {

    TextView mainTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button loginButton;
        loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userText = (EditText) findViewById(R.id.editTextLoginID);
                EditText passText = (EditText) findViewById(R.id.editTextLoginPass);
                String user = userText.getText().toString();
                String pass = passText.getText().toString();

                startActivity(new Intent(v.getContext(),MapsActivity.class));
                finish();

                if (user.equals(Global.user) && pass.equals(Global.password)){
                    startActivity(new Intent(v.getContext(),MapsActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(v.getContext(), "Invalid Username Combination", Toast.LENGTH_LONG).show();
                }

            }
        });
        Button registerButton;
        registerButton = (Button) findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), Register.class));
                finish();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_register:
                startActivity(new Intent(this, Register.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}