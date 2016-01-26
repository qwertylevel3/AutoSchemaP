package controllers;

import models.TreeForm;
import play.*;
import play.api.data.Form$;
import play.data.Form;
import play.mvc.*;
import views.html.*;

import java.text.Normalizer;
import java.util.Map;


/**
 * Created by qwertylevel3 on 16-1-25.
 */
public class TreePage extends Controller{
    static Form<TreeForm> treeForm=Form.form(TreeForm.class);

    public static Result show(){
        XsdAnalyser.getInstance().analyse("/home/qwertylevel3/2013-07-31_19_09_32.xsd");
        //XsdAnalyser.getInstance().debug();
        return ok(tree.render(XsdAnalyser.getInstance(),treeForm));
    }
    public static Result post(){
        //TreeForm t=treeForm.bindFromRequest().get();
        //String list=request().getQueryString("treeNodeList");
        //System.out.println(request());

//        for(int i=0;i<XsdAnalyser.allNode.size();i++){
//            Map<String,String[]> m=request().queryString();
//            System.out.println(request().getQueryString("i"));
//        }
        for(String index:request().queryString().keySet()){
            if(!index.equals("send")){
                System.out.println(XsdAnalyser.allNode.get(
                        Integer.parseInt(index)
                ).getName());
            }
        }

        return ok(debugView.render("ok"));
    }
}

