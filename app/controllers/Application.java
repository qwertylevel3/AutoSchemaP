package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;

public class Application extends Controller {

    public static Result index() {
        IndexItem item1=new IndexItem();
        item1.cname="aaa";
        item1.ename="eee";
        item1.dataType=3;
        item1.participle=false;
        item1.path="/afdsaf";
        item1.showName="gggg";

        IndexModel indexModel= new IndexModel();

        indexModel.items.add(item1);

        return ok(index.render(indexModel));
    }

    public static Result home() {
        return ok(home.render("message"));
    }
}
