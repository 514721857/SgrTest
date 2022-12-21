package com.sgr.dyg.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sgr.dyg.test.umeng.UPushAlias;
import com.sgr.dyg.test.umeng.UPushNotification;
import com.sgr.dyg.test.umeng.UPushTag;
import com.sgr.dyg.test.utils.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReviceMessageActivity extends AppCompatActivity {

    @BindView(R.id.edit_alias)
    EditText edit_alias;

    @BindView(R.id.btn_alias)
    Button btn_alias;

    @BindView(R.id.edit_tag)
    EditText edit_tag;

    @BindView(R.id.btn_tag)
    Button btn_tag;

    @BindView(R.id.btn_send)
    Button btn_send;

    @BindView(R.id.msg_title)
    TextView msg_title;

    @BindView(R.id.msg_content)
    TextView msg_content;

    @BindView(R.id.msg_extra)
    TextView msg_extra;

    String title,content,msg_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revice_message);
        ButterKnife.bind(this);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle!=null){
            title = bundle.getString("title");
            content = bundle.getString("text");
            msg_id = bundle.getString("msg_id");
            msg_title.setText(title);
            msg_content.setText(content);
            msg_extra.setText(msg_id);
        }

    }

    @OnClick({R.id.btn_alias, R.id.btn_tag,R.id.btn_send})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.btn_alias:
                if(edit_alias.getText().toString().equals("")){
                    MyUtils.myToast(ReviceMessageActivity.this,"请输入别名");
                }else{
                    setAlias(edit_alias.getText().toString());
                }

                break;
            case R.id.btn_tag:
                if(edit_tag.getText().toString().equals("")){
                    MyUtils.myToast(ReviceMessageActivity.this,"请输入标签");
                }else{
                    setTag(edit_tag.getText().toString());
                }
                break;
            case R.id.btn_send:
                sendMessage();
                break;
        }
    }
    //alias有效期为180天，所以要会重复设置 700677 技术科  700678ahk  700679 ahk 700680 安环科
    private void setAlias(String alias){
        UPushAlias.set(this, alias, "uid");
    }

    private void setTag(String tag){
        UPushTag.add(this, "全厂",tag);
    }

    private void sendMessage(){
        UPushNotification.send(getApplicationContext(), "来消息了", "这是标题", "这是内容，这是内容...");
//        AkoF_yNXAkIZA1Ru6uDRYeb9h0j9rhjc7e038hvB27gO
    }



}