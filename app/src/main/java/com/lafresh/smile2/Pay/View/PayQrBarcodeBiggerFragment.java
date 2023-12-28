package com.lafresh.smile2.Pay.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.Pay.Contract.PayContract;
import com.lafresh.smile2.Pay.Presenter.PayPresenter;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.PayCardBarcode;

import java.util.Timer;
import java.util.TimerTask;

public class PayQrBarcodeBiggerFragment extends BaseFragment implements PayContract.View {
    private static PayQrBarcodeBiggerFragment fragment;
    private float defaultScreenValue = 0.5f;
    private int minutes = 5,seconds = 0;
    private Timer timer;
    private ImageView img_pay_barcode,bt_barcode_time_reset;
    private TextView tv_barcode_time,tv_transno;
    private PayPresenter payPresenter;
    private String cardToken = "";
    public static PayQrBarcodeBiggerFragment getInstance(){
        if(fragment == null){
            fragment = new PayQrBarcodeBiggerFragment();
        }
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_qrbarcode_bigger,container,false);
        img_pay_barcode = view.findViewById(R.id.img_pay_barcode);
        bt_barcode_time_reset = view.findViewById(R.id.bt_barcode_time_reset);
        bt_barcode_time_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payPresenter.getBarcode(cardToken);
            }
        });
        tv_transno = view.findViewById(R.id.tv_transno);
        tv_barcode_time = view.findViewById(R.id.tv_barcode_time);
        payPresenter = new PayPresenter(this,getRepositoryManager(getContext()));

        Bundle bundle = getArguments();
        String lastTime = bundle.getString("time");
        String lastTransNo = bundle.getString("transNo");
        String lastBarCode = bundle.getString("barcode");
        cardToken = bundle.getString("cardToken");
        tv_transno.setText(lastTransNo);
        setBarCode(lastBarCode);
        timeStart(lastTime);

        return view;
    }

    private void setBarCode(String barCode){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix barMatrix = multiFormatWriter.encode(barCode, BarcodeFormat.CODE_39, 1600, 400);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap barcodeBitmap = barcodeEncoder.createBitmap(barMatrix);
            img_pay_barcode.setImageBitmap(barcodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void timeStart(final String cn){
        String[] timeSplit = cn.split(":");
        minutes = Integer.parseInt(timeSplit[0]);
        seconds = Integer.parseInt(timeSplit[1]);
        TimerTask task = new TimerTask() {
            String time = cn;
            @Override
            public void run() {
                if(minutes==0 && seconds==0){
                    ((MainActivity)getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(fragment.getActivity())
                                    .setTitle(R.string.dialog_hint)
                                    .setMessage("支付碼已失效，請重新操作")
                                    .setPositiveButton(android.R.string.ok, null).show();
                            timer.cancel();
                            payPresenter.getBarcode(cardToken);
                        }
                    });
                }else{
                    if(seconds==0){
                        seconds = 59;
                        minutes--;
                    }else
                        seconds--;
                }
                String cm = "0"+minutes;
                String cs = ""+seconds;
                if(seconds<10)
                    cs = "0"+cs;
                time = cm+":"+cs;
                ((MainActivity)getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_barcode_time.setText(time);
                    }
                });
            }
        };
        if(timer!=null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(task, 0, 1000);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setScreenLight();
    }

    @Override
    public void onPause() {
        super.onPause();
        setDefaultScreenLight();
        if(timer!=null) {
            timer.cancel();
        }
    }

    private void setScreenLight(){
        WindowManager.LayoutParams lp = ((MainActivity)getActivity()).getWindow().getAttributes();
        defaultScreenValue = lp.screenBrightness;
        lp.screenBrightness = 1.0f;
        ((MainActivity)getActivity()).getWindow().setAttributes(lp);
    }

    private void setDefaultScreenLight(){
        WindowManager.LayoutParams lp = ((MainActivity)getActivity()).getWindow().getAttributes();
        lp.screenBrightness = defaultScreenValue;
        ((MainActivity)getActivity()).getWindow().setAttributes(lp);
    }

    @Override
    public void setData(Object barcodeData) {
        String transNo = ((PayCardBarcode)barcodeData).transNo;
        String barcode = ((PayCardBarcode)barcodeData).barCode;
        timeStart("05:01");
        Log.d("ddd",transNo);
        setBarCode(barcode);
        tv_transno.setText(transNo);
    }
}
