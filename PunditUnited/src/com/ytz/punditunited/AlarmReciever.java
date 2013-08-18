package com.ytz.punditunited;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReciever extends BroadcastReceiver {

	@Override
    public void onReceive(Context context, Intent intent)
    {
		ParseCloud.callFunctionInBackground("updateScore", null,
				new FunctionCallback<String>() {
					public void done(String result, ParseException e) {
						if (e == null) {
							
						}
					}
				});
     }
}
