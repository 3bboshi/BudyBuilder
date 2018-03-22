package com.example.amahl.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//http://3bboshi.epizy.com/budy_inseart.php
//"https://o-j-a2003.000webhostapp.com/budy_inseart.php"
public class send_data extends StringRequest {
/*
private  static  final  String SEND_DATA_URL= "https://o-j-a2003.000webhostapp.com/budy_inseart.php" ;
*/
//192.168.8.102:81
private static final  String SEND_DATA_URL="http://192.168.8.102:81/login/budy_inseart.php";
    private Map<String,String> MapData ;

    public send_data(String Ename, String Emob , String EEremark ,String KD ,String Tdate1 ,String ToDate1 , String Einvoice ,String EimageId ,String Eimg,Response.Listener<String>listener){
        super(Method.POST,SEND_DATA_URL,listener ,null);
        MapData=new HashMap<>();
        MapData.put("Ename",Ename);
        MapData.put("Emob",Emob);
        MapData.put("EEremark",EEremark);
        MapData.put("EKD",KD);
        MapData.put("Tdate1",Tdate1);
        MapData.put("ToDate1",ToDate1);
        MapData.put("Einvoice",Einvoice);
        MapData.put("EimageId",EimageId);

        MapData.put("Eimg",Eimg);



    }

    @Override
    public Map<String, String> getParams(){
        return MapData;
    }
}
