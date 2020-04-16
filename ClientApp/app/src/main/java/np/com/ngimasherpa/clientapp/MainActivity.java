package np.com.ngimasherpa.clientapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Ngima Sherpa
 */
public class MainActivity extends Activity implements OnClickListener {


    private final static int KEY_NAME = 1;
    private final static int KEY_CAST = 2;
    private final static String KEY_MESSAGE = "message";

    private Messenger messenger = null; //used to make an RPC invocation
    private boolean isBound = false;
    private ServiceConnection connection;//receives callbacks from bind and unbind invocations
    private Messenger replyTo = null; //invocation replies are processed by this Messenger

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_main);
        this.connection = new RemoteServiceConnection();
        this.replyTo = new Messenger(new IncomingHandler());

        findViewById(R.id.getCastButton).setOnClickListener(this);
        findViewById(R.id.getNameButton).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Bind to the remote service
        Intent intent = new Intent();
        intent.setClassName("np.com.ngimasherpa.serverapp", "np.com.ngimasherpa.serverapp.RemoteService");

        this.bindService(intent, this.connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Unbind if it is bound to the service
        if (this.isBound) {
            this.unbindService(connection);
            this.isBound = false;
        }
    }

    @Override
    public void onClick(View v) {
        int what = -1;
        switch (v.getId()) {
            case R.id.getNameButton:
                what = KEY_NAME;
                break;
            case R.id.getCastButton:
                what = KEY_CAST;
                break;
        }

        if (MainActivity.this.isBound) {
            //Setup the message for invocation
            Message message = Message.obtain(null, what, 0, 0);
            try {
                //Set the ReplyTo Messenger for processing the invocation response
                message.replyTo = MainActivity.this.replyTo;

                //Make the invocation
                MainActivity.this.messenger.send(message);
            } catch (RemoteException rme) {
                //Show an Error Message
                Toast.makeText(MainActivity.this, "Invocation Failed!!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Service is Not Bound!!", Toast.LENGTH_LONG).show();
        }
    }

    private class RemoteServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName component, IBinder binder) {
            MainActivity.this.messenger = new Messenger(binder);

            MainActivity.this.isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName component) {
            MainActivity.this.messenger = null;

            MainActivity.this.isBound = false;
        }
    }

    private class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case KEY_NAME:
                    Toast.makeText(MainActivity.this.getApplicationContext(), "NAME from server : (" + msg.getData().getString(KEY_MESSAGE) + ")", Toast.LENGTH_LONG).show();

                    break;

                case KEY_CAST:
                    Toast.makeText(MainActivity.this.getApplicationContext(), "CAST from server : (" + msg.getData().getString(KEY_MESSAGE) + ")", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
