package project.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.bookstore.exception.EntityNotFoundException;
import project.bookstore.model.Role;
import project.bookstore.repository.RoleRepository;
import project.bookstore.service.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByRoleName(Role.RoleName roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(() ->
                new EntityNotFoundException(String
                        .format("Role with such name: %s does not exist in DB", roleName.name())));
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}
