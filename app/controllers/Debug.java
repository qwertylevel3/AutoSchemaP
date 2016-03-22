package controllers;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.debugView;
/**
 * Created by qwerty on 16-1-17.
 */
public class Debug extends Controller{
    public static Result debug(String message){
        return ok(debugView.render(message));
    }
}