package training.web.application.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import training.web.application.dao.CommonDao;
import training.web.application.dao.CommonDaoJdbc;
import training.web.application.dao.DBException;
import training.web.application.model.Admin;
import training.web.application.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Set;

public class MatchUserInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        request.setCharacterEncoding("utf-8");

        CommonDao commonDao = new CommonDaoJdbc();
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        boolean match = false;

        try {
            Set<User> users = commonDao.selectAllUsers();
            for (User user: users) {
                if(user.getLogin().equals(login) || user.getEmail().equals(email)){
                    match = true;
                    break;
                }
            }
            Set<Admin> admins = commonDao.selectAllAdmins();
            for (Admin admin: admins) {
                if(admin.getLogin().equals(login)){
                    match = true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Reading of all users from data base", e);
        }

        if(match){
            request.setAttribute("matchUser", true);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/registration.jsp");
            dispatcher.forward(request, response);
            return false;
        }
        else return true;
    }
}
