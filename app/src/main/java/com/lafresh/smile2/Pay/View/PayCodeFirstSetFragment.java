package com.lafresh.smile2.Pay.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.lafresh.smile2.Base.BaseFragment;
import com.lafresh.smile2.Main.View.MainActivity;
import com.lafresh.smile2.R;

public class PayCodeFirstSetFragment extends BaseFragment {
    private static PayCodeFirstSetFragment fragment;
    private InputMethodManager inputMethodManager;
    EditText et_first,et_second,et_third,et_fourth,et_fifth,et_sixth;
    private String firstCode;
    public static PayCodeFirstSetFragment getInstance(){
        if(fragment == null){
            fragment = new PayCodeFirstSetFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_first_code,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        ((MainActivity)getActivity()).setAppTitle(R.string.tab_pay);
        ((MainActivity)getActivity()).setBackButtonVisibility(true);
        ((MainActivity)getActivity()).setMessageButtonVisibility(false);
        ((MainActivity)getActivity()).setMailButtonVisibility(false);
        ((MainActivity)getActivity()).setSortButtonVisibility(false);
        et_first = view.findViewById(R.id.et_first);
        et_second = view.findViewById(R.id.et_second);
        et_third = view.findViewById(R.id.et_third);
        et_fourth = view.findViewById(R.id.et_fourth);
        et_fifth = view.findViewById(R.id.et_fifth);
        et_sixth = view.findViewById(R.id.et_sixth);
        et_second.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    et_second.clearFocus();
                    et_first.setText("");
                    et_first.requestFocus();
                }
                return false;
            }
        });
        et_third.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    et_third.clearFocus();
                    et_second.setText("");
                    et_second.requestFocus();
                }
                return false;
            }
        });
        et_fourth.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    et_fourth.clearFocus();
                    et_third.setText("");
                    et_third.requestFocus();
                }
                return false;
            }
        });
        et_fifth.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==keyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    et_fifth.clearFocus();
                    et_fourth.setText("");
                    et_fourth.requestFocus();
                }
                return false;
            }
        });
        et_sixth.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==keyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    et_sixth.clearFocus();
                    et_fifth.setText("");
                    et_fifth.requestFocus();
                }
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).hideBottomNavigation();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).showBottomNavigation();
        clearCode();
    }

    private void clearCode(){
        et_first.setText("");
        et_second.setText("");
        et_third.setText("");
        et_fourth.setText("");
        et_fifth.setText("");
        et_sixth.setText("");
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_first.requestFocus();
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String code1 = et_first.getText().toString();
                String code2 = et_second.getText().toString();
                String code3 = et_third.getText().toString();
                String code4 = et_fourth.getText().toString();
                String code5 = et_fifth.getText().toString();
                String code6 = et_sixth.getText().toString();
                if(code1.length()==1){
                    et_first.clearFocus();
                    et_second.requestFocus();
                }
                if(code2.length()==1){
                    et_second.clearFocus();
                    et_third.requestFocus();
                }
                if(code3.length()==1){
                    et_third.clearFocus();
                    et_fourth.requestFocus();
                }
                if(code4.length()==1){
                    et_fourth.clearFocus();
                    et_fifth.requestFocus();
                }
                if(code5.length()==1){
                    et_fifth.clearFocus();
                    et_sixth.requestFocus();
                }
                if(code6.length()==1){
                    if(code1.length()==1&&code2.length()==1&&code3.length()==1&code4.length()==1&code5.length()==1) {
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        firstCode = code1+code2+code3+code4+code5+code6;
                        PayCodeSecondSetFragment secondCodeFragment = PayCodeSecondSetFragment.getInstance();
                        Bundle bundle = new Bundle();
                        bundle.putString("firstCode",firstCode);
                        secondCodeFragment.setArguments(bundle);
                        ((MainActivity)getActivity()).addFragment(secondCodeFragment);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        et_first.addTextChangedListener(textWatcher);
        et_second.addTextChangedListener(textWatcher);
        et_third.addTextChangedListener(textWatcher);
        et_fourth.addTextChangedListener(textWatcher);
        et_fifth.addTextChangedListener(textWatcher);
        et_sixth.addTextChangedListener(textWatcher);
    }
}

