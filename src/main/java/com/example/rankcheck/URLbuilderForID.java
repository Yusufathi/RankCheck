package com.example.rankcheck;

public class URLbuilderForID {
    String key,ign,server,url;

    public URLbuilderForID(String ign, String server,String key){
        this.key=key;
        this.ign=ign;
        this.server=server;
    }

    public String GetURL(){
        return urlBuilding();
    }

    public String urlBuilding(){

        String start="https://";
        String middle=".api.riotgames.com/lol/summoner/v4/summoners/by-name/";
        String name=ign.replaceAll("\\s+","");
        String end="?api_key=";

        url =start+server+middle+name+end+key;
        return url;
    }
}
