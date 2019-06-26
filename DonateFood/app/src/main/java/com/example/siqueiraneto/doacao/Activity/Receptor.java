package com.example.siqueiraneto.doacao.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Receptor extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myintent = new Intent(context, LiderService.class);
        context.startService(myintent);
    }
}
