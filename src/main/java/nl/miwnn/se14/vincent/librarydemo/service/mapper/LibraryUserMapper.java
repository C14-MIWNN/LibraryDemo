package nl.miwnn.se14.vincent.librarydemo.service.mapper;

import nl.miwnn.se14.vincent.librarydemo.dto.LibraryUserDTO;
import nl.miwnn.se14.vincent.librarydemo.model.LibraryUser;

/**
 * @author Vincent Velthuizen
 * Converts between Model classes and DTOs
 */
public class LibraryUserMapper {

    public static LibraryUser fromDTO(LibraryUserDTO dto) {
        LibraryUser user = new LibraryUser();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }

}
