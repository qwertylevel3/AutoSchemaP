package controllers;

import play.*;
import play.mvc.*;
import views.html.*;
/**
 * Created by qwertylevel3 on 16-1-25.
 */
public class TreePage extends Controller{
    public static Result show(){
        return ok(tree.render());
    }
}
