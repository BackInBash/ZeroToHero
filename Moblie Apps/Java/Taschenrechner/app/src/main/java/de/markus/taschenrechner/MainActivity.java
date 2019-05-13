package de.markus.taschenrechner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    StringBuilder value1 = new StringBuilder();
    StringBuilder value2 = new StringBuilder();
    char action = ' ';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView erg = findViewById(R.id.textView);

        // Values
        Button _1 = findViewById(R.id._1);
        _1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erg.append("1");

                if(action != ' '){
                    value2.append("1");
                } else {
                    value1.append("1");
                }
            }
        });
        Button _2 = findViewById(R.id._2);
        _2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erg.append("2");
                if(action != ' '){
                    value2.append("2");
                } else {
                    value1.append("2");
                }
            }
        });
        Button _3 = findViewById(R.id._3);
        _3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erg.append("3");
                if(action != ' '){
                    value2.append("3");
                } else {
                    value1.append("3");
                }
            }
        });
        Button _4 = findViewById(R.id._4);
        _4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erg.append("4");
                if(action != ' '){
                    value2.append("4");
                } else {
                    value1.append("4");
                }
            }
        });
        Button _5 = findViewById(R.id._5);
        _5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erg.append("5");
                if(action != ' '){
                    value2.append("5");
                } else {
                    value1.append("5");
                }
            }
        });
        Button _6 = findViewById(R.id._6);
        _6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erg.append("6");
                if(action != ' '){
                    value2.append("6");
                } else {
                    value1.append("6");
                }
            }
        });
        Button _7 = findViewById(R.id._7);
        _7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erg.append("7");
                if(action != ' '){
                    value2.append("7");
                } else {
                    value1.append("7");
                }
            }
        });
        Button _8 = findViewById(R.id._8);
        _8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erg.append("8");
                if(action != ' '){
                    value2.append("8");
                } else {
                    value1.append("8");
                }
            }
        });
        Button _9 = findViewById(R.id._9);
        _9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erg.append("9");
                if(action != ' '){
                    value2.append("9");
                } else {
                    value1.append("9");
                }
            }
        });
        Button _0 = findViewById(R.id._0);
        _0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erg.append("0");
                if(action != ' '){
                    value2.append("0");
                } else {
                    value1.append("0");
                }
            }
        });

        // Special
        Button del = findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erg.setText("");
                value1 = null;
                value1 = new StringBuilder();
                value2 = null;
                value2 = new StringBuilder();
                action = ' ';
            }
        });
        Button seperator = findViewById(R.id.seperatior);
        seperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erg.append(",");
                value1.append(".");
            }
        });

        // Operators
        Button div = findViewById(R.id.div);
        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(action != '/') {
                    erg.append("÷");
                }
                action = '/';
            }
        });
        Button multi = findViewById(R.id.multi);
        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(action != '*') {
                    erg.append("*");
                }
                action = '*';
            }
        });
        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(action != '+') {
                    erg.append("+");
                }
                action = '+';
            }
        });
        Button sub = findViewById(R.id.sub);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(action != '-') {
                    erg.append("-");
                }
                action = '-';
            }
        });

        // Erg
        Button res = findViewById(R.id.erg);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erg.setText("");
                double calc;
                switch(action){
                    case '+':
                         calc = (Double.parseDouble(value1.toString()))+(Double.parseDouble(value2.toString()));
                        erg.setText(Double.toString(calc));
                        break;
                    case '-':
                        calc = (Double.parseDouble(value1.toString()))-(Double.parseDouble(value2.toString()));
                        erg.setText(Double.toString(calc));
                        break;
                    case '*':
                        calc = (Double.parseDouble(value1.toString()))*(Double.parseDouble(value2.toString()));
                        erg.setText(Double.toString(calc));
                        break;
                    case '/':
                        calc = (Double.parseDouble(value1.toString()))/(Double.parseDouble(value2.toString()));
                        erg.setText(Double.toString(calc));
                        break;


                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
