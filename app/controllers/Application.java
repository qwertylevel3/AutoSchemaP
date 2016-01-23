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
        TreeNode root=new TreeNode();


        System.out.println("root over");
        root.setName("Root");

        TreeNode t1=new TreeNode();
        t1.setName("t1");
        TreeNode t2=new TreeNode();
        t2.setName("t2");
        TreeNode t3=new TreeNode();
        t3.setName("t3");
        TreeNode t4=new TreeNode();
        t4.setName("t4");


        root.addChild(t1);
        root.addChild(t2);
        t1.addChild(t3);
        t1.addChild(t4);


        System.out.println(root.getName());
        System.out.println(t1.getParent().getName());
        System.out.println(t2.getParent().getName());
        System.out.println(t3.getParent().getName());
        System.out.println(t4.getParent().getName());



        root.setPath();
        t1.setPath();
        t2.setPath();
        t3.setPath();
        t4.setPath();



        System.out.println(root.getName());
        System.out.println(t1.getParent().getName());
        System.out.println(t2.getParent().getName());
        System.out.println(t3.getParent().getName());
        System.out.println(t4.getParent().getName());


        System.out.println(root.getPath());
        System.out.println(t1.getPath());
        System.out.println(t2.getPath());
        System.out.println(t3.getPath());
        System.out.println(t4.getPath());

        //XsdAnalyser.getInstance();
    }

}
