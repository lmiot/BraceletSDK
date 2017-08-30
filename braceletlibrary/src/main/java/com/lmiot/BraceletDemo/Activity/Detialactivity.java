package com.lmiot.BraceletDemo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lmiot.BraceletDemo.Bean.RateDataInfo;
import com.lmiot.BraceletDemo.Database.BraceletDBManager;
import com.lmiot.BraceletDemo.R;
import com.lmiot.BraceletDemo.Util.LogUtils;

import java.util.List;


public class Detialactivity extends Activity implements View.OnClickListener {



    private BraceletDBManager manager;
    private List<RateDataInfo> allRate;
    private String sport_type;
    private String start_time;
    private String end_time;
    private String step_num;
    private String heat_num;
    private RateDataInfo mRateDataInfo_data;

    Detailadter detailadter;
    String url_getRateData = "192.168.1.100";
    private String mBydate;
    private ImageView mIdBack;
    private TextView mIdTitle;
    private ListView mIdRateDetaidListview;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detialactivity);
        mBydate = getIntent().getStringExtra("bydate");
        initView();
        intiDB();

        GetAllData(mBydate);


    }


    /**
     * 设置页头
     */
    private void initView() {
        mIdBack = findViewById(R.id.id_back);
        mIdTitle = findViewById(R.id.id_title);
        mIdRateDetaidListview = findViewById(R.id.id_rate_detaid_listview);
        mIdTitle.setText(mBydate);
        mIdBack.setOnClickListener(this);
    }

    /**
     * 获取当天的所有数据
     */
    private void GetAllData(String date) {

        allRate = manager.findAllRate(date);
        LogUtils.d("所有数据：", allRate.toString());
        if (allRate != null) {
            if (detailadter != null) {
                detailadter.setData(allRate);
            } else {
                detailadter = new Detailadter(allRate);
                mIdRateDetaidListview.setAdapter(detailadter);
            }
        }


		/*点击时跳到运动详情页面*/
        mIdRateDetaidListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Detialactivity心率详情", allRate.get(position).toString());
                mRateDataInfo_data = allRate.get(position);
                Intent intent_heart = new Intent(Detialactivity.this, HeartRateDatailActivity.class);
                intent_heart.putExtra("sport_type", mRateDataInfo_data.getSport_type());//运动类型
                intent_heart.putExtra("start_time", mRateDataInfo_data.getStart_time());//测试的开始时间
                intent_heart.putExtra("end_time", mRateDataInfo_data.getEnd_time());//测试的结束时间
                intent_heart.putExtra("sport_time", mRateDataInfo_data.getSport_time());//运动时间
                intent_heart.putExtra("heart_num", mRateDataInfo_data.getHeat_num());//消耗热量
                intent_heart.putExtra("step_num", mRateDataInfo_data.getStep_num());//步数
                intent_heart.putExtra("average_rate", mRateDataInfo_data.getAverage_rate());//平均心率
                intent_heart.putExtra("average_speed", mRateDataInfo_data.getAverage_speed());//平均速度
                startActivity(intent_heart);

            }
        });

    }


    private void intiDB() {
        manager = new BraceletDBManager(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.id_back){
            finish();
        }
    }


    public class Detailadter extends BaseAdapter {
        List<RateDataInfo> allRate;

        public Detailadter(List<RateDataInfo> allRate) {
            this.allRate = allRate;
        }

        public void setData(List<RateDataInfo> allRate) {
            this.allRate = allRate;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return allRate.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (null == convertView) {
                viewHolder = new ViewHolder();
                LayoutInflater mInflater = LayoutInflater.from(Detialactivity.this);
                convertView = mInflater.inflate(R.layout.listitem_detailactivty, null);
                viewHolder.sporttype = (TextView) convertView.findViewById(R.id.id_tv_detailac_sport);
                viewHolder.starttime = (TextView) convertView.findViewById(R.id.id_tv_detailac_starttime);
                viewHolder.endtime = (TextView) convertView.findViewById(R.id.id_tv_detailac_endtime);
                viewHolder.tatalpacenum = (TextView) convertView.findViewById(R.id.id_tv_detailac_pacenum);
                viewHolder.tatalheatnum = (TextView) convertView.findViewById(R.id.id_tv_detailac_heatnum);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.sporttype.setText(allRate.get(position).getSport_type());
            viewHolder.starttime.setText(allRate.get(position).getStart_time());
            viewHolder.endtime.setText(allRate.get(position).getEnd_time());
            viewHolder.tatalpacenum.setText("总步数：" + allRate.get(position).getHeat_num());
            viewHolder.tatalheatnum.setText("热量消耗：" + allRate.get(position).getStep_num());

            return convertView;
        }

    }

    static class ViewHolder {
        TextView sporttype;
        TextView starttime;
        TextView endtime;
        TextView tatalpacenum;
        TextView tatalheatnum;
    }


}
