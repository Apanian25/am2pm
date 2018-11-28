package com.dawson.jonat.stockers;

import android.app.Activity;
import android.os.Bundle;

import com.dawson.jonat.stockers.Menu.Menus;

public class MainActivity extends Menus {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
