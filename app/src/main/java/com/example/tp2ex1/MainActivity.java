package com.example.tp2ex1;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "DEBUG";
    private View box;
    private View rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        box = findViewById(R.id.box);
        rootLayout = findViewById(R.id.rootLayout);

        // Correctement définir le listener
        rootLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(DEBUG_TAG, "Action was DOWN");

                        float touchX = event.getX();
                        float touchY = event.getY();

                        // Vérifie si le toucher est à l'intérieur du carré
                        if (touchX >= box.getX() && touchX <= box.getX() + box.getWidth()
                                && touchY >= box.getY() && touchY <= box.getY() + box.getHeight()) {

                            Log.d(DEBUG_TAG, "Touch was inside the box");

                            // On déplace le carré à la position du doigt, centré
                            box.setX(touchX - box.getWidth() / 2f);
                            box.setY(touchY - box.getHeight() / 2f);

                            // On autorise le mouvement
                            return true;

                        } else {
                            Log.d(DEBUG_TAG, "Touch was outside the box");
                        }

                        return false; // on ne consomme pas l'événement si on n'a pas touché le carré

                    case MotionEvent.ACTION_MOVE:
                        Log.d(DEBUG_TAG, "Action was MOVE");
                        // On déplace le carré à la position du doigt
                        box.setX(event.getX() - (float) box.getWidth() / 2);
                        box.setY(event.getY() - (float) box.getHeight() / 2);

                        return true;
                    case MotionEvent.ACTION_UP:
                        Log.d(DEBUG_TAG, "Action was UP");
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        Log.d(DEBUG_TAG, "Action was CANCEL");
                        return true;
                    case MotionEvent.ACTION_OUTSIDE:
                        Log.d(DEBUG_TAG, "Movement occurred outside bounds of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}
