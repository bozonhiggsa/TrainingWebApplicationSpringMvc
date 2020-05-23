package training.web.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import training.web.application.dao.CommonDao;
import training.web.application.dao.CommonDaoJdbc;
import training.web.application.dao.DBException;
import training.web.application.model.Admin;
import training.web.application.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Set;

/**
 * Controller for requests handling
 * @author Ihor Savchenko
 * @version 1.0
 */
@SessionAttributes("session_attribute")
@Controller
public class MvcController {

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "/index";
    }

    @PostMapping("/login")
    public String verifyLogin(@RequestParam(value = "login") String login,
                                          @RequestParam(value = "password") String password) {
        CommonDao commonDao = new CommonDaoJdbc();
        User user = null;
        Admin admin = null;
        HttpSession httpSession = request.getSession();

        try {
            user = commonDao.selectUser(login, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Select user by login and password from data base is failed", e);
        }

        if(user != null){
            httpSession.setAttribute("user", user);
            return "redirect:/enter";
        }
        else {
            try {
                admin = commonDao.selectAdmin(login, password);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DBException("Select user by login and password from data base is failed", e);
            }

            if (admin != null) {
                httpSession.setAttribute("admin", admin);
                return "redirect:/enterAdmin";
            } else {
                request.setAttribute("incorrectLogin", true);
                return "login";
            }
        }
    }

    @GetMapping("/enter")
    public String enterUser() {
        HttpSession httpSession = request.getSession(false);
        if(httpSession != null && httpSession.getAttribute("user") != null){
            return "PersonalCabinet";
        } else{
            return "accessError";
        }
    }

    @GetMapping("/enterAdmin")
    public String enterAdmin() {
        HttpSession httpSession = request.getSession(false);
        CommonDao commonDao = new CommonDaoJdbc();

        if(httpSession != null && httpSession.getAttribute("admin") != null){
            try {
                Set<User> users= commonDao.selectAllUsers();
                if(users != null) httpSession.setAttribute("allUsers", users);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DBException("Select user by login and password from data base is failed", e);
            }
            return "AdminCabinet";
        } else{
            return "accessError";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        HttpSession httpSession = request.getSession(false);
        if(httpSession != null && httpSession.getAttribute("user") != null){
            httpSession.setAttribute("user", null);
            httpSession.invalidate();
        } else if(httpSession != null && httpSession.getAttribute("admin") != null){
            httpSession.setAttribute("admin", null);
            httpSession.invalidate();
        }
        return "index";
    }

    @PostMapping("/createUser")
    public String createUser(@RequestParam(value = "login") String login,
                              @RequestParam(value = "password") String password,
                              @RequestParam(value = "name") String name,
                              @RequestParam(value = "lastname") String lastname,
                              @RequestParam(value = "email") String email) {

        CommonDao commonDao = new CommonDaoJdbc();

        try {
            commonDao.addUser(login, password, name, lastname, email);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Creating of user in data base is failed", e);
        }

        return "redirect:/afterCreate";
    }

    @GetMapping("/afterCreate")
    public String afterCreateUser() {
        return "index";
    }

    @GetMapping("/blockUser")
    public String blockUser(@RequestParam(value = "access") boolean access,
                            @RequestParam(value = "id_user") int id_user) {

        HttpSession httpSession = request.getSession(false);
        CommonDao commonDao = new CommonDaoJdbc();

        if(httpSession != null && httpSession.getAttribute("admin") != null){
            try {
                commonDao.updateUserAccess(id_user, access);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DBException("Update user's access by id is failed", e);
            }

            return "forward:/enterAdmin";
        } else{
            return "accessError";
        }
    }
}
