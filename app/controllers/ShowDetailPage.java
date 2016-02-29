package controllers;

import models.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import play.api.libs.iteratee.Enumeratee;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.debugView;
import views.html.showDetail;
import views.html.showDetailTree;

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
public class ShowDetailPage extends Controller{

    static ShowDetailModel showDetailModel=new ShowDetailModel();
    static Form<TreeForm> treeForm=Form.form(TreeForm.class);
    static public Integer cur=0;
    public static Result show(){
        showDetailModel=new ShowDetailModel();

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
        temptable.items.clear();

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
    public static void createXml(String fileName) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder builder = dbf.newDocumentBuilder();

            Document doc = builder.newDocument();

            Element show=doc.createElement("show");

            doc.appendChild(show);

            for(int i=0;i<showDetailModel.tables.size();i++){
                Element table=doc.createElement("table");
                show.appendChild(table);

                Element id=doc.createElement("id");
                id.setTextContent(i+"");
                table.appendChild(id);

                Element tablename=doc.createElement("tablename");
                tablename.setTextContent(showDetailModel.tables.get(i).tableName);
                table.appendChild(tablename);
            }

            for (int i = 0; i < showDetailModel.tables.size(); i++) {
                for(int j=0;j<showDetailModel.tables.get(i).items.size();j++){
                    Element detail=doc.createElement("detail");
                    show.appendChild(detail);

                    Element showid=doc.createElement("showid");
                    showid.setTextContent(j+"");
                    detail.appendChild(showid);

                    Element tableid=doc.createElement("tableid");
                    tableid.setTextContent(i+"");
                    detail.appendChild(tableid);

                    Element name=doc.createElement("name");
                    name.setTextContent(showDetailModel.tables.get(i).items.get(j).name);
                    detail.appendChild(name);

                    Element path=doc.createElement("path");
                    path.setTextContent(showDetailModel.tables.get(i).items.get(j).path);
                    detail.appendChild(path);
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
