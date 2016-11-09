package demo.text.alpha.yibo.com.textalphademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.fadeyTextView);
        textView.setText("每天看我一眼，心情好一天每天看我一眼，心情好一天每好一天每天看我一眼，心情好一天每好一天每天看我一眼，心情好一天每好一天每天看我一眼，心情好一天每好一天每天看我一眼，心情好一天每好一天每天看我一眼，心情好一天");
    }

    public void click(View view) {
        textView.setText("每天看我一眼，心情好一天每天看我一眼，心情好一天每好一天每天看我一眼，心情好一天每好一天每天看我一眼，心情好一天每好一天每天看我一眼，心情好一天每好一天每天看我一眼，心情好一天每好一天每天看我一眼，心情好一天");
    }
}
