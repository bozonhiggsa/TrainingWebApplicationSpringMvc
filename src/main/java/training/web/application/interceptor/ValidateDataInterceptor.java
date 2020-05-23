package training.web.application.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateDataInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        request.setCharacterEncoding("utf-8");

        boolean incorrectFieldFound = false;

        Pattern loginPattern = Pattern.compile("^[A-Za-z_]([A-Za-z\\d_]{2,19})$");
        Pattern passwordPattern = Pattern.compile("^[\\w]{5,20}$");
        Pattern namePattern = Pattern.compile("^[A-ZА-Я]([a-zа-я]{1,19})$");
        Pattern lastnamePattern = Pattern.compile("^[A-ZА-Я]([a-zа-я]{1,19})$");
        Pattern emailPattern = Pattern.compile("^[\\w]([\\w-]{1,19})@([a-z]{3,8})\\.([a-z]{2,3})$");

        Matcher loginMatcher = loginPattern.matcher(request.getParameter("login"));
        Matcher passwordMatcher = passwordPattern.matcher(request.getParameter("password"));
        Matcher nameMatcher = namePattern.matcher(request.getParameter("name"));
        Matcher lastnameMatcher = lastnamePattern.matcher(request.getParameter("lastname"));
        Matcher emailMatcher = emailPattern.matcher(request.getParameter("email"));

        if(!loginMatcher.matches()){
            incorrectFieldFound = true;
        }
        else if(!passwordMatcher.matches()){
            incorrectFieldFound = true;
        }
        else if(!nameMatcher.matches()){
            incorrectFieldFound = true;
        }
        else if(!lastnameMatcher.matches() && !request.getParameter("lastname").equals("")){
            incorrectFieldFound = true;
        }
        else if(!emailMatcher.matches()){
            incorrectFieldFound = true;
        }

        if(incorrectFieldFound){
            request.setAttribute("incorrectRegistration", true);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/registration.jsp");
            dispatcher.forward(request, response);
            return false;
        }
        else return true;
    }
}
