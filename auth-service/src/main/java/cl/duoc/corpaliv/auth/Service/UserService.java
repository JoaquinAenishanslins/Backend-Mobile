package cl.duoc.corpaliv.auth.Service;

import cl.duoc.corpaliv.auth.Model.UserModel;
import cl.duoc.corpaliv.auth.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final HashService hashService;

    public UserService(UserRepository userRepository, JwtService jwtService, HashService hashService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.hashService = hashService;
    }

    public String login (String email, String password) {
        UserModel userModel = userRepository.findByEmail(email);
        if (userModel == null ) return null;
        
        String hashedInput = hashService.sha1(password);
        if (!hashedInput.equals(userModel.getPassword())) return null;

        return jwtService.generateToken(email);
    }

    public String register(String email, String password) {
        UserModel existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            return "Usuario ya existe!";
        }

        UserModel newUser = new UserModel();
        newUser.setEmail(email);
        newUser.setPassword(hashService.sha1(password));
        newUser.setRole("USER");

        userRepository.save(newUser);
        return "Usuario registrado exitosamente!";

    }


}
