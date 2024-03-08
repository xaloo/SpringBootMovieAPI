package api.application.security;

import api.application.entities.dbo.EmployeeDBO;
import api.application.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public UserDetailServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        EmployeeDBO employee = employeeRepository.findOneByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Username with email %s does not exist",email)));
        return new UserDetailsImpl(employee);
    }
}
