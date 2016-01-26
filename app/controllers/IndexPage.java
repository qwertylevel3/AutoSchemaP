package controllers;

import models.IndexItem;
import models.IndexModel;
import models.XmlElement;
import models.XmlTreeNode;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * Created by qwertylevel3 on 16-1-20.
 */
public class IndexPage extends Controller{

    static Form<IndexModel> indexForm =Form.form(IndexModel.class);

    public static Result show() {

        IndexModel indexModel= new IndexModel();

        for(String index:request().queryString().keySet()){
            if(!index.equals("send")){
                XmlTreeNode t=XsdAnalyser.allNode.get(Integer.parseInt(index));
                IndexItem tempItem=new IndexItem();
                tempItem.cname=t.getId();
                tempItem.ename=t.getName();
                tempItem.path=t.getPath();

                indexModel.items.add(tempItem);

            }
        }

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
