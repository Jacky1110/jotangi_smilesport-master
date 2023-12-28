package com.lafresh.smile2.Pay.View;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.Pay.Adapter.PayCardListAdapter;
import com.lafresh.smile2.Pay.Contract.PayContract;
import com.lafresh.smile2.Pay.Presenter.PayPresenter;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.PayCard;
import com.lafresh.smile2.Repository.Model.PayCardBarcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PayQrBarcodeFragment extends BaseFragment implements PayContract.View{
    private static PayQrBarcodeFragment fragment;
    private ImageView img_pay_qrcode,img_pay_barcode,bt_barcode_time_reset;
    private LinearLayout linear_card_list;
    private TextView tv_barcode_time,tv_transno;
    private Spinner spinner_card_list;
    private float defaultScreenValue = 0.5f;
    private int minutes = 5,seconds = 0;
    private Timer timer;
    private PayPresenter payPresenter;
    private List<PayCard> cardData = new ArrayList<>();
    private int selectedCard = 0;
    private String currentTime="0:0";
    private String currentTransNo = "000000000000000000";
    private String currentBarCode = "";
    public static PayQrBarcodeFragment getInstance(){
        if(fragment == null){
            fragment = new PayQrBarcodeFragment();
        }
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_qrbarcode,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        payPresenter = new PayPresenter(this,getRepositoryManager(getContext()));
        cardData = PayFragment.getCardData();
        ((MainActivity)getActivity()).setAppTitle(R.string.tab_pay_card_barcode);
        ((MainActivity)getActivity()).setBackButtonVisibility(true);
        ((MainActivity)getActivity()).setMessageButtonVisibility(false);
        ((MainActivity)getActivity()).setMailButtonVisibility(false);
        ((MainActivity)getActivity()).setSortButtonVisibility(false);
        linear_card_list = view.findViewById(R.id.linear_card_list);
        img_pay_qrcode = view.findViewById(R.id.img_pay_qrcode);
        img_pay_barcode = view.findViewById(R.id.img_pay_barcode);
        img_pay_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PayQrBarcodeBiggerFragment payQrBarcodeBiggerFragment = PayQrBarcodeBiggerFragment.getInstance();
                Bundle bundle = new Bundle();
                bundle.putString("time",currentTime);
                bundle.putString("transNo",currentTransNo);
                bundle.putString("barcode",currentBarCode);
                bundle.putString("cardToken",cardData.get(selectedCard).token);
                payQrBarcodeBiggerFragment.setArguments(bundle);
                ((MainActivity)getActivity()).addFragment(payQrBarcodeBiggerFragment);
            }
        });
        spinner_card_list = view.findViewById(R.id.spinner_card_list);
        bt_barcode_time_reset = view.findViewById(R.id.bt_barcode_time_reset);
        bt_barcode_time_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payPresenter.getBarcode(cardData.get(selectedCard).token);
            }
        });
        tv_barcode_time = view.findViewById(R.id.tv_barcode_time);
        tv_transno = view.findViewById(R.id.tv_transno);
    }

    private void timeStart(){
        minutes = 5;
        seconds = 1;
        TimerTask task = new TimerTask() {
            String time = "05:00";
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
                            payPresenter.getBarcode(cardData.get(selectedCard).token);
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
                        currentTime = time;
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

        PayCardListAdapter payCardListAdapter = new PayCardListAdapter(getContext(),cardData);
        spinner_card_list.setAdapter(payCardListAdapter);
        spinner_card_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCard = i;
                payPresenter.getBarcode(cardData.get(selectedCard).token);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_card_list.setSelection(0,false);
        linear_card_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_card_list.performClick();
            }
        });
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
        String barCode = ((PayCardBarcode)barcodeData).barCode;
        String duration = ((PayCardBarcode)barcodeData).duration;
        if(transNo==null)
            Log.d("ddd_barcodeData","nnnnnnnnnnnnnnnnnnn");
        else {
            Log.d("ddd_barcodeData", barCode);
            Log.d("ddd_barcodeData", duration);
            tv_transno.setText(transNo + " (點擊條碼可放大)");
            currentTransNo = transNo;
            currentBarCode = barCode;
            timeStart();
            //set imageview_resource barcode qrcode
            img_pay_qrcode.setBackgroundColor(Color.WHITE);
            img_pay_barcode.setBackgroundColor(Color.WHITE);
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix qrMatrix = multiFormatWriter.encode(barCode,BarcodeFormat.QR_CODE,200,200);
                BitMatrix barMatrix = multiFormatWriter.encode(barCode, BarcodeFormat.CODE_39, 474, 100);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap qrcodeBitmap = barcodeEncoder.createBitmap(qrMatrix);
                Bitmap barcodeBitmap = barcodeEncoder.createBitmap(barMatrix);
                img_pay_qrcode.setImageBitmap(qrcodeBitmap);
                img_pay_barcode.setImageBitmap(barcodeBitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }
}
