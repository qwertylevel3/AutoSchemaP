package controllers;

import models.IndexItem;
import models.IndexModel;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * Created by qwertylevel3 on 16-1-20.
 */
public class IndexPage extends Controller{

    static Form<IndexModel> indexForm =Form.form(IndexModel.class);

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

    public static Result post(){
        IndexModel indexModel=indexForm.bindFromRequest().get();

        String output="";
        for(int i=0;i<indexModel.items.size();i++)
        {
            output+=indexModel.items.get(i).showName;
        }
        return redirect(routes.Debug.debug(output));
    }

}
