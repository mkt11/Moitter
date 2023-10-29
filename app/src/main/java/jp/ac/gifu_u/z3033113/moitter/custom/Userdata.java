package jp.ac.gifu_u.z3033113.moitter.custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

//keyの種類
//Name ::ユーザーネームを保存しておく
//Email :: メールを保存しておく
//Password ::パスワードを保存しておく
//Signed ::サインインしているかどうかを確認する

public class Userdata {
    //ここを static にすることによってどこでもuserdataを呼び出すことができる。
    private static SharedPreferences userdata;

    //コンストラクタ
    public Userdata(Context context){
        //アプリ内で使用できるように制限する
        userdata = context.getSharedPreferences("Userdata",Context.MODE_PRIVATE);
    }


    public Boolean getBoolean_user(String key){
        return userdata.getBoolean(key,false);
    }

    public void putBoolean_user(String key,Boolean value){
        //ユーザのデータを入力し、userdataに収納する。
        SharedPreferences.Editor editor = userdata.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }



    public String getString_user(String key)
    {
        return userdata.getString(key,null);
    }

    public void putString_user(String key,String value){
        //ユーザのデータを入力し、userdataに収納する。
        SharedPreferences.Editor editor = userdata.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public void clear(){
        SharedPreferences.Editor editor = userdata.edit();
        //すべてのデータを削除
        editor.clear();
        editor.apply();
    }

}
