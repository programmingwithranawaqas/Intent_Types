package com.example.intent_types;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ShowFragments extends AppCompatActivity {

    View portrait, landscape;

    FragmentManager fragmentManager;

    Fragment names_frag, detail_frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_fragments);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        if(landscape==null)
        {

            fragmentManager
                    .beginTransaction()
                    .show(names_frag)
                    .hide(detail_frag)
                    .commit();
        }
        else
        {
            Toast.makeText(this, "Landscape Mode", Toast.LENGTH_SHORT).show();
            fragmentManager
                    .beginTransaction()
                    .show(names_frag)
                    .show(detail_frag)
                    .commit();
        }
    }

    private void init()
    {
        portrait = findViewById(R.id.portrait);
        landscape = findViewById(R.id.landscape);
        fragmentManager = getSupportFragmentManager();
        names_frag = fragmentManager.findFragmentById(R.id.names_fragment);
        detail_frag = fragmentManager.findFragmentById(R.id.detail_fragment);
    }
}