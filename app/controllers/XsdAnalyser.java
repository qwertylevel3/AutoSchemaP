package controllers;

import java.io.*;

import com.sun.xml.internal.bind.v2.TODO;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by qwertylevel3 on 16-1-22.
 */
public class XsdAnalyser {
    private static XsdAnalyser p=null;
    private String xsdFile;

    private XsdAnalyser(){
    }


    public static XsdAnalyser getInstance(){
        if(p == null){
            p=new XsdAnalyser();
        }
        return p;
    }

    private void analyse(Node node){
        String nodeName=node.getNodeName();
        System.out.println(nodeName);

        if(nodeName=="xs:complexType"){
            //System.out.println("conplexType");
        }
        if(nodeName=="xs:simpleType"){
            //System.out.println("simpleType");
        }
        if(nodeName=="xs:element"){
            //System.out.println("element");
        }

    }

    public Boolean analyse(String filename){
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
                    analyse(nodeList.item(i));
                } else{
                    //TODO
                }


            }

        }  catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
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
