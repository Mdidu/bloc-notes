package com.example.blocnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String br = "<br />";
    private final String bOpen = "<b>";
    private final String bClose = "</b>";
    private final String iOpen = "<i>";
    private final String iClose = "</i>";
    private final String uOpen = "<u>";
    private final String uClose = "</u>";
    private String color;
    private boolean bClick = false;
    private boolean iClick = false;
    private boolean uClick = false;
    private boolean hide = false;

    private Button bold = null;
    private Button italic = null;
    private Button underline = null;
    private Button toHide = null;
    private RadioGroup radioGroup = null;
    private EditText edit = null;
    private TextView result = null;

    private ImageButton smile = null;
    private ImageButton happy = null;
    private ImageButton clin = null;

    private SmileyGetter getter = null;

    private LinearLayout menu = null;
    private TranslateAnimation animation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getter = new SmileyGetter(this);

        menu = (LinearLayout)findViewById(R.id.menu);
        toHide = (Button)findViewById(R.id.visibility);
        bold = (Button)findViewById(R.id.bold);
        italic = (Button)findViewById(R.id.italic);
        underline = (Button)findViewById(R.id.underline);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        edit = (EditText)findViewById(R.id.edit);
        result = (TextView)findViewById(R.id.result);

        smile = (ImageButton)findViewById(R.id.smile);
        happy = (ImageButton)findViewById(R.id.happy);
        clin = (ImageButton)findViewById(R.id.clin);

        smile.setOnClickListener(smileListener);
        happy.setOnClickListener(happyListener);
        clin.setOnClickListener(clinListener);

        toHide.setOnClickListener(toHideListener);
        bold.setOnClickListener(boldListener);
        italic.setOnClickListener(italicListener);
        underline.setOnClickListener(underlineListener);

        edit.setOnKeyListener(keyListener);
        edit.addTextChangedListener(textWatcher);
    }

    private View.OnClickListener toHideListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(hide) {
                // affiche le menu
                animation = new TranslateAnimation(0, 0,
                        -menu.getHeight(), 0);
                menu.startAnimation(animation);
                menu.setVisibility(v.VISIBLE);
                toHide.setText(R.string.show);
                hide = false;
            } else {
                // masque le menu
                animation = new TranslateAnimation(0, 0,
                        0, -menu.getHeight());
                menu.startAnimation(animation);
                menu.setVisibility(v.GONE);
                toHide.setText(R.string.hide);
                hide = true;
            }
        }
    };


    // gestion effet de style texte
    /**
     *
     * @param open String
     * @param close String
     * @param click boolean
     * @return boolean
     */
    private boolean openAndCloseBalise(String open, String close, boolean click) {

        if(!click) {
            edit.getText().insert(edit.getSelectionStart(), open);
            return true;
        } else {
            edit.getText().insert(edit.getSelectionStart(), close);
            return false;
        }
    }
    private View.OnClickListener boldListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            bClick = openAndCloseBalise(bOpen, bClose, bClick);
        }
    };
    private View.OnClickListener italicListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            iClick = openAndCloseBalise(iOpen, iClose, iClick);
        }
    };
    private View.OnClickListener underlineListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            uClick = openAndCloseBalise(uOpen, uClose, uClick);
        }
    };

    //gestion affichage des smiley
    /**
     *
     * @param str String
     */
    private void addSmiley(String str){
        edit.getText().insert(edit.getSelectionStart(), "<img src=\"" + str + "\">");
    }
    private View.OnClickListener smileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addSmiley("smile");
        }
    };
    private View.OnClickListener happyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addSmiley("happy");
        }
    };
    private View.OnClickListener clinListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addSmiley("clin");
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(radioGroup.getCheckedRadioButtonId() == R.id.blue) {
                color = "#0000FF";
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.red) {
                color = "#FF0000";
            } else {
                color = "#000000";
            }
            result.setText(Html.fromHtml("<font color=\"" + color + "\">" +
                    edit.getText().toString() + "</font>", getter, null));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private View.OnKeyListener keyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            // pour ne pas agir 2 fois ( enfoncement et relachement)
            if(event.getAction() == 0) {
                //keyCode touch Enter
                if(keyCode == 66) {
                    edit.getText().insert(edit.getSelectionStart(), br);
                }
            }
            return false;
        }
    };
}