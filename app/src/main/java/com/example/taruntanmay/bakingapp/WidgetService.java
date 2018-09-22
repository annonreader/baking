package com.example.taruntanmay.bakingapp;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new RemoteService(this.getApplicationContext(),intent));
    }
}