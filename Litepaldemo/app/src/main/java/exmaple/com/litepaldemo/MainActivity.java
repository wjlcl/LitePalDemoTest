package exmaple.com.litepaldemo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_CreateDB, btn_AddDT, btn_ModifyDT, btn_QueryDT, btn_clearDT;
    private TextView textV;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = new Date();

        initView();

        btn_CreateDB.setOnClickListener(this);
        btn_AddDT.setOnClickListener(this);
        btn_ModifyDT.setOnClickListener(this);
        btn_QueryDT.setOnClickListener(this);

        btn_clearDT.setOnClickListener(this);
    }

    private void initView() {
        btn_CreateDB = (Button) findViewById(R.id.btn_crateDB);
        btn_AddDT = (Button) findViewById(R.id.btn_AddData);
        btn_ModifyDT = (Button) findViewById(R.id.btn_ModifyData);
        btn_QueryDT = (Button) findViewById(R.id.btn_QueryData);
        textV = (TextView) findViewById(R.id.text_show);
        btn_clearDT = (Button) findViewById(R.id.btn_clearData);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_crateDB:
                Log.d("TAG", "创建数据库");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase sqlDB = Connector.getDatabase();
                    }
                }).start();
                break;

            case R.id.btn_AddData:
                Log.d("TAG", "添加数据");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        News news;
                        news = new News();
                        news.setTitle("lcl1");
                        news.setContent("测试");
                        news.setCommentCount(5);
                        news.setPublishDate(date);
                        news.save();
                    }
                }).start();

                break;
            case R.id.btn_ModifyData:
                Log.d("TAG", "修改数据");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        News news;
                        news = new News();
                        news.setContent("测试2");
                        news.update(36);
                    }
                }).start();


                break;
            case R.id.btn_QueryData:
                Log.d("TAG", "查询数据");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        textV.post(new Runnable() {
                            @Override
                            public void run() {
                                textV.setText("");
                            }
                        });
                        List<News> list_news = DataSupport.findAll(News.class);
                        final StringBuffer sb = new StringBuffer();
                        for (News news2 : list_news) {
                            sb.append("ID：").append(String.valueOf(news2.getId()));
                            sb.append("数量：").append(news2.getCommentCount());
                            if (news2.getPublishDate() != null) {
                                sb.append("日期：").append(news2.getPublishDate().toString());
                            }
                            sb.append("标题：").append(news2.getTitle());
                            sb.append("内容：").append(news2.getContent()).append("\n");
//
                            textV.post(new Runnable() {
                                @Override
                                public void run() {
                                    textV.setText(sb.toString());
                                }
                            });
                        }
                    }
                }).start();

                break;
            case R.id.btn_clearData:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataSupport.deleteAll(News.class);
                    }
                }).start();

                break;

        }
    }
}
