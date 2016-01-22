package controllers;
import play.*;
import play.mvc.*;
import views.html.*;
/**
 * Created by qwerty on 16-1-17.
 */
public class Debug extends Controller{
    public static Result debug(String message){
        return ok(debugView.render(message));
    }
}