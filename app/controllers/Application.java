package controllers;

import models.IndexItem;
import models.IndexModel;
import models.XmlTreeNode;
import play.mvc.Controller;
import play.mvc.Result;
import java.io.File;

import views.html.index;
import views.html.home;
import views.html.debugView;


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

    private static void debugTree(XmlTreeNode t){
        for(int i=0;i<t.getChild().size();i++){
            System.out.println(t.getChild().get(i).getTreeNodeIndex());

            debugTree(t.getChild().get(i));
        }
    }

    public static void init(){
        XsdAnalyser.getInstance().analyse(Config.getInstance().getXsdFilePath());

        XmlTreeNode root=XsdAnalyser.getInstance().getTree();

        //debugTree(root);
    }

    public static Result createXml(){
        new File(Config.getInstance().getOutputDirPath()).mkdirs();

        IndexPage.createXml(Config.getInstance().getIndexFilePath());
        ResultPage.createXml(Config.getInstance().getResultFilePath());
        ShowDetailPage.createXml(Config.getInstance().getShowDetailFilePath());

        return ok(debugView.render("ok"));
    }
}

