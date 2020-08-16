package com.example.ajay.a_step;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class TermConditions extends Activity {

    CheckBox accept;
    View view;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_conditions);
        WebView webview = (WebView)this.findViewById(R.id.textView2);
        webview.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        accept = (CheckBox) findViewById(R.id.checkBox);
        ok = (Button) findViewById(R.id.button);
        ok.setEnabled(false);
        ok.setTextColor(getResources().getColor(android.R.color.white));
        ok.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        String text;
        text = "<html><body><p align=\"\">";
        text+= "<u><h3>Licence Agreement</h3>\n</u>" +
                "        <b>1.Accepting the Terms</b>\n" +
                "                    </br><b>1.1</b> In order to use the application, you must first agree to the terms. You may not use the application if you do not accept the Terms.\n" +
                "                    </br><b>1.2</b> You can accept the Terms by:\n" +
                "                     </br>(A) clicking to accept or agree to the Terms, where this option is made available to you in the user interface (Agree checkbox).\n" +
                "                     </br>(B) by actually using the software. In this case, you understand and agree that your use of the software will be treated as acceptance of the Terms from that point onwards.\n" +
                "\n" +
                "        </br></br><b>2. Provision of the Application</b>\n" +
                "\n" +
                "                    </br><b>2.1</b> A-Step team is constantly innovating in order to provide the best possible experience for its users. You acknowledge and agree\n" +
                "                    that the form and nature of the software may change from time to time without prior notice to you.\n" +
                "\n" +
                "                    </br><b>2.2</b> As part of this continuing innovation, you acknowledge and agree that A-Step team may stop (permanently or temporarily) providing\n" +
                "                    the Services (or any features within the Services) to you or to users generally without prior notice to you. You may stop using the\n" +
                "                    software at any time. You do not need to specifically inform A-Step team when you stop using the software.\n" +
                "\n" +
                "    </br></br><b>3. Use of the Software by you</b>\n" +
                "    </br><b>3.1</b> You may be required to provide information about yourself (such as identification or contact details) as part of the registration\n" +
                "    process , or as part of your continued use of the software. You agree that any registration information you give to A-Step team will\n" +
                "    always be accurate, correct and up to date. A-Step may use an email address associated with current device user account to send\n" +
                "    latest updates notices, including any notices required bylaw and identify cloud related services.\n" +
                "\n" +
                "   </br> <b>3.2</b> You agree that you will not engage in any activity that interferes with or disrupts the software (or the servers and networks\n" +
                "which are connected to the software).\n" +
                "\n" +
                "</br><b>3.3</b> You agree that you will not reproduce, duplicate, copy. sell, trade or resell the software for any purpose.\n" +
                "\n" +
                "</br><b>3.4</b> You agree that you are solely responsible for (and that A-Step team has no responsibility to you or to any third party for) any\n" +
                "breach of your obligations under the Terms and for the consequences (including any loss or damage) of any such breach.\n" +
                "\n" +
                " </br></br><b>4. LIMITATION OF LIABILITY</b>\n" +
                "\n" +
                "                    </br>YOU EXPRESSLY UNDERSTAND AND AGREE THAT A-Step TEAM, ITS SUBSIDIARIES AND AFFILIATES SHALL NOT BE LIABLE TO YOU FOR:\n" +
                "                   </br> <b>A.</b> ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL CONSEQUENTIAL OR EXEMPLARY DAMAGES WHICH MAY BE INCURRED BY YOU.\n" +
                "                    </br><b>B.</b> THIS APPLICATION IS 3 WITH NO WARRANTY.\n" +
                "                   </br> <b>C.</b> USING THIS SOFTWARE IS DEPENDING SOLELY OF YOUR JUDJEMENT OF YOUR\n" +
                "                   ABILITIES AND CURRENT PHYSICAL AND MENTAL STATE.</br></br><b></br></br><b>";
        webview.loadData(text, "text/html", "utf-8");


        accept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ok.setEnabled(true);
                    ok.setTextColor(getResources().getColor(android.R.color.white));
                    ok.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                } else {
                    ok.setEnabled(false);
                    ok.setTextColor(getResources().getColor(android.R.color.white));
                    ok.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                }
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TermConditions.this, Registraction.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();

            }
        });
    }
}

