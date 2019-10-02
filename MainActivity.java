package ru.tarasplakhotnichenko.tictactoe2;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;



import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        //Logcat: Info;MainActivity;Regex:checked; Show only application
        Log.i(MainActivity.class.getName(), "onCreate");
        //Restore screen state after changing screen orientation if any
        load(state);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(MainActivity.class.getName(), "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(MainActivity.class.getName(), "onStart");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(MainActivity.class.getName(), "onPause");
    }


    @Override
    //Envoked every time app is getting inactive  that is  paused, stopped or destroyed
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        Log.i(MainActivity.class.getName(), "onSaveInstanceState");
        state.putIntegerArrayList("gamefield", memory.saveCurPlayField());
    }

    //Restore active screen state by loading gamefield map from arraylist
    private void load(Bundle state) {
        //state could be null on app start
        if (state != null) {
            HashMap<Integer, Integer> playField = memory.restoreCurPlayField(state.getIntegerArrayList("gamefield"));
            for (Map.Entry<Integer, Integer> entry : playField.entrySet()) {
                Button btn = findViewById(entry.getKey());
                if (entry.getValue().equals(1)) {
                    btn.setText("X");
                } else if (entry.getValue().equals(0)) {
                    btn.setText("0");
                }
            }
        }
    }


    //---------------------------------------------------------------------------------

    private Memory memory = new Memory(
            Arrays.asList(R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9));


    public void answer(View view) {

        if (memory.checkLegalMove(view.getId())) {

            //Toast.makeText(this, "Button tapped-: " + memory.getTheWinner(), Toast.LENGTH_LONG).show();


            if (!memory.isFinish()) {
                //you
                if (memory.getTheWinner().equals("-")) {
                    memory.addAnswer(view.getId(), 1);
                    ( (Button) view ).setText("X");
                    //Toast.makeText(this, "Button tapped: " + String.valueOf(view.getId()), Toast.LENGTH_LONG).show();
                }

                if (memory.isWinner()) {
                    Toast.makeText(this, "Game over " + memory.getTheWinner() + " wins", Toast.LENGTH_LONG).show();
                }
                //Computer
                if (memory.getTheWinner().equals("-")) {
                    Button btn = MainActivity.this.findViewById(memory.rnd());
                    ( (Button) btn ).setText("0");
                    //Toast.makeText(this, "Button tapped: " + String.valueOf(view.getId()), Toast.LENGTH_LONG).show();
                }

                if (memory.isWinner()) {
                    Toast.makeText(this, "Game over " + memory.getTheWinner() + " wins", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(this, "Game over: parity", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, String.valueOf(view.getId()) + " illegal move", Toast.LENGTH_LONG).show();
        }

    }
}

