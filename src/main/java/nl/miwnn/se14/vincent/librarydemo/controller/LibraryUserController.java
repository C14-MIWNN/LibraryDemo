package nl.miwnn.se14.vincent.librarydemo.controller;

import jakarta.validation.Valid;
import nl.miwnn.se14.vincent.librarydemo.dto.LibraryUserDTO;
import nl.miwnn.se14.vincent.librarydemo.model.LibraryUser;
import nl.miwnn.se14.vincent.librarydemo.service.LibraryUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Vincent Velthuizen
 * Handle all requests related primarily to LibraryUsers
 */

@Controller
@RequestMapping("/user")
public class LibraryUserController {
    private final LibraryUserService libraryUserService;

    public LibraryUserController(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    @GetMapping("/overview")
    private String showUserOverview(Model datamodel) {
        datamodel.addAttribute("allUsers", libraryUserService.getAllUsers());
        datamodel.addAttribute("formUser", new LibraryUserDTO());

        return "userOverview";
    }

    @PostMapping("/save")
    private String saveOrUpdateUser(@ModelAttribute("formUser") @Valid LibraryUserDTO userDtoToBeSaved, BindingResult result,
                                    Model datamodel) {
        if (libraryUserService.usernameInUse(userDtoToBeSaved.getUsername())) {
            result.rejectValue("username", "duplicate", "This username is not available");
        }

        if (!userDtoToBeSaved.getPassword().equals(userDtoToBeSaved.getPasswordConfirm())) {
            result.rejectValue("password", "no.match", "The passwords do not match");
        }

        if (result.hasErrors()) {
            datamodel.addAttribute("allUsers", libraryUserService.getAllUsers());
            return "userOverview";
        }

        libraryUserService.save(userDtoToBeSaved);
        return "redirect:/user/overview";
    }
}
