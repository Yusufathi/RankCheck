package com.example.rankcheck;

public class URLbuilderForRank {

    String url,server,ID;
    String key;

    public URLbuilderForRank(String server,String ID,String key){
        this.server=server;
        this.ID=ID;
        this.key=key;
    }
    public String GetURL(){
        return urlBuilding();
    }

    public String urlBuilding() {

        String start="https://";
        String middle=".api.riotgames.com/lol/league/v4/entries/by-summoner/";
        String end="?api_key=";
        url=start+server+middle+ID+end+key;
        return url;
    }
}
