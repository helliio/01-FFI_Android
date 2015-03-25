package ffiandroid.situationawarenessuitest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by liang on 18/03/15.
 */
public class Register extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Button backButton;
        backButton = (Button) findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), Login.class));
                finish();
            }
        });
        Button registerButton;
        registerButton = (Button) findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    startActivity(new Intent(v.getContext(), Login.class));
                    finish();
                }
                else{
                    Toast.makeText(v.getContext(), "Make user you enter the same password !", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean validate(){
        EditText editTextUserpassword = (EditText) findViewById(R.id.register_user_password);
        EditText editTextUserpasswordR = (EditText) findViewById(R.id.register_user_password_repeat);
        EditText editTextUserid = (EditText) findViewById(R.id.register_user_id);
        String pass = editTextUserpassword.getText().toString();
        String passR = editTextUserpasswordR.getText().toString();

        if (pass.equals(passR)){
            Global.user = editTextUserid.getText().toString();
            Global.password = pass;
            return true;
        }
        else{
            return false;
        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_login:
                startActivity(new Intent(this, Login.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
