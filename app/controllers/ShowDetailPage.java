package controllers;

import models.*;
import play.api.libs.iteratee.Enumeratee;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.debugView;
import views.html.showDetail;
import views.html.showDetailTree;

import java.util.Map;

/**
 * Created by qwertylevel3 on 16-1-27.
 */
public class ShowDetailPage extends Controller{

    static ShowDetailModel showDetailModel=new ShowDetailModel();
    static Form<TreeForm> treeForm=Form.form(TreeForm.class);
    static public Integer cur=0;
    public static Result show(){
        showDetailModel=new ShowDetailModel();

        ShowDetailItem item1=new ShowDetailItem();
        item1.name="111";
        item1.path="/111/";

        ShowDetailItem item2=new ShowDetailItem();
        item2.name="2";
        item2.path="222/";

        ShowDetailItem item3=new ShowDetailItem();
        item3.name="333";
        item3.path="/333/";

        ShowDetailItem item4=new ShowDetailItem();
        item4.name="4";
        item4.path="444/";

        ShowDetailTable table1=new ShowDetailTable();
        table1.tableName="table1";
        table1.items.add(item1);
        table1.items.add(item2);

        ShowDetailTable table2=new ShowDetailTable();
        table2.tableName="table2";
        table2.items.add(item3);
        table2.items.add(item4);
        table2.items.add(item1);

        showDetailModel.tables.add(table1);
        showDetailModel.tables.add(table2);

        return ok(showDetail.render(showDetailModel));

    }
    public static Result post(){
        return ok(debugView.render("ok"));
    }
    public static Result addTable(){
        Map<String,String[]> m=request().queryString();
        if(m.containsKey("add")){
            String[] n=m.get("tableName");
            ShowDetailTable table=new ShowDetailTable();
            if(n.length!=0 && n[0].length()!=0){
                table.tableName=n[0];

                showDetailModel.tables.add(table);
            }else{
                return ok(debugView.render("error"));
            }
        }else if(m.containsKey("delete")){
            String[] n=m.get("tableName");
            if(n.length==0 || n[0].length()==0){
                return ok(debugView.render("error"));
            }
            for(int i=0;i<showDetailModel.tables.size();i++){
                if(showDetailModel.tables.get(i).tableName.equals(n[0])){
                    showDetailModel.tables.remove(i);
                }
            }
        }
        return ok(showDetail.render(showDetailModel));
    }

    public static boolean isNumeric(String str){
        for (int i = 0; i < str.length(); i++){
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static Result chooseItem(){
        Map<String,String[]> m=request().queryString();

        for(String k:m.keySet()){
            if(isNumeric(k)){
                int tableIndex=Integer.parseInt(k);
                cur=tableIndex;
            }
        }
        return ok(showDetailTree.render(XsdAnalyser.getInstance(),treeForm));
    }
    public static Result addItem(){
        ShowDetailTable temptable=showDetailModel.tables.get(cur);

        for(String index:request().queryString().keySet()){
            if(!index.equals("send")){
                XmlTreeNode t=XsdAnalyser.allNode.get(Integer.parseInt(index));
                ShowDetailItem tempItem=new ShowDetailItem();

                tempItem.name=t.getId();
                tempItem.path=t.getPath();

                temptable.items.add(tempItem);
            }
        }
        return ok(showDetail.render(showDetailModel));
    }
}
