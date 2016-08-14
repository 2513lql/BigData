package test;

/**
 * Created by stone on 2016/5/3.
 */

import org.wltea.analyzer.Lexeme;
import org.wltea.analyzer.IKSegmentation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

//import org.wltea.analyzer.core.IKSegmenter;
//import org.wltea.analyzer.core.Lexeme;



public class ikAnalyzier {
    public static void main(String args[])
    {
        String str = "中文分词工具包";

        IKAnalysis(str);
    }
    public static List<String> IKAnalysis(String str) {
        List<String> keywordList = new ArrayList<String>();
        try {
            byte[] bt = str.getBytes();
            InputStream ip = new ByteArrayInputStream(bt);
            Reader read = new InputStreamReader(ip);
            IKSegmentation iks = new IKSegmentation(read,true);//true开启智能分词模式，如果不设置默认为false，也就是细粒度分割
            Lexeme t;
            while ((t = iks.next()) != null) {
                keywordList.add(t.getLexemeText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(keywordList);
        return keywordList;
    }

}
