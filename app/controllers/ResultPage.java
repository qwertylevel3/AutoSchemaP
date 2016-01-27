package controllers;

import models.IndexModel;
import models.ResultItem;
import models.ResultModel;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.debugView;
import views.html.result;

import java.util.Map;

/**
 * Created by qwertylevel3 on 16-1-27.
 */
public class ResultPage extends Controller{
    static ResultModel resultModel=new ResultModel();

    public static Result show(){
        resultModel=new ResultModel();

        IndexModel m=IndexPage.indexModel;


        for(int i=0;i<m.items.size();i++) {
            ResultItem tempItem = new ResultItem();
            tempItem.treeNodeIndex = m.items.get(i).treeNodeIndex;
            tempItem.indexName = m.items.get(i).ename;
            tempItem.showName = m.items.get(i).showName;

            resultModel.items.add(tempItem);

        }
        return ok(result.render(resultModel));
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

    public static Result post(){
        //IndexModel indexModel=indexForm.bindFromRequest().get();

        //String output="";
        //for(int i=0;i<indexModel.items.size();i++)
        //{
        //    output+=indexModel.items.get(i).showName;
        //}
        //for(String index:request().queryString().keySet()){
        //    if(!index.equals("send")){
        //        System.out.println(XsdAnalyser.allNode.get(
        //                Integer.parseInt(index)
        //        ).getName());
        //    }
        //}
        Map<String ,String[]> m=request().queryString();
//        String[] textList=m.get("text");
//        for(int i=0;i<textList.length;i++){
//            indexModel.items.get(i).showName=textList[i];
//        }
//
        String[] selectList=m.get("select");
        for(int i=0;i<selectList.length;i++){
            String temp=selectList[i];
            if(temp.equals("0:其他项")){
                resultModel.items.get(i).showDetailType=0;
            }else if(temp.equals("1:标题显示")){
                resultModel.items.get(i).showDetailType=1;
            }else if(temp.equals("2:联系方式")){
                resultModel.items.get(i).showDetailType=2;
            }else if(temp.equals("3:块视图")){
                resultModel.items.get(i).showDetailType=3;
            }else if(temp.equals("4:在线地址")){
                resultModel.items.get(i).showDetailType=4;
            }
        }
        for(String k:m.keySet()){
            if(isNumeric(k)){
                int nodeIndex=Integer.parseInt(k);
                for(int i=0;i<resultModel.items.size();i++)
                {
                    if(nodeIndex==resultModel.items.get(i).treeNodeIndex){
                        resultModel.items.get(i).chosen=true;
                        break;
                    }
                }
            }
        }

        return redirect(routes.ShowDetailPage.show());
    }

}
