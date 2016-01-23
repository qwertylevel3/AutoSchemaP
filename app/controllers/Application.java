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
        XsdAnalyser.getInstance().analyse("/home/qwertylevel3/2013-07-31_19_09_32.xsd");
    }

}
