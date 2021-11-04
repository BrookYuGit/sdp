package cn.mysdp.biz.controller;

import cn.mysdp.biz.dto.request.BaseRequest;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Controller:
 * @Author: SDP
 * @date: 2020-06-01
 */

@RestController
@RequestMapping(value = "/auth_v1")
public class LoginController extends BaseController {

    public static String mockUserInfo = "{\"code\":\"admin\",\"email\":\"admin@com.cn\",\"id\":1,\"name\":\"admin\",\"role_code\":\"admin\",\"role_id\":1,\"role_name\":\"admin\",\"role_permissions\":\"admin\",\"role_type\":\"admin\",\"sessionid\":\"12345678\",\"status\":1}";

    @PostMapping("/login")
    public JSONObject login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody JSONObject request) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(mockUserInfo);
        return jsonObject;
    }

    @PostMapping("/logout")
    public JSONObject logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody BaseRequest request) throws Exception {
        return new JSONObject();
    }

    @PostMapping("/send_vcode")
    public JSONObject sendVcode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody BaseRequest request) throws Exception {
        return new JSONObject();
    }

    @PostMapping("/get_user_info")
    public JSONObject getUserInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody BaseRequest request) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(mockUserInfo);
        return jsonObject;
    }
}
