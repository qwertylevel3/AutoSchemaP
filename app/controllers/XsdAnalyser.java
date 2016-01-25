package controllers;


import models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by qwertylevel3 on 16-1-22.
 */
public class XsdAnalyser {
    private static XsdAnalyser p=null;
    public String xsdFile="";
    private XmlTreeNode root=new XmlTreeNode();

//    public static Map<Integer,XmlTreeNode> nodeMap=new HashMap<>();
    public static List<XmlTreeNode> allNode=new ArrayList<>();

    private XsdAnalyser(){
    }
    public static XsdAnalyser getInstance(){
        if(p == null){
            p=new XsdAnalyser();
        }
        return p;
    }

    private static Integer treeNodeIndex=0;
    public static List<XmlTreeNode> nodeList=new ArrayList<>();

    public XmlTreeNode getTree(){return root;}

    private void handleComplexType(Node node,XmlTreeNode parent){
        XmlComplexType tempNode=new XmlComplexType();
        String tempString=node.getAttributes().getNamedItem("name").toString();
        tempNode.setName(tempString.substring(
                tempString.indexOf('"')+1,
                tempString.lastIndexOf('"')
        ));
        tempNode.setTreeNodeIndex(treeNodeIndex++);
        tempNode.setNodeType("complexType");
        //allNode.add(treeNodeIndex-1,tempNode);
        parent.addChild(tempNode);
        //System.out.println(tempNode.getName());

        for(int i=0;i<node.getChildNodes().getLength();i++)
        {
            analyse(node.getChildNodes().item(i),tempNode);
        }
    }
    private void handleSimpleType(Node node,XmlTreeNode parent){
        XmlSimpleType tempNode=new XmlSimpleType();
        String tempString=node.getAttributes().getNamedItem("name").toString();
        tempNode.setName(tempString.substring(
                tempString.indexOf('"')+1,
                tempString.lastIndexOf('"')
        ));
        tempNode.setTreeNodeIndex(treeNodeIndex++);
        tempNode.setNodeType("simpleType");
        //allNode.add(treeNodeIndex-1,tempNode);
        parent.addChild(tempNode);
        //System.out.println(tempNode.getName());

        for(int i=0;i<node.getChildNodes().getLength();i++)
        {
            analyse(node.getChildNodes().item(i),tempNode);
        }
    }

    private void handleElement(Node node,XmlTreeNode parent){
        XmlElement tempNode=new XmlElement();
        String tempString=node.getAttributes().getNamedItem("name").toString();
        tempNode.setName(tempString.substring(
                tempString.indexOf('"')+1,
                tempString.lastIndexOf('"')
        ));
        tempString=node.getAttributes().getNamedItem("id").toString();
        tempNode.setId(tempString.substring(
                tempString.indexOf('"')+1,
                tempString.lastIndexOf('"')
        ));
        tempString=node.getAttributes().getNamedItem("type").toString();
        tempNode.setId(tempString.substring(
                tempString.indexOf('"')+1,
                tempString.lastIndexOf('"')
        ));
        tempNode.setTreeNodeIndex(treeNodeIndex++);
        tempNode.setNodeType("element");
        //allNode.add(treeNodeIndex-1,tempNode);
        parent.addChild(tempNode);
        for(int i=0;i<node.getChildNodes().getLength();i++)
        {
            analyse(node.getChildNodes().item(i),tempNode);
        }
    }

    private void analyse(Node node, XmlTreeNode parent){
        String nodeName=node.getNodeName();
        //System.out.println(nodeName);

        if(nodeName=="xs:complexType"){
            handleComplexType(node,parent);
        }
        else if(nodeName=="xs:simpleType"){
            handleSimpleType(node, parent);
        }
        else if(nodeName=="xs:element"){
            handleElement(node, parent);
        }else{
            for(int i=0;i<node.getChildNodes().getLength();i++){
                analyse(node.getChildNodes().item(i),parent);
            }
        }
    }

    public void analyse(String filename){

        allNode.clear();

        treeNodeIndex=1;

        xsdFile=readFileByChars(filename);

        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder builder = dbf.newDocumentBuilder();

            File f = new File(filename);
            InputStream in = new FileInputStream(f);
            //byte b[]=new byte[(int)f.length()];     //创建合适文件大小的数组
            //in.read(b);    //读取文件中的内容到b[]数组

            Document doc = builder.parse(in);
            Element r = doc.getDocumentElement();
            System.out.println(r.getTagName());
            NodeList nodeList=r.getChildNodes();

            //第一层解析
            for(int i=0;i<nodeList.getLength();i++)
            {
                //非根
                if(nodeList.item(i).getNodeName()!="xs:element"){
                    analyse(nodeList.item(i),root);
                } else{
                    //单独处理根
                    String tempString=nodeList.item(i).getAttributes().getNamedItem("name").toString();
                    root.setName(tempString.substring(
                            tempString.indexOf('"')+1,
                            tempString.lastIndexOf('"')
                    ));
                    root.setTreeNodeIndex(0);
                    allNode.add(0,root);
                }
            }
            setTreeNodePath(root);
            createNodeList(root);

        }  catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTreeNodePath(XmlTreeNode t){
        t.setPath();
        for(int i=0;i<t.getChild().size();i++)
        {
            setTreeNodePath(t.getChild().get(i));
        }
    }

    private void createNodeList(XmlTreeNode t){
        for(int i=0;i<t.getChild().size();i++){
            //System.out.println(t.getChild().get(i).getTreeNodeIndex());
            allNode.add(t.getChild().get(i));

            createNodeList(t.getChild().get(i));
        }
    }

    public void debug(){
        for(int i=0;i<allNode.size();i++){
            System.out.println(allNode.get(i).getName());
        }
        System.out.println(allNode.size());
        //debugTree(root);
    }
    private void debugTree(XmlTreeNode t){
        for(int i=0;i<t.getChild().size();i++){
            System.out.println(t.getChild().get(i).getTreeNodeIndex());

            debugTree(t.getChild().get(i));
        }
    }
    public String readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;

        String fileContent="";

        try {
            //System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    fileContent+=((char)tempchar);

                    //System.out.print((char) tempchar);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}
