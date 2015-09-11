package com.ivoryworks.pgma;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;

public class CustomTestRunner extends AndroidJUnitRunner {
    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        if (arguments != null) {
            String loop = arguments.getString("loop", "1");
            System.setProperty("loop", loop);
        }
    }
}
