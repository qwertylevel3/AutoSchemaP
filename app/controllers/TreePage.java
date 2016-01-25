package controllers;

import play.*;
import play.mvc.*;
import views.html.*;


/**
 * Created by qwertylevel3 on 16-1-25.
 */
public class TreePage extends Controller{
    public static Result show(){
        XsdAnalyser.getInstance().analyse("/home/qwertylevel3/2013-07-31_19_09_32.xsd");
        //XsdAnalyser.getInstance().debug();
        return ok(tree.render(XsdAnalyser.getInstance()));
    }
}
