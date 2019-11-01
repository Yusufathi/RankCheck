package com.example.rankcheck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RankManger {
    private String content;

    public String[] qType=new String[3];
    public String[] tiers=new String[3];
    public String[] divs=new String[3];
    public int[] LPs=new int[3];

    public RankManger(String JSONcontent) throws JSONException {
        content=JSONcontent.replaceAll("null","");
        intiallize();
    }

    public void intiallize() throws JSONException {
        JSONArray jsonArray = new JSONArray(content);
        for(int i=0; i<jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            qType[i]= (String) obj.get("queueType");
            tiers[i]= (String) obj.get("tier");
            divs[i]=(String) obj.get("rank");
            LPs[i]= (int) obj.get("leaguePoints");
        }


    }
}
