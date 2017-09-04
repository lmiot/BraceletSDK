package com.lmiot.BraceletDemo.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lmiot.BraceletDemo.R;
import com.lmiot.BraceletDemo.Util.BlueToothUtils;
import com.lmiot.BraceletDemo.Util.DialogUtil;
import com.lmiot.BraceletDemo.Util.ToastUtil;

import java.util.ArrayList;
import java.util.List;



@SuppressLint("NewApi")
public class BluetoothScanActivity extends Activity implements View.OnClickListener {

    private Button mIdBtnSearch;
    private ListView mIdListview;
    private List<BluetoothDevice> mDeviceList;
    private DevAdapter mDevAdapter;
    private ImageView mIdBack;
    private TextView mIdTitle;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0x200:  //搜索到设备
                    BluetoothDevice device = (BluetoothDevice) msg.obj;

                    if (!FindDev(device.getAddress())) {
                        mDeviceList.add(device);
                    }

                    mDevAdapter.notifyDataSetChanged();
                    break;
                case 0x201: //搜索结束
                    DialogUtil.Hidden();
                    mIdBtnSearch.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_scan);
        intView();
        startScan();
    }


    private void intView() {
        mIdBack = findViewById(R.id.id_back);
        mIdTitle = findViewById(R.id.id_title);
        mIdBtnSearch = findViewById(R.id.id_btn_search);
        mIdListview = findViewById(R.id.id_listview);

        mIdTitle.setText("搜索设备");
        mIdBack.setOnClickListener(this);
        mIdBtnSearch.setOnClickListener(this);

    }


    /**
     * 判断是否已经有该设备
     *
     * @param address
     */
    private boolean FindDev(String address) {

        if (mDeviceList != null) {
            if (mDeviceList.size() > 0) {
                for (BluetoothDevice bd : mDeviceList) {
                    if (bd.getAddress().equals(address)) {
                        return true;
                    }

                }

            }
        }
        return false;
    }


    private void startScan() {
        DialogUtil.showDialog(BluetoothScanActivity.this);
        if (mDeviceList == null) {
            mDeviceList = new ArrayList<>();
        } else {
            mDeviceList.clear();
        }
        mDevAdapter = new DevAdapter();
        mIdListview.setAdapter(mDevAdapter);

        mIdBtnSearch.setVisibility(View.GONE);
        BlueToothUtils.scanDevice(BluetoothScanActivity.this, mHandler);

    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.id_back) {
            finish();

        } else if (i == R.id.id_btn_search) {
            startScan();
         /*   String address = "00:00:00:00:00:00";
            Intent intent = new Intent(BluetoothScanActivity.this, SunActivity.class);
            intent.putExtra("devID", address);
            startActivity(intent);*/


        }

    }


    private class DevAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mDeviceList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = View.inflate(BluetoothScanActivity.this, R.layout.listitem_device, null);
            TextView deviceName = (TextView) v.findViewById(R.id.device_name);
            TextView deviceAddress = (TextView) v.findViewById(R.id.device_address);
            final BluetoothDevice device = mDeviceList.get(i);
            deviceName.setText(device.getName() + "");
            deviceAddress.setText(device.getAddress());

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String address = device.getAddress();
                    Intent intent = new Intent(BluetoothScanActivity.this, SunActivity.class);
                    intent.putExtra("devID", address);
                    startActivity(intent);


                  /*  String name = device.getName();

                    if (!TextUtils.isEmpty(name)) {
                        if (name.startsWith("HRW")) {
                            String address = device.getAddress();
                            Intent intent = new Intent(BluetoothScanActivity.this, SunActivity.class);
                            intent.putExtra("devID", address);
                            startActivity(intent);

                        } else {
                            ToastUtil.ToastMessage(BluetoothScanActivity.this, "无法识别该设备！");
                        }
                    } else {
                        ToastUtil.ToastMessage(BluetoothScanActivity.this, "无法识别该设备  ！");
                    }*/
                }
            });

            return v;
        }


    }
}
