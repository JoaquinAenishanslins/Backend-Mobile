package cl.duoc.corpaliv.auth.Controller;

import cl.duoc.corpaliv.auth.Service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String token = userService.login(email, password);

        Map<String, String> response = new HashMap<>();
        if (token == null) {
            response.put("status", "error");
            response.put("token", token);
        } else {
            response.put("status", "ok");
            response.put("token", token);
        }
        return response;
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String result = userService.register(email, password);

        Map<String, String> response = new HashMap<>();
        response.put("message", result);
        return response;
    }

}
