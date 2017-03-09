package com.atguigu.shopping.home.homefragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anye.greendao.gen.UserDao;
import com.atguigu.shopping.R;
import com.atguigu.shopping.home.activity.GoodsListActivity;
import com.atguigu.shopping.home.bean.SearchAdapter;
import com.atguigu.shopping.utils.JsonParser;
import com.atguigu.shopping.utils.User;
import com.atguigu.shopping.view.MyApplication;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

    @InjectView(R.id.tv_search)
    EditText tvSearch;
    @InjectView(R.id.iv_search_voice)
    ImageView ivSearchVoice;
    @InjectView(R.id.tv_search_go)
    TextView tvSearchGo;
    @InjectView(R.id.ll_hot_search)
    LinearLayout llHotSearch;
    @InjectView(R.id.hsl_hot_search)
    HorizontalScrollView hslHotSearch;
    @InjectView(R.id.lv_search)
    ListView lvSearch;
    @InjectView(R.id.btn_clear)
    Button btnClear;
    @InjectView(R.id.ll_history)
    LinearLayout llHistory;
    private User mUser;
    private UserDao mUserDao;
    private List<User> users;
    private SearchAdapter adapter;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);
        mUserDao = MyApplication.getInstances().getDaoSession().getUserDao();

        Adapter();
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5838f0d9");
    }

    @OnClick({R.id.iv_search_voice, R.id.tv_search_go, R.id.btn_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_voice:
//                Toast.makeText(this, "语音", Toast.LENGTH_SHORT).show();
                showDialogVoice();
                break;
            case R.id.tv_search_go:
                if (TextUtils.isEmpty(tvSearch.getText())) {
                    Toast.makeText(this, "所搜内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    mUser = new User(null, tvSearch.getText().toString());
                    mUserDao.insert(mUser);
                    Adapter();
                    Intent intent = new Intent(SearchActivity.this, GoodsListActivity.class);
                    startActivity(intent);
                    finish();


                }
                break;
            case R.id.btn_clear:
                mUserDao.deleteAll();
                Adapter();
                break;
        }
    }

    private void Adapter() {
        users = mUserDao.loadAll();
        adapter = new SearchAdapter(this, users);
        lvSearch.setAdapter(adapter);
    }

    private void showDialogVoice() {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(this, new MyInitListener());
        //2.设置accent、 language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        //若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
        //结果
        // mDialog.setParameter("asr_sch", "1");
        // mDialog.setParameter("nlp_version", "2.0");
        //3.设置回调接口
        mDialog.setListener(new MyRecognizerDialogListener());
        //4.显示dialog，接收语音输入
        mDialog.show();
    }

    class MyRecognizerDialogListener implements RecognizerDialogListener {

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            String result = recognizerResult.getResultString();
            System.out.println(result);
            String text = JsonParser.parseIatResult(recognizerResult.getResultString());

            String sn = null;
            // 读取json结果中的sn字段
            try {
                JSONObject resultJson = new JSONObject(recognizerResult.getResultString());
                sn = resultJson.optString("sn");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mIatResults.put(sn, text);

            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults.get(key));
            }
            String reulst = resultBuffer.toString();
            reulst = reulst.replace("。","");

            tvSearch.setText(reulst);
            tvSearch.setSelection(tvSearch.length());

        }

        @Override
        public void onError(SpeechError speechError) {

            Toast.makeText(SearchActivity.this, "出错了哦", Toast.LENGTH_SHORT).show();
        }
    }

    class MyInitListener implements InitListener {

        @Override
        public void onInit(int i) {


        }
    }
}
