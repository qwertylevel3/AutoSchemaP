package models;

/**
 * Created by qwertylevel3 on 16-1-23.
 */
public class XmlElement extends XmlTreeNode {
    protected String id;
    protected String type;

    public String getId(){return id;}
    public String getType(){ return type;}

    public void setId(String i){id=i;}
    public void setType(String t){type=t;}

    public void copy(XmlElement a, XmlElement b){
        super.copy(a,b);
        b.setId(a.getId());
        b.setType(a.getType());
    }

    public XmlElement clone(){
        XmlElement e=new XmlElement();

        copy(this,e);

        return e;
    }
}
