package com.lafresh.smile2.MemberCenter.View;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.HttpPost;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.MemberBean;
import com.lafresh.smile2.MemberCenter.MemberInfoChangeContract;
import com.lafresh.smile2.MemberCenter.Presenter.MemberInfoChangePresenter;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Address;
import com.lafresh.smile2.Repository.Model.User;
import com.lafresh.smile2.Repository.RepositoryManager;
import com.lafresh.smile2.Utils.ViewUtils;
import com.lafresh.smile2.Widget.SpinnerDatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MemberInfoChangeFragment extends BaseFragment implements MemberInfoChangeContract.View, View.OnClickListener {
    public static final String TAG = MemberInfoChangeFragment.class.getSimpleName();

    private static MemberInfoChangeFragment fragment;
    private RadioGroup radioGender;
    private TextView tvName, tvBirth, tvPhone, btnSend, btnDelete;
    private EditText etMail, etAddress, password;
    private Spinner spinner_city, spinner_area;
    private Dialog dialog;
    private View viewdialog;
    private Button ok, cancel;
    private SharedPreferences pref;
    private ArrayAdapter<String> CitySpinnerList, AreaSpinnerList;
    protected RepositoryManager repositoryManager;


    private Context context;

    private MemberInfoChangeContract.Presenter memberPresenter;

    public static MemberInfoChangeFragment getInstance() {
        if (fragment == null) {
            fragment = new MemberInfoChangeFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_info_change, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.title_member_info_change);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);


        spinner_city = view.findViewById(R.id.spinner_city);
        spinner_area = view.findViewById(R.id.spinner_area);


        radioGender = view.findViewById(R.id.group_gender);
        tvName = view.findViewById(R.id.tv_name);
        tvBirth = view.findViewById(R.id.tv_birth);
        tvPhone = view.findViewById(R.id.tv_phone);
        etMail = view.findViewById(R.id.et_mail);
        etAddress = view.findViewById(R.id.et_address);

        btnSend = view.findViewById(R.id.tv_send);
        btnSend.setOnClickListener(this);
        btnDelete = view.findViewById(R.id.tv_delete);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        memberPresenter = new MemberInfoChangePresenter(this, getRepositoryManager(getContext()));
        memberPresenter.getPropertyData();

    }

    @Override
    public void setUser(User user) {
        tvName.setText(user.getName());

        if (user.getBirthday() != null) {
            if (user.getBirthday().equals("")) {
                tvBirth.setOnClickListener(this);
                tvBirth.setBackgroundResource(R.drawable.selec_et_line);
            } else {
                tvBirth.setText(user.getBirthday());
            }
        }

        tvPhone.setText(user.getMobile());
        etMail.setText(user.getEmail());
        etAddress.setText(user.getAddress());

        int spinnerCity = 0;
        if (user.getCitycode() != null) {
            if (!user.getCitycode().equals("")) {
                spinnerCity = CitySpinnerList.getPosition(user.getCitycode());
            }
        }

        spinner_city.setSelection(spinnerCity);

        if (user.getAreacode() != null) {
            getContext().getSharedPreferences("Location", Context.MODE_PRIVATE).edit()
                    .putString("Areacode", user.getAreacode())
                    .apply();
        }

//        if(user.getGender()!=null){
//            radioGender.check(user.getGender().equals("0") ? R.id.radio_female : R.id.radio_male);
//        }
    }

    @Override
    public void setPropertyCode(List<Address> addressList) {
        Map<String, ArrayList<String>> mList = new HashMap<>();
        final ArrayList<String> CityArrayList = new ArrayList<>();
        ArrayList<String> AreaArrayList = new ArrayList<>();

        CityArrayList.add("請選擇城市");
        AreaArrayList.add("請選擇地區");
        mList.put("請選擇城市", AreaArrayList);
        AreaArrayList = new ArrayList<>();

        int iCity = 1;
        for (int i = 0; i < addressList.size(); i++) {
            if (!CityArrayList.contains(addressList.get(i).getCity())) {
                if (AreaArrayList.size() != 0) {
                    mList.put(CityArrayList.get(iCity), AreaArrayList);
                    iCity++;
                }
                AreaArrayList = new ArrayList<>();
                CityArrayList.add(addressList.get(i).getCity());
            }
            //台北:["XXX","XXX"],台中:["XXX","XXX"]
            AreaArrayList.add(addressList.get(i).getZip() + " " + addressList.get(i).getArea());
        }
        mList.put(CityArrayList.get(iCity), AreaArrayList);

        //整理後的Address JSOMObject
        final JSONObject oAddress = new JSONObject(mList);
        CitySpinnerList = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_style,
                CityArrayList);
        spinner_city.setAdapter(CitySpinnerList);
        //點選下拉選單後觸發事件
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                JSONArray aArea = null;
                try {
                    aArea = oAddress.getJSONArray(CityArrayList.get(position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayList<String> AreaList = new ArrayList();
                for (int i = 0; i < aArea.length(); i++) {
                    try {
                        AreaList.add(aArea.getString(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                AreaSpinnerList = new ArrayAdapter<>(getActivity(),
                        R.layout.spinner_style,
                        AreaList);
                spinner_area.setAdapter(AreaSpinnerList);
                changeArea();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        memberPresenter.getMemberInfo();

    }

    private void changeArea() {
        String Areacode = getContext().getSharedPreferences("Location", Context.MODE_PRIVATE).getString("Areacode", "");
        int spinnerArea = AreaSpinnerList.getPosition(Areacode);
        spinner_area.setSelection(spinnerArea);
    }

    @Override
    public void showAlert(int messageId) {
        showAlert(getString(messageId));
    }

    @Override
    public void showAlert(String message) {
        new AlertDialog.Builder(getContext()).setTitle(R.string.dialog_hint)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public void setBirthDayModifiedOrNot(String message) {
        if (message.contains("修改成功")) {
            tvBirth.setOnClickListener(null);
            tvBirth.setBackgroundResource(0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
//                memberPresenter.updateMember(spinner_city.getSelectedItem().toString(), spinner_area.getSelectedItem().toString(), etAddress.getText().toString(), etMail.getText().toString(), radioGender.getCheckedRadioButtonId(), tvBirth.getText().toString());
                memberPresenter.updateMember(spinner_city.getSelectedItem().toString(), spinner_area.getSelectedItem().toString(), etAddress.getText().toString(), etMail.getText().toString(), tvBirth.getText().toString());
                break;
            case R.id.tv_delete:
                getDialog();
                break;
            case R.id.tv_birth:
                showDatePickerDialog();
        }
    }

    private void getDialog() {
        final String member = MemberBean.member_id;
        Log.d(TAG, "getDialog: ");
        pref = requireActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        final String pwd = pref.getString("password", "");
        Log.d(TAG, "pwd: " + pwd);
        Log.d(TAG, "member: " + MemberBean.member_id);
        dialog = new Dialog(requireActivity());

        viewdialog = getLayoutInflater().inflate(R.layout.dialog_member_delete, null);
        //然後把綁好的xml連接到dialog上面
        dialog.setContentView(viewdialog);
        //因為是自定義的子元件，後面綁定id記得都要綁上面的view
        password = viewdialog.findViewById(R.id.password);
        ok = viewdialog.findViewById(R.id.ok);
        cancel = viewdialog.findViewById(R.id.cancle);
        dialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getText().toString().trim().equals(pwd)) {
                    getMemberDele(member);
                    memberPresenter.logout();
                    dialog.dismiss();
                } else if (!password.getText().toString().trim().equals(pwd)) {
                    Toast.makeText(requireActivity(), "密碼不正確", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
    }

    private void getMemberDele(String account) {
        HttpPost httpPost = new HttpPost(context);

        httpPost.setOnPostBack(new HttpPost.Post_Call_Back() {
            @Override
            public void Success() {

            }

            @Override
            public void Fail() {

            }
        });

        httpPost.httprequest2(
                "https://2020smilesports.jotangi.net/api/Member/DeleteData",
                "{\"member_id\":\"" + account + "\",\"delete_flag\":\"Y\",\"modified_user\":\"" + account + "\"}"
        );


    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        DatePickerDialog datePickerDialog;
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            datePickerDialog = new SpinnerDatePickerDialog(getContext(), R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, final int i, final int i1, final int i2) {
                    new AlertDialog.Builder(getContext()).setTitle(R.string.dialog_hint)
                            .setTitle("生日設定後就不能修改")
                            .setMessage("為了維護您的權益，請確認日期無誤")
                            .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    tvBirth.setText(i + "-" + (i1 + 1) + "-" + i2);
                                }
                            })
                            .setNegativeButton(R.string.cancel, null)
                            .show();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            datePickerDialog = new DatePickerDialog(getContext(), R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, final int i, final int i1, final int i2) {
                    new AlertDialog.Builder(getContext()).setTitle(R.string.dialog_hint)
                            .setTitle("生日設定後就不能修改")
                            .setMessage("為了維護您的權益，請確認日期無誤")
                            .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    tvBirth.setText(i + "-" + (i1 + 1) + "-" + i2);
                                }
                            })
                            .setNegativeButton(R.string.cancel, null)
                            .show();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }

        ViewUtils.colorizeDatePicker(datePickerDialog.getDatePicker());
        datePickerDialog.getDatePicker().setSpinnersShown(true);
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


    @Override
    public void redirectToMainPage() {
        ((MainActivity) Objects.requireNonNull(getActivity())).setTabPage(0);
        ((MainActivity) Objects.requireNonNull(getActivity())).refreshBadge();
    }
}
