package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by qwertylevel3 on 16-1-22.
 */
public class XsdAnalyser {
    private static XsdAnalyser p=null;

    private XsdAnalyser(){
        String content=readFileByChars("/home/qwertylevel3/2013-07-31_19_09_32.xsd");
        System.out.print(content);
    }

    public static XsdAnalyser getInstance(){
        if(p == null){
            p=new XsdAnalyser();
        }
        return p;
    }

    public String readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;

        String fileContent="";

        try {
            //System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    fileContent+=((char)tempchar);

                    //System.out.print((char) tempchar);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}
