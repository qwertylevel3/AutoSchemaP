package models;

/**
 * Created by qwertylevel3 on 16-1-22.
 */

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    //get....
    public String getName(){
        return name;
    }
    public String getPath(){
        return path;
    }
    public List<String> getAnnotation(){
        return annotation;
    }
    public TreeNode getParent(){
        return parent;
    }
    public List<TreeNode> getChild(){
        return child;
    }


    //set...
    public void setName(String n){
        name=n;
    }
    public void setPath(String p){
        path=p;
    }
    public void setParent(TreeNode p){
        parent=p;
    }
    public void addChild(TreeNode p){
        p.setParent(this);
        child.add(p);
    }
    public void addAnnotation(String a){
        annotation.add(a);
    }


    public String setPath(TreeNode par){
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


    //clone root
    public TreeNode clone(){
        TreeNode root=new TreeNode();

        root.setName(this.getName());
        root.setPath(this.getPath());
        for(int i=0;i<annotation.size();i++)
        {
            root.addAnnotation(this.getAnnotation().get(i));
        }
        for(int i=0;i<child.size();i++)
        {
            TreeNode t=child.get(i).clone();

            root.addChild(t);

        }
        return root;
    }




    protected TreeNode parent=null;
    protected List<TreeNode> child=new ArrayList<>();


    protected String name=new String("");
    protected List<String> annotation=new ArrayList<>();
    protected String path=new String("");
}
