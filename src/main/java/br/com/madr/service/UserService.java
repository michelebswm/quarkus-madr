package br.com.madr.service;

import br.com.madr.domain.User;
import br.com.madr.exception.ApplicationServiceException;
import br.com.madr.repository.UserRepository;
import br.com.madr.utils.StringUtils;
import br.com.madr.utils.message.MessageService;
import br.com.madr.vo.UserVO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.hibernate.exception.ConstraintViolationException;

import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class UserService {

    private static final Logger log = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(UserVO userVO) throws ApplicationServiceException {
        log.log(Level.INFO, "Inserindo Usuario");
        if (this.userRepository.findByUsername(StringUtils.sanitizeName(userVO.getUsername())).isPresent()) {
            throw new ApplicationServiceException("user.jacadastrado");
        }

        User user = userVO.toEntity();

        try {
            this.userRepository.persistAndFlush(user);
        } catch (ConstraintViolationException e) {
            throw new ApplicationServiceException("user." + e.getConstraintName());
        } catch (jakarta.validation.ConstraintViolationException cve){
            throw new ApplicationServiceException("message.parametrosnaoinformados",
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    new MessageService(cve).getErrors()
            );
        } catch (Exception e){
            log.log(Level.SEVERE, "Error in the execution of UserService: register");
            throw new ApplicationServiceException("user.erro", new String[]{"register"});
        }


    }
}
