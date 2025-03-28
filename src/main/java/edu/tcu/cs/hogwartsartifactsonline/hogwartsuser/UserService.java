package edu.tcu.cs.hogwartsartifactsonline.hogwartsuser;

public class UserService  {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
