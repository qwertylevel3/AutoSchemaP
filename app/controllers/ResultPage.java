package controllers;

import models.IndexModel;
import models.ResultItem;
import models.ResultModel;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.debugView;
import views.html.result;

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
//        Map<String ,String[]> m=request().queryString();
//        String[] textList=m.get("text");
//        for(int i=0;i<textList.length;i++){
//            indexModel.items.get(i).showName=textList[i];
//        }
//
//        String[] selectList=m.get("select");
//        for(int i=0;i<selectList.length;i++){
//            String temp=selectList[i];
//            if(temp.equals("0:org")){
//                indexModel.items.get(i).dataType=0;
//            }else if(temp.equals("1:String")){
//                indexModel.items.get(i).dataType=1;
//            }else if(temp.equals("2:Integer")){
//                indexModel.items.get(i).dataType=2;
//            }else if(temp.equals("3:double")){
//                indexModel.items.get(i).dataType=3;
//            }else if(temp.equals("4:date")){
//                indexModel.items.get(i).dataType=4;
//            }
//        }
//        for(String k:m.keySet()){
//            if(isNumeric(k)){
//                int nodeIndex=Integer.parseInt(k);
//                for(int i=0;i<indexModel.items.size();i++)
//                {
//                    if(nodeIndex==indexModel.items.get(i).treeNodeIndex){
//                        indexModel.items.get(i).participle=true;
//                        break;
//                    }
//                }
//            }
//        }
//

        return ok(debugView.render("ok"));
    }

}
