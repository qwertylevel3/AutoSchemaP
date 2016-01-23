package models;

/**
 * Created by qwertylevel3 on 16-1-23.
 */
public class Element extends TreeNode{
    protected String id;
    protected String type;

    public String getId(){return id;}
    public String getType(){ return type;}

    public void setId(String i){id=i;}
    public void setType(String t){type=t;}

    public void copy(Element a,Element b){
        super.copy(a,b);
        b.setId(a.getId());
        b.setType(a.getType());
    }

    public Element clone(){
        Element e=new Element();

        copy(this,e);

        return e;
    }
}
