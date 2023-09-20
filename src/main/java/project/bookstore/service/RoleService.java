package project.bookstore.service;

import project.bookstore.model.Role;

public interface RoleService {
    Role save(Role role);

    Role findByRoleName(Role.RoleName roleName);

    void delete(Long id);
}
