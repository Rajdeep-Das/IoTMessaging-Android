package com.tanu.iotmessaging;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button btn_Send;
    TextView tv_Message;
    EditText et_Message;
    final String MESSAGE_REF="message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FireBase Realtime DataBase
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(MESSAGE_REF);

        btn_Send = (Button)findViewById(R.id.btn_Send);
        tv_Message=(TextView)findViewById(R.id.tv_message);
        et_Message=(EditText)findViewById(R.id.et_message);


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                if(value != null) {
                    tv_Message.setText(value);
                }else {
                    tv_Message.setText("");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB_ERROR", "Failed to read value.", error.toException());
            }
        });

        btn_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp_text = et_Message.getText().toString();
                myRef.setValue(tmp_text);
                et_Message.setText("");
            }
        });
  }
}
