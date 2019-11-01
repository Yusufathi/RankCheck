package com.example.rankcheck;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText apiBox;

    String key;

    boolean hide=false;

    Spinner serverList;

    URLbuilderForID urlgetterID;

    URLbuilderForRank urlgetterRank;

    EditText nameBox;

    String ign,server;

    String url1,url2;

    HttpIDFetcher idFetcher;

    HttpRankFetcher rankFetcher;

    RankManger manger;

    ImageView soloqImg,flexqImg;

    TextView soloqText,flexqText,soloLPText,flexLPText;

    ImageView statusBar;



    int index=0;

    public String[] qType=new String[3];
    public String[] tiers=new String[3];
    public String[] divs=new String[3];
    public int[] LPs=new int[3];


    public void HiddenBtnClick(View view){
        if(hide){
            apiBox.setEnabled(false);
            apiBox.setVisibility(View.GONE);
            hide=false;

        }else{
            apiBox.setVisibility(View.VISIBLE);
            apiBox.setEnabled(true);
            hide=true;
        }
    }

    public void checkBtnClick(View view) throws Exception {

        statusBar.setImageResource(R.color.colorAccent);

        index=0;
        flexqImg.setImageResource(R.drawable.unranked);
        soloqImg.setImageResource(R.drawable.unranked);
        flexqText.setText("Unranked");
        soloqText.setText("Unranked");
        flexLPText.setText("0 LP");
        soloLPText.setText("0 LP");

        ign=nameBox.getText().toString();
        server=serverList.getSelectedItem().toString();
        String id="";

        urlgetterID=new URLbuilderForID(ign,server,key);
        url1=urlgetterID.GetURL();

        String content1=idFetcher.execute(url1).get();
        idFetcher=new HttpIDFetcher();

        id=rtrnID(content1);
        urlgetterRank=new URLbuilderForRank(server,id,key);
        url2=urlgetterRank.GetURL();

        String content2=rankFetcher.execute(url2).get();

        rankFetcher=new HttpRankFetcher();

        manger=new RankManger(content2);

        intializeArrays();

        setRanks();

        statusBar.setImageResource(R.color.colorPrimaryDark);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiallizers();
        intializeServers();
    }

    public  void intiallizers(){

        apiBox=(EditText) findViewById(R.id.ApiKeyBoxID);
        apiBox.setVisibility(View.GONE);

        serverList=findViewById(R.id.serverSpinnerID);

        nameBox=findViewById(R.id.nameBoxID);

        soloqImg=findViewById(R.id.soloqImgBoxID);

        flexqImg=findViewById(R.id.flexqImgBoxID);

        soloqText=findViewById(R.id.soloqBoxID);

        flexqText=findViewById(R.id.flexqBoxID);

        soloLPText=findViewById(R.id.soloLPBoxID);
        flexLPText=findViewById(R.id.flexLPBoxID);

        statusBar=findViewById(R.id.StatusBarRightID);

        key="RGAPI-9cd3bf21-b848-448e-847c-77a523edf1a4";

        idFetcher=new HttpIDFetcher();

        rankFetcher=new HttpRankFetcher();


    }

    public void setRanks(){
        for(int i=0;i<index;i++){
            if(qType[i].equals("RANKED_FLEX_SR")){
                if(tiers[i].equals("IRON")){
                    flexqImg.setImageResource(R.drawable.iron);
                }else if(tiers[i].equals("BRONZE")){
                    flexqImg.setImageResource(R.drawable.bronze);
                }else if(tiers[i].equals("SILVER")){
                    flexqImg.setImageResource(R.drawable.silver);
                }else if(tiers[i].equals("GOLD")){
                    flexqImg.setImageResource(R.drawable.gold);
                }else if(tiers[i].equals("PLATINUM")){
                    flexqImg.setImageResource(R.drawable.platinum);
                }else if(tiers[i].equals("DIAMOND")){
                    flexqImg.setImageResource(R.drawable.diamond);
                }else if(tiers[i].equals("MASTER")){
                    flexqImg.setImageResource(R.drawable.master);
                }else if(tiers[i].equals("GRANDMASTER")){
                    flexqImg.setImageResource(R.drawable.grandmaster);
                }else if(tiers[i].equals("CHALLENGER")){
                    flexqImg.setImageResource(R.drawable.challenger);
                }
                flexqText.setText(tiers[i]+ " "+divs[i]);
                flexLPText.setText(LPs[i]+" LP");
            }
            else if(qType[i].equals("RANKED_SOLO_5x5")){
                if(tiers[i].equals("IRON")){
                    soloqImg.setImageResource(R.drawable.iron);
                }else if(tiers[i].equals("BRONZE")){
                    soloqImg.setImageResource(R.drawable.bronze);
                }else if(tiers[i].equals("SILVER")){
                    soloqImg.setImageResource(R.drawable.silver);
                }else if(tiers[i].equals("GOLD")){
                    soloqImg.setImageResource(R.drawable.gold);
                }else if(tiers[i].equals("PLATINUM")){
                    soloqImg.setImageResource(R.drawable.platinum);
                }else if(tiers[i].equals("DIAMOND")){
                    soloqImg.setImageResource(R.drawable.diamond);
                }else if(tiers[i].equals("MASTER")){
                    soloqImg.setImageResource(R.drawable.master);
                }else if(tiers[i].equals("GRANDMASTER")){
                    soloqImg.setImageResource(R.drawable.grandmaster);
                }else if(tiers[i].equals("CHALLENGER")){
                    soloqImg.setImageResource(R.drawable.challenger);
                }
                soloqText.setText(tiers[i]+ " "+divs[i]);
                soloLPText.setText(LPs[i]+" LP");
            }
        }
    }

    public void intializeServers(){

        String[] items = new String[]{"RU", "KR", "BR1", "OC1", "JP1", "NA1", "EUN1", "EUW1", "TR1", "LA1"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        serverList.setAdapter(adapter);
    }

    public String rtrnID(String content) throws JSONException {
        String ID="";
        content=content.replaceAll("null","");

        Pattern p=Pattern.compile("\"id\":\"(.*?)\",\"accountId");
        Matcher m=p.matcher(content);

        while(m.find()){
            ID=m.group(1);
        }
        return ID;
    }

    public void intializeArrays(){

        for(int i=0;i<3;i++){
            if(manger.qType[i]==null){
                break;
            }
            qType[i]=manger.qType[i];
            divs[i]=manger.divs[i];
            tiers[i]=manger.tiers[i];
            LPs[i]=manger.LPs[i];
            index++;
        }
    }
}
