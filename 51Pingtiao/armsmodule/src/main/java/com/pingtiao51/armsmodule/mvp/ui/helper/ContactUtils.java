package com.pingtiao51.armsmodule.mvp.ui.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.pingtiao51.armsmodule.mvp.model.entity.RelationSubmitRequestEntity;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Map;

import timber.log.Timber;

/**
 * ================================================
 * <p>
 * Created by NIRVANA on 2018/1/2
 * Contact with <zwq651406441@gmail.com>
 * ================================================
 */
public class ContactUtils {
    private static String[] simUris = {
            "content://icc/adn",
            "content://icc/adn/subId/0",
            "content://icc/adn/subId/1",
            "content://icc/sdn",
            "content://icc/sdn/subId/0",
            "content://icc/sdn/subId/1",
            "content://icc/fdn",
            "content://icc/fdn/subId/0",
            "content://icc/fdn/subId/1",
    };

    /**
     * 根据电话号码查询通讯录中的联系人姓名
     *
     * @param phonNum 联系人的号码
     * @return 联系人的姓名
     */
    public static String findNameFromContact(String phonNum, Map<String, String> allContacts) {
        if (!TextUtils.isEmpty(phonNum) && allContacts != null && allContacts.size() > 0) {
            String phone = handlePhoneNum(phonNum);//将号码处理后再进行查询
            if (allContacts.containsKey(phone)) {
                return allContacts.get(phone);
            }
        }
        return null;
    }

    /**
     * 获取通讯录中的所有联系人存储在Map中
     * 注：Map中号码唯一作为key，号码对应的联系人作为value
     * 对同一个号码存储多个联系人姓名的做了处理，这里只存一个姓名。即一个号码只对应一个姓名
     *
     * @param context
     * @return
     */
    public static ArrayList<RelationSubmitRequestEntity> getAllContacts(Context context) {
        ArrayList<RelationSubmitRequestEntity> contacts = new ArrayList<>();
        //生成ContentResolver对象
        ContentResolver contentResolver = context.getContentResolver();
        // 获得所有的联系人
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        // 循环遍历
        if (cursor.moveToFirst()) {
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int displayNameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            do {
                // 获得联系人的ID
                String contactId = cursor.getString(idColumn);
                // 获得联系人姓名
                String displayName = cursor.getString(displayNameColumn);
                // 查看联系人有多少个号码，如果没有号码，返回0
                int phoneCount = cursor.getInt(cursor
                        .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (phoneCount > 0) {
                    // 获得联系人的电话号码列表
                    Cursor phoneCursor = context.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + "=" + contactId, null, null);
                    if (phoneCursor.moveToFirst()) {
                        do {
                            //遍历所有的联系人下面所有的电话号码
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            if (!TextUtils.isEmpty(handlePhoneNum(phoneNumber))){
                                RelationSubmitRequestEntity entity = new RelationSubmitRequestEntity(displayName,handlePhoneNum(phoneNumber));
                                contacts.add(entity);
                            }
                        } while (phoneCursor.moveToNext());
                    }
                    phoneCursor.close();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contacts;
    }

    public static void getAllSimContacts(Context context, ArrayList<RelationSubmitRequestEntity> list) {
        // 读取SIM卡手机号,有三种可能:content://icc/adn || content://icc/sdn || content://icc/fdn
        // 具体查看类 IccProvider.java

        Cursor cursor = null;
        for (String simUri : simUris) {
            try {
                Uri uri = Uri.parse(simUri);
                Timber.d("SimContacts uri -> " + uri.toString());
                cursor = context.getContentResolver().query(uri, null,
                        null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        // 取得联系人名字
                        int nameIndex = cursor.getColumnIndex("name");
                        // 取得电话号码
                        int numberIndex = cursor.getColumnIndex("number");
                        //遍历所有的联系人下面所有的电话号码
                        RelationSubmitRequestEntity entity = new RelationSubmitRequestEntity(cursor.getString(nameIndex), handlePhoneNum(cursor.getString(numberIndex)));
                        if (!list.contains(entity)) {
                            list.add(entity);
                        }
                    }
                }
            } catch (Exception e) {
                Timber.e(e);
                MobclickAgent.reportError(context, e);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    /**
     * 处理号码的方法
     * 规则：
     * 1、去除号码中所有的非数字
     * 2、如果号码为13位（即手机号）就去掉86
     *
     * @param phoneNum
     * @return
     */
    private static String handlePhoneNum(String phoneNum) {
        if (!TextUtils.isEmpty(phoneNum)) {
            phoneNum = phoneNum.replaceAll("\\D", "");
        }else {
            return "";
        }
        if (phoneNum.length() == 13 && phoneNum.startsWith("86")) {
            return phoneNum.substring(2);
        }
        return phoneNum;
    }
}
