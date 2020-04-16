/**
 * Copyright (c) {2003,2011} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package np.com.ngimasherpa.serverapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @author Ngima Sherpa
 */

public class MainActivity extends Activity {
    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((EditText) findViewById(R.id.nameTextView)).setText(DataUtils.getName(this));
        ((EditText) findViewById(R.id.castTextView)).setText(DataUtils.getCast(this));

        ((EditText) findViewById(R.id.nameTextView)).addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        DataUtils.saveName(MainActivity.this, s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
        );
        ((EditText) findViewById(R.id.castTextView)).addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        DataUtils.saveCast(MainActivity.this, s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
        );
    }
}
