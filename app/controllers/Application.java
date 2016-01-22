package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;

import java.io.*;
import java.io.File.*;


public class Application extends Controller {

    public static Result index() {
        IndexItem item1=new IndexItem();
        item1.cname="aaa";
        item1.ename="eee";
        item1.dataType=3;
        item1.participle=false;
        item1.path="/afdsaf";
        item1.showName="gggg";
        IndexItem item2=new IndexItem();
        item2.cname="aaa";
        item2.ename="eee";
        item2.dataType=3;
        item2.participle=false;
        item2.path="/afdsaf";
        item2.showName="gggg";
        IndexModel indexModel= new IndexModel();

        indexModel.items.add(item1);
        indexModel.items.add(item2);



        return ok(index.render(indexModel));
    }

    public static Result home() {
        init();

        return ok(home.render("message"));
    }


    public static void init(){
        String content=readFileByChars("/home/qwertylevel3/2013-07-31_19_09_32.xsd");
        System.out.print(content);

    }

    public static String readFileByChars(String fileName) {
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
