package com.ladwa.aditya.chatapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    Socket socket;
    List<Model> modelList = new ArrayList<>();
    CustomAdapter adapter;
    String username;
    ListView mListView;
    ImageButton mSendButton;
    EditText mInputMessage;
    TextView online, whoistyping;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);


        View v = View.inflate(this, R.layout.layout_actionbar, null);

        actionBar.setCustomView(v);

        online = (TextView) v.findViewById(R.id.totalonline);
        whoistyping = (TextView) v.findViewById(R.id.typing);


        username = getIntent().getStringExtra(Config.TAG_NAME).trim();


        final JSONObject userObj = new JSONObject();
        try {
            userObj.put("name", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, username, Toast.LENGTH_SHORT).show();

        mListView = (ListView) findViewById(R.id.list_view_messages);
        adapter = new CustomAdapter(this, modelList);
        mListView.setAdapter(adapter);

        mInputMessage = (EditText) findViewById(R.id.inputMsg);
        mSendButton = (ImageButton) findViewById(R.id.btnSend);

        mInputMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                socket.emit(Config.TAG_TYPING, userObj);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //Send Button Click Event
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = mInputMessage.getText().toString().trim();
                if (message.length() <= 0)
                    Toast.makeText(getApplicationContext(), "Please Enter a Message", Toast.LENGTH_SHORT).show();
                else
                    sendMessage(message);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            socket = IO.socket("http://10.0.2.2:8080");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //Listen For Connections
        socket.on(Socket.EVENT_CONNECT, onConnect);

        //Listen For Disconnection
        socket.on(Socket.EVENT_DISCONNECT, onDisconnect);

        //Listen For message recieved
        socket.on(Config.TAG_OUTPUT, onMessageRecieved);

        //Listen for who is typing
        socket.on(Config.TAG_ISTYPING, onIsTyping);

        //Listen for status
        socket.on(Config.TAG_STATUS, onStatusRecieve);


        socket.on(Config.TAG_ONLINE, onTotalOnline);


        //Connect to socket
        socket.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        socket.disconnect();

        socket.off(Socket.EVENT_CONNECT, onConnect);

        //Listen For Disconnection
        socket.off(Socket.EVENT_DISCONNECT, onDisconnect);

        //Listen For message recieved
        socket.off(Config.TAG_OUTPUT, onMessageRecieved);

        //Listen for who is typing
        socket.off(Config.TAG_ISTYPING, onIsTyping);

        //Listen for status
        socket.off(Config.TAG_STATUS, onStatusRecieve);
    }

    private Emitter.Listener onTotalOnline = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String totalonline = args[0].toString();

                    online.setText(totalonline + " are Online");

                }
            });

        }
    };

    private Emitter.Listener onStatusRecieve = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject obj = (JSONObject) args[0];
                    Log.d("Status", obj.toString());
                    try {
                        String mes = obj.getString("message");
                        Boolean clear = obj.getBoolean("clear");
                        if (clear) {
                            mInputMessage.setText("");
                            Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        }
    };

    private void sendMessage(String message) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("name", username);
            obj.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        socket.emit(Config.TAG_INPUT, obj);


    }


    private Emitter.Listener onIsTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    final String name = args[0].toString();
                    if (!name.equals(username))
//                        Toast.makeText(getApplicationContext(), name + " is Typing", Toast.LENGTH_SHORT).show();
                        whoistyping.setText(name + " is Typing...");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            whoistyping.setText("Ideal");
                        }
                    }, 2500);


                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("Socket", "Disconnected");
        }
    };

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("Socket", "Connected......");

            socket.emit("hello", "Hello World");
        }
    };

    private Emitter.Listener onMessageRecieved = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray myArray = (JSONArray) args[0];
                    if (myArray.length() > 0) {
                        for (int i = 0, j = myArray.length(); i < j; i++) {
                            try {
                                String name = myArray.getJSONObject(i).getString("name");
                                String messages = myArray.getJSONObject(i).getString("message");
                                Model model;
                                if (!name.equals(username))
                                    model = new Model(name, messages, false);
                                else
                                    model = new Model(name, messages, true);


                                modelList.add(model);
                                adapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            });
        }
    };


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
