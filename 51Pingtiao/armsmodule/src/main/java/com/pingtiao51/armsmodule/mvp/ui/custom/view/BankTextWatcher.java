package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class BankTextWatcher {
    private int[] ints;//多少间隔进行空格判断 数组的最后一个数字 将会成为最后无限间隔的数
    private TextWatcher mTextWatcher;
    private EditText mEditText;

    //    private static BankTextWatcher instance;
    public BankTextWatcher() {
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String numbers = s.toString();
                numbers = numbers.replaceAll(" ", "");
                char[] chars = numbers.toCharArray();
                StringBuffer sb = new StringBuffer();
                if (chars.length > ints[0]) {
                    for (int i = 0, j = 0, k = 0; i < chars.length; i++) {
                        sb.append(chars[i]);
                        if (j == 0) {
                            if ((i + 1) % ints[j] == 0) {
                                j++;
                                if (j > ints.length - 1) {
                                    j--;
                                    k++;
                                }
                                sb.append(" ");
                            }
                        } else {
                            int distance = 0;
                            for (int z = 0; z < j; z++) {
                                distance = distance + ints[z];
                            }
                            distance = distance + k * ints[ints.length - 1];
                            if ((i + 1 - distance) % ints[j] == 0) {
                                j++;
                                if (j > ints.length - 1) {
                                    j--;
                                    k++;
                                }
                                sb.append(" ");
                            }
                        }
                    }
                } else {
                    sb.append(s.toString());
                }
                String res = sb.toString();
                if (res.endsWith(" ")) {
                    res = res.substring(0, res.length() - 1);
                }
                mEditText.removeTextChangedListener(mTextWatcher);
                mEditText.setText(res);
                mEditText.addTextChangedListener(mTextWatcher);
                mEditText.setSelection(mEditText.getText().toString().length());
            }
        };
    }

  /*  public static BankTextWatcher getInstance() {
        if(instance == null) {
            synchronized (BankTextWatcher.class) {
                    if(instance == null){
                        instance = new BankTextWatcher();
                    }
            }
        }
        return instance;
    }*/

    public void bind(EditText et, int[] ints1) {
        this.mEditText = et;
        this.ints = ints1;
        this.mEditText.addTextChangedListener(mTextWatcher);
    }
}
