package controllers;

import models.IndexModel;
import models.ResultItem;
import models.ResultModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.debugView;
import views.html.result;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
            tempItem.indexType=m.items.get(i).dataType;

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
    public static void createXml(String fileName) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder builder = dbf.newDocumentBuilder();

            Document doc = builder.newDocument();

            Element config=doc.createElement("config");

            doc.appendChild(config);

            for (int i = 0; i < resultModel.items.size(); i++) {
                if(resultModel.items.get(i).chosen==true){
                    Element result=doc.createElement("result");
                    config.appendChild(result);

                    Element indexName=doc.createElement("indexName");
                    indexName.setTextContent(resultModel.items.get(i).indexName);
                    result.appendChild(indexName);

                    Element showName=doc.createElement("showName");
                    showName.setTextContent(resultModel.items.get(i).showName);
                    result.appendChild(showName);

                    Element type=doc.createElement("type");
                    type.setTextContent(resultModel.items.get(i).indexType.toString());
                    result.appendChild(type);

                    Element showDetail=doc.createElement("showDetail");
                    showDetail.setTextContent(resultModel.items.get(i).showDetailType.toString());
                    result.appendChild(showDetail);

                }

            }

            // 将修改后的文档保存到文件
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transFormer = transFactory.newTransformer();
            transFormer.setOutputProperty(OutputKeys.METHOD,"xml");
            transFormer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
            transFormer.setOutputProperty(OutputKeys.INDENT,"yes");
            transFormer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transFormer.setOutputProperty(OutputKeys.STANDALONE,"yes");

            DOMSource domSource = new DOMSource(doc);

            File file = new File(fileName);

            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            StreamResult xmlResult = new StreamResult(out);
            transFormer.transform(domSource, xmlResult);
           //System.out.println(file.getAbsolutePath());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
