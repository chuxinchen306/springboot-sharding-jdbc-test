package hello.admin;

import hello.dao1.UserRepository;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chenchx on 2019/1/3.
 */
@Controller
@RequestMapping(path="/demo")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String userName,@RequestParam Long idOwnOrg){
        AdminDo adminDo = new AdminDo();
        adminDo.setUserName(userName);
        adminDo.setIdOwnOrg(idOwnOrg);
        userRepository.save(adminDo);
        return "Saved";
    }

//    @RequiresPermissions(value = "haha")
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<AdminDo> getAllUsers(){
        Subject subject = SecurityUtils.getSubject();
        return userRepository.findAll();
    }

    @GetMapping(path = "/login")
    public @ResponseBody String login(@RequestParam String userName
            ,@RequestParam String password){
        UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    @GetMapping(path = "/loginOut")
    public @ResponseBody String loginOut(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "success";
    }

    /**
     * 测试hintManager运用外部字段来分片
     * @param userName
     * @param idOwnOrg
     * @return
     */
    @GetMapping(path = "/find2")
    public @ResponseBody AdminDo get2Users(@RequestParam String userName,@RequestParam Long idOwnOrg){
//        Subject subject = SecurityUtils.getSubject();
        Long companyId = getCompanyId(idOwnOrg);
        HintManager.clear();
        HintManager hintManager = HintManager.getInstance();
        hintManager.addTableShardingValue("sys_admin",companyId%2);
        return userRepository.findByUserNameAndIdOwnOrg(userName,idOwnOrg);
    }

    private Long getCompanyId(Long idOwnOrg) {
        return idOwnOrg * 3;
    }

    @GetMapping(path = "/find1")
    public @ResponseBody AdminDo getUsers(@RequestParam String userName){
//        Subject subject = SecurityUtils.getSubject();
        return userRepository.findByUserName(userName);
    }
}
