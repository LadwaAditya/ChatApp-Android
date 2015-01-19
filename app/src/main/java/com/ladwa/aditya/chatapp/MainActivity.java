package com.ladwa.aditya.chatapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    Socket socket;
    List<Model> modelList = new ArrayList<>();
    CustomAdapter adapter;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        username = getIntent().getStringExtra(Config.TAG_NAME).toString().trim();
        Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
        ListView mListView = (ListView) findViewById(R.id.list_view_messages);
        adapter = new CustomAdapter(this, modelList);
        mListView.setAdapter(adapter);

        try {
            socket = IO.socket("http://10.0.2.2:8080");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("Socket", "Connected......");

                socket.emit("hello", "Hello World");
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("Socket", "Disconnected");
            }
        });

        socket.on(Config.TAG_OUTPUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray myArray = (JSONArray) args[0];
                for (int i = 0, j = myArray.length(); i < j; i++) {
                    try {
                        String name = myArray.getJSONObject(i).getString("name");
                        String messages = myArray.getJSONObject(i).getString("message");
                        Model model;
                        if (username.equals(name.trim()))
                            model = new Model(name, messages, true);
                        else
                            model = new Model(name, messages, false);

                        modelList.add(model);

                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        socket.connect();


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
