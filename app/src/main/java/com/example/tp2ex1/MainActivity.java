package com.example.tp2ex1;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "DEBUG";
    private View box;
    private View rootLayout;

    private GestureDetector gestureDetector;
    private boolean isDragging = false; // pour savoir si on est en train de déplacer la boîte

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        box = findViewById(R.id.box);
        rootLayout = findViewById(R.id.rootLayout);

        // Crée le GestureDetector
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(@NonNull MotionEvent event) {
                float touchX = event.getX();
                float touchY = event.getY();

                // Vérifie si le toucher est dans la boîte
                if (touchX >= box.getX() && touchX <= box.getX() + box.getWidth()
                        && touchY >= box.getY() && touchY <= box.getY() + box.getHeight()) {

                    Log.d(DEBUG_TAG, "onDown: Touch started inside the box");
                    isDragging = true; // commence le drag
                    return true; // on consomme l'événement
                }
                isDragging = false;
                return false; // toucher en dehors de la boîte
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (isDragging) {
                    // Déplace la boîte en inversant distanceX/distanceY pour que ça suive le doigt
                    box.setX(box.getX() - distanceX);
                    box.setY(box.getY() - distanceY);
                    return true; // on consomme l'événement
                }
                return false;
            }
        });

        // Applique le GestureDetector sur le layout racine
        rootLayout.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }
}
