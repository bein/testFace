package com.asdan.testface;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.facebook.appevents.AppEventsLogger;

/**
 * Portada.java
 *
 * clase de entrada que muestra animación
 *
 * @author alberto.quirino
 */
public class Portada extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portada);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_portada);
        RelativeLayout fondo = (RelativeLayout) findViewById(R.id.RL_Portada);
        fondo.startAnimation(anim);

        class AnimationHandler implements Animation.AnimationListener {

            @Override
            public void onAnimationEnd(Animation animation) {
                Portada.this.startActivity(new Intent(Portada.this, Logeo.class));
                Portada.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
        }

        anim.setAnimationListener(new AnimationHandler());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        AppEventsLogger.deactivateApp(this);
    }
}
