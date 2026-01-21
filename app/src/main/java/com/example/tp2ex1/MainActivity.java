package com.example.tp2ex1;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "DEBUG";
    private View box;
    private GestureDetector gestureDetector;
    private boolean isDragging = false; // pour savoir si on est en train de déplacer la boîte

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        box = findViewById(R.id.box);
        View rootLayout = findViewById(R.id.rootLayout);

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
                    // Update the box position
                    box.setX(box.getX() - distanceX);
                    box.setY(box.getY() - distanceY);
                    return true;
                }
                return false;
            }
        });

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create dummy data
        List<String> data = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            data.add("Item " + i);
        }

        // Set Adapter
        MyAdapter adapter = new MyAdapter(data);
        recyclerView.setAdapter(adapter);

        // Applique le GestureDetector sur le RecyclerView
        recyclerView.setOnTouchListener((v, event) -> {
            Log.d(DEBUG_TAG, "onTouch RecyclerView");
            return gestureDetector.onTouchEvent(event);
        });
    }
}
