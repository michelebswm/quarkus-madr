package br.com.madr.service;

import br.com.madr.domain.User;
import br.com.madr.exception.ApplicationServiceException;
import br.com.madr.repository.UserRepository;
import br.com.madr.utils.StringUtils;
import br.com.madr.utils.message.MessageService;
import br.com.madr.utils.message.MessageServiceError;
import br.com.madr.utils.validator.FieldValidator;
import br.com.madr.vo.UserVO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import jakarta.ws.rs.core.Response;
import org.hibernate.exception.ConstraintViolationException;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class UserService {

    private static final Logger LOG = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;

    @Inject
    Validator validator;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(UserVO userVO) throws ApplicationServiceException {
        LOG.log(Level.INFO, "UserService: register");
        if (this.userRepository.findByUsername(StringUtils.sanitizeName(userVO.getUsername())).isPresent()) {
            throw new ApplicationServiceException("user.jacadastrado");
        }

        try {
            this.userRepository.persistAndFlush(userVO.toEntity());
        } catch (ConstraintViolationException e) {
            throw new ApplicationServiceException("user." + e.getConstraintName());
        } catch (jakarta.validation.ConstraintViolationException cve) {
            System.out.println(cve.getMessage());
            throw new ApplicationServiceException("message.parametrosnaoinformados",
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    new MessageService(cve).getErrors()
            );
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error in the execution of UserService: register");
            throw new ApplicationServiceException("user.erro", new String[]{"register"});
        }
    }


    public void update(UserVO userVO) throws ApplicationServiceException {
        LOG.log(Level.INFO, "UserService: update");

        Optional<User> existingUserOpt = this.userRepository.findByUsername(StringUtils.sanitizeName(userVO.getUsername()));
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            if (!existingUser.getId().equals(userVO.getId())){
                throw new ApplicationServiceException("user.jacadastrado");
            }
        }

        try{
            this.userRepository.validAndMerge(userVO.toEntity());
        } catch (ConstraintViolationException hcve){
            throw new ApplicationServiceException("user."+ hcve.getConstraintName());
        }catch (jakarta.validation.ConstraintViolationException cve) {
            System.out.println(cve.getMessage());
            throw new ApplicationServiceException("message.parametrosnaoinformados",
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    new MessageService(cve).getErrors()
            );
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error in the execution of UserService: update");
            throw new ApplicationServiceException("user.erro", new String[]{"update"});
        }

    }
}
