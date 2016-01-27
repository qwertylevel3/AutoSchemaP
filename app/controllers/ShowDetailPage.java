package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.debugView;

/**
 * Created by qwertylevel3 on 16-1-27.
 */
public class ShowDetailPage extends Controller{
    public static Result show(){
        return ok(debugView.render("ok"));

    }
    public static Result post(){
        return ok(debugView.render("ok"));
    }
}
