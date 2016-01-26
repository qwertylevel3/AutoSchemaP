package models;

/**
 * Created by qwertylevel3 on 16-1-22.
 */

import java.util.ArrayList;
import java.util.List;

public class XmlTreeNode {

    //get....
    public String getNodeType(){return nodeType;}
    public Integer getTreeNodeIndex(){return treeNodeIndex;}
    public String getName(){
        return name;
    }
    public String getPath(){
        return path;
    }
    public List<String> getAnnotation(){
        return annotation;
    }
    public XmlTreeNode getParent(){
        return parent;
    }
    public List<XmlTreeNode> getChild(){
        return child;
    }
    public String getId(){return "";}
    public String getType(){ return "";}

    public void setId(String i){}
    public void setType(String t){}


    //set...
    public void setNodeType(String t){nodeType=t;}
    public void setTreeNodeIndex(Integer i){treeNodeIndex=i;}
    public void setName(String n){
        name=n;
    }
    public void setPath(String p){
        path=p;
    }
    public void setParent(XmlTreeNode p){
        parent=p;
    }
    public void addChild(XmlTreeNode p){
        p.setParent(this);
        child.add(p);
    }
    public void addAnnotation(String a){
        annotation.add(a);
    }


    public String setPath(XmlTreeNode par){
        String temp="";
        if(par!=null){
            temp=setPath(par.getParent());
            return temp+"/"+par.getName();
        }
        return temp;
    }

    public void setPath(){
        if(parent!=null){
            this.path=this.setPath(parent)+"/"+name;
        }
    }

    //把a复制到b
    public void copy(XmlTreeNode a, XmlTreeNode b){

        b.setName(a.getName());
        b.setPath(a.getPath());
        for(int i=0;i<a.getAnnotation().size();i++)
        {
            b.addAnnotation(a.getAnnotation().get(i));
        }
        for(int i=0;i<a.getChild().size();i++)
        {
            XmlTreeNode t=a.getChild().get(i).clone();

            b.addChild(t);
        }
    }

    //clone root
    public XmlTreeNode clone(){
        XmlTreeNode root=new XmlTreeNode();

        copy(this,root);

        return root;
    }


    public Integer treeNodeIndex=0;

    public XmlTreeNode parent=null;
    protected List<XmlTreeNode> child=new ArrayList<>();


    public String name=new String("");
    protected List<String> annotation=new ArrayList<>();
    protected String path=new String("");

    public String nodeType=new String("");
}
