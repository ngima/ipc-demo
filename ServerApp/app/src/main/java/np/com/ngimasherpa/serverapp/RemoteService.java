package np.com.ngimasherpa.serverapp;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

/**
 * @author Ngima Sherpa
 */
public class RemoteService extends Service {

    private final static int KEY_NAME = 1;
    private final static int KEY_CAST = 2;
    private final static String KEY_MESSAGE = "message";

    private Messenger messenger; //receives remote invocations

    @Override
    public IBinder onBind(Intent intent) {
        if (this.messenger == null) {
            synchronized (RemoteService.class) {
                if (this.messenger == null) {
                    this.messenger = new Messenger(new IncomingHandler());
                }
            }
        }
        //Return the proper IBinder instance
        return this.messenger.getBinder();
    }

    private class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            Message message = Message.obtain(null, msg.what, 0, 0);
            Bundle data = new Bundle();

            switch (msg.what) {
                case KEY_NAME:
                    data.putString(KEY_MESSAGE, DataUtils.getName(RemoteService.this));
                    Toast.makeText(RemoteService.this.getApplicationContext(), "Remote Service invoked-(NAME)", Toast.LENGTH_LONG).show();

                    break;

                case KEY_CAST:
                    data.putString(KEY_MESSAGE, DataUtils.getCast(RemoteService.this));
                    Toast.makeText(RemoteService.this.getApplicationContext(), "Remote Service invoked-(CAST)", Toast.LENGTH_LONG).show();
                    break;
            }


            //Setup the reply message
            message.setData(data);

            try {
                //make the RPC invocation
                Messenger replyTo = msg.replyTo;
                replyTo.send(message);
            } catch (RemoteException rme) {
                //Show an Error Message
                Toast.makeText(RemoteService.this, "Invocation Failed!!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
