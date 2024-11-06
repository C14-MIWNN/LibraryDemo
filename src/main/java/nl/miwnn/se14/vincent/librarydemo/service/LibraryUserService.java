package nl.miwnn.se14.vincent.librarydemo.service;

import nl.miwnn.se14.vincent.librarydemo.dto.LibraryUserDTO;
import nl.miwnn.se14.vincent.librarydemo.model.LibraryUser;
import nl.miwnn.se14.vincent.librarydemo.repositories.LibraryUserRepository;
import nl.miwnn.se14.vincent.librarydemo.service.mapper.LibraryUserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Vincent Velthuizen
 * Purpose for the class
 */

@Service
public class LibraryUserService implements UserDetailsService {
    private final LibraryUserRepository libraryUserRepository;
    private final PasswordEncoder passwordEncoder;

    public LibraryUserService(LibraryUserRepository libraryUserRepository, PasswordEncoder passwordEncoder) {
        this.libraryUserRepository = libraryUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return libraryUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s was not found.", username)));
    }

    public boolean usernameInUse(String username) {
        return libraryUserRepository.findByUsername(username).isPresent();
    }

    public void save(LibraryUserDTO userDTO) {
        save(LibraryUserMapper.fromDTO(userDTO));
    }

    public void save(LibraryUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        libraryUserRepository.save(user);
    }

    public List<LibraryUser> getAllUsers() {
        return libraryUserRepository.findAll();
    }
}
