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

    public RomancistaDTOResponse getById(Long id) throws ApplicationServiceException {

        Romancista romancista = this.romancistaRepository.findById(id);

        if (romancista == null){
            LOG.info("Debug na execucao do RomancistaService: findById = não existe - id="+id);
            throw new ApplicationServiceException("romancista.naocadastrado", Response.Status.NOT_FOUND.getStatusCode());
        }
        
        return RomancistaDTOResponse.fromEntity(romancista);

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

    @Transactional
    public void updatePatch(Long id, RomancistaVO romancistaVO) throws ApplicationServiceException {
        LOG.log(Level.INFO, "RomancistaService : updatePatch");
        Romancista romancista = this.romancistaRepository.findById(id);

        if (romancista == null){
            throw new ApplicationServiceException("romancista.naocadastrado", Response.Status.NOT_FOUND.getStatusCode());
        }

        try{
            if (romancistaVO.getNome() != null){
                romancista.setNome(romancistaVO.getNome());
            }
            this.romancistaRepository.persistAndFlush(romancista);
        }catch (ConstraintViolationException cve){
            if (cve.getConstraintName() == null && cve.getSQLException() != null) {
                String sqlMessage = cve.getSQLException().getMessage();
                if (sqlMessage.contains("uk_nome_romancista")) {
                    throw new ApplicationServiceException("romancista.uk_nome_romancista");
                }
            }
            throw new ApplicationServiceException("romancista." + cve.getConstraintName());
        }catch (jakarta.validation.ConstraintViolationException cve) {
            System.out.println("cve: " + cve.getMessage());
            throw new ApplicationServiceException("message.parametrosnaoinformados",
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    new MessageService(cve).getErrors()
            );
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error in the execution of RomancistaService: updatePatch");
            throw new ApplicationServiceException("romancista.erro", new String[]{"updatePatch"});
        }
    }

    @Transactional
    public void deleteById(Long id) throws ApplicationServiceException {
        LOG.log(Level.INFO, "RomancistaService: deleteById");

        Romancista romancista = this.romancistaRepository.findById(id);
        if (romancista == null){
            LOG.info("Debug na execucao do RomancistaService: deleteById = não existe - id="+id);
            throw new ApplicationServiceException("romancista.naocadastrado", Response.Status.NOT_FOUND.getStatusCode());
        }

        try{
            this.romancistaRepository.deleteById(id);
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Erro na execucao do RomancistaService: deleteById", e);
            throw new ApplicationServiceException("romancista.erro", new String[] { "deleteById" },
                    Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
    }

}
