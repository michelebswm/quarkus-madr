package br.com.madr.service;

import br.com.madr.domain.Romancista;
import br.com.madr.dto.response.RomancistaDTOResponse;
import br.com.madr.exception.ApplicationServiceException;
import br.com.madr.repository.RomancistaRepository;
import br.com.madr.utils.message.MessageService;
import br.com.madr.vo.RomancistaVO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.core.Response;
import org.hibernate.exception.ConstraintViolationException;

import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class RomancistaService {

    private static final Logger LOG = Logger.getLogger(RomancistaService.class.getName());

    @Inject
    Validator validator;

    private final RomancistaRepository romancistaRepository;

    public RomancistaService(RomancistaRepository romancistaRepository) {
        this.romancistaRepository = romancistaRepository;
    }


    @Transactional
    public RomancistaDTOResponse register(RomancistaVO romancistaVO) throws ApplicationServiceException {
        LOG.log(Level.INFO, "RomancistaService : new register");

        if (this.romancistaRepository.findByNome(romancistaVO.getNome()).isPresent()){
            throw new ApplicationServiceException("romancista.jacadastrado", Response.Status.CONFLICT.getStatusCode());
        }

        try{
            Romancista romancista = romancistaVO.toEntity();
            this.romancistaRepository.persistAndFlush(romancista);
            return RomancistaDTOResponse.fromEntity(romancista);
        } catch (ConstraintViolationException cve){
            throw new ApplicationServiceException("romancista." + cve.getConstraintName());
        }catch (jakarta.validation.ConstraintViolationException cve) {
            System.out.println(cve.getMessage());
            throw new ApplicationServiceException("message.parametrosnaoinformados",
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    new MessageService(cve).getErrors()
            );
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error in the execution of RomancistaService: register");
            throw new ApplicationServiceException("romancista.erro", new String[]{"register"});
        }
    }

}
