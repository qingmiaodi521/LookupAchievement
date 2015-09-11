package com.blackwhite.update;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.HashMap;

/**
 * Created by BlackWhite on 15/9/11.
 *
 */
public class ParseXmlWithPull {
    public static HashMap<String,String> parse(String xmlData)
    {
        HashMap<String,String> hashmap = new HashMap<>();
        try{
            XmlPullParserFactory factory =XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String version  = "";
            String versionName = "";
            String url = "";
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        Log.e("nodeName",nodeName);
                        if ("version".equals(nodeName)){
                            version = xmlPullParser.nextText();
                            hashmap.put("version",version);
                        }else if ("name".equals(nodeName)){
                            versionName = xmlPullParser.nextText();
                            hashmap.put("name",nodeName);
                        }else if ("url".equals(nodeName))
                        {
                            url = xmlPullParser.nextText();
                            hashmap.put("url",url);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("update".equals(nodeName))
                        {
                            Log.e("version:",version);
                            Log.e("versionName:",versionName);
                            Log.e("url",url);
                        }
                    break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }

        }catch (Exception e)
        {
         Log.e("ErrorOfParse",e.toString());
        }
        return hashmap;
    }
}
