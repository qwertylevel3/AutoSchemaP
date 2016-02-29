package controllers;

import models.IndexItem;
import models.IndexModel;
import models.XmlElement;
import models.XmlTreeNode;

import org.w3c.dom.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Map;

/**
 * Created by qwertylevel3 on 16-1-20.
 */
public class IndexPage extends Controller {

    static Form<IndexModel> indexForm = Form.form(IndexModel.class);

    public static IndexModel indexModel = new IndexModel();


    public static Result showAgain(){
        return ok(index.render(indexModel));
    }
    public static Result show() {
        indexModel = new IndexModel();

        for (String index : request().queryString().keySet()) {
            if (!index.equals("send")) {
                XmlTreeNode t = XsdAnalyser.allNode.get(Integer.parseInt(index));
                IndexItem tempItem = new IndexItem();

                tempItem.treeNodeIndex = t.getTreeNodeIndex();
                tempItem.cname = t.getId();
                tempItem.ename = t.getName();
                tempItem.path = t.getPath();

                indexModel.items.add(tempItem);

            }
        }

        return ok(index.render(indexModel));
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static Result post() {
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
        Map<String, String[]> m = request().queryString();
        String[] textList = m.get("text");
        for (int i = 0; i < textList.length; i++) {
            indexModel.items.get(i).showName = textList[i];
        }

        String[] selectList = m.get("select");
        for (int i = 0; i < selectList.length; i++) {
            String temp = selectList[i];
            if (temp.equals("0:org")) {
                indexModel.items.get(i).dataType = 0;
            } else if (temp.equals("1:String")) {
                indexModel.items.get(i).dataType = 1;
            } else if (temp.equals("2:Integer")) {
                indexModel.items.get(i).dataType = 2;
            } else if (temp.equals("3:double")) {
                indexModel.items.get(i).dataType = 3;
            } else if (temp.equals("4:date")) {
                indexModel.items.get(i).dataType = 4;
            }
        }
        for (String k : m.keySet()) {
            if (isNumeric(k)) {
                int nodeIndex = Integer.parseInt(k);
                for (int i = 0; i < indexModel.items.size(); i++) {
                    if (nodeIndex == indexModel.items.get(i).treeNodeIndex) {
                        indexModel.items.get(i).participle = true;
                        break;
                    }
                }
            }
        }


        //return ok(debugView.render("ok"));
        return redirect(routes.ResultPage.show());
    }

    public static void createXml(String fileName) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder builder = dbf.newDocumentBuilder();

            Document doc = builder.newDocument();

            Element config=doc.createElement("config");

            doc.appendChild(config);

            for (int i = 0; i < indexModel.items.size(); i++) {
                Element index=doc.createElement("index");
                config.appendChild(index);

                Element indexName=doc.createElement("indexName");
                indexName.setTextContent(indexModel.items.get(i).ename);
                index.appendChild(indexName);

                Element type=doc.createElement("type");
                type.setTextContent(indexModel.items.get(i).dataType.toString());
                index.appendChild(type);

                Element showName=doc.createElement("showName");
                showName.setTextContent(indexModel.items.get(i).showName);
                index.appendChild(showName);

                Element path=doc.createElement("path");
                path.setTextContent(indexModel.items.get(i).path);
                index.appendChild(path);

                Element analyzed=doc.createElement("analyzed");
                analyzed.setTextContent(indexModel.items.get(i).participle==true?"1":"0");
                index.appendChild(analyzed);
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
