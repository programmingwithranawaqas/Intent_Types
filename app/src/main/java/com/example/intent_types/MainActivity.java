package com.example.intent_types;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout llMainContainer;
    TextView tvError, tvName;
    Button btnCreateProfile;
    ImageView ivCall, ivMap, ivBrowser;
    String name, phone, address, url;

    ActivityResultLauncher<Intent> laucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        btnCreateProfile.setOnClickListener(this);
        ivCall.setOnClickListener(this);
        ivBrowser.setOnClickListener(this);
        ivMap.setOnClickListener(this);

    }

    private void init()
    {
        llMainContainer = findViewById(R.id.llMainContainer);
        btnCreateProfile = findViewById(R.id.btnCreateProfile);
        tvError = findViewById(R.id.tvError);
        tvName = findViewById(R.id.tvName);
        ivBrowser = findViewById(R.id.ivBrowser);
        ivCall = findViewById(R.id.ivCall);
        ivMap = findViewById(R.id.ivMap);
        llMainContainer.setVisibility(GONE);

        laucher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        Intent data = o.getData();
                        if(data!=null)
                        {
                            if(o.getResultCode()==RESULT_CANCELED)
                            {
                                tvError.setText("Add your information to proceed");
                            }
                            else
                            {
                                name = data.getStringExtra("key_name");
                                phone = data.getStringExtra("key_phone");
                                address = data.getStringExtra("key_address");
                                url = data.getStringExtra("key_url");

                                llMainContainer.setVisibility(VISIBLE);
                                tvName.setText(name);
                            }
                        }
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnCreateProfile)
        {
            laucher.launch(new Intent(MainActivity.this, Information.class));
        }
        else if(v.getId() == R.id.ivCall)
        {
            Intent i = new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:"+phone));
            startActivity(i);

        }
        else if(v.getId() == R.id.ivBrowser)
        {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        else if(v.getId() == R.id.ivMap)
        {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("geo:0,0?q="+address));
            startActivity(i);
        }
    }
}