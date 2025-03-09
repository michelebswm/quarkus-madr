package br.com.madr.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.exception.ConstraintViolationException;

import br.com.madr.domain.Livro;
import br.com.madr.dto.response.LivroDTOResponse;
import br.com.madr.exception.ApplicationServiceException;
import br.com.madr.repository.LivroRepository;
import br.com.madr.utils.StringUtils;
import br.com.madr.utils.message.MessageService;
import br.com.madr.vo.LivroVO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class LivroService {

    private static final Logger LOG = Logger.getLogger(LivroService.class.getName());

    @Inject
    Validator validator;

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public LivroDTOResponse getById(Long id) throws ApplicationServiceException {
        LOG.log(Level.INFO, "LivroService: getById");

        Livro livro = this.livroRepository.findById(id);

        if (livro == null) {
            LOG.log(Level.INFO, "Debug na execucao do LivroService: findById = n\u00e3o existe - id={0}", id);
            throw new ApplicationServiceException("livro.naocadastrado", Response.Status.NOT_FOUND.getStatusCode());
        }

        return LivroDTOResponse.fromEntity(livro);
    }

    @Transactional
    public LivroDTOResponse register(LivroVO livroVO) throws ApplicationServiceException {
        LOG.log(Level.INFO, "LivroService: register");

        if (this.livroRepository.findByTitulo(StringUtils.sanitizeName(livroVO.getTitulo())).isPresent()) {
            throw new ApplicationServiceException("livro.jacadastrado", Response.Status.CONFLICT.getStatusCode());
        }

        try {
            Livro livro = livroVO.toEntity();
            this.livroRepository.persistAndFlush(livro);
            return LivroDTOResponse.fromEntity(livro);
        } catch (ConstraintViolationException cve) {
            throw new ApplicationServiceException("livro." + cve.getConstraintName());
        } catch (jakarta.validation.ConstraintViolationException cve) {
            System.out.println(cve.getMessage());
            throw new ApplicationServiceException("message.parametrosnaoinformados",
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    new MessageService(cve).getErrors()
            );
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error in the execution of LivroService: register");
            throw new ApplicationServiceException("livro.erro", new String[]{"register"});
        }
    }

    @Transactional
    public void update(Long id, LivroVO livroVO) throws ApplicationServiceException {
        LOG.log(Level.INFO, "LivroService: update");
        Livro livro = this.livroRepository.findById(id);

        if (livro == null) {
            throw new ApplicationServiceException("livro.naocadastrado", Response.Status.NOT_FOUND.getStatusCode());
        }

        try {
            this.livroRepository.validAndMerge(livroVO.toEntity());
        } catch (ConstraintViolationException hcve) {
            throw new ApplicationServiceException("livro." + hcve.getConstraintName());
        } catch (jakarta.validation.ConstraintViolationException cve) {
            throw new ApplicationServiceException("message.parametrosnaoinformados", Response.Status.BAD_REQUEST.getStatusCode(), new MessageService(cve).getErrors());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Erro na execucao do LivroService: update", e);
            throw new ApplicationServiceException("livro.erro", new String[]{"update"});
        }

    }

    @Transactional
    public void deleteById(Long id) throws ApplicationServiceException{
        LOG.log(Level.INFO, "LivroService: deleteById");
        Livro livro = this.livroRepository.findById(id);

        if (livro == null) {
            throw new ApplicationServiceException("livro.naocadastrado", Response.Status.NOT_FOUND.getStatusCode());
        }

        try{
            this.livroRepository.deleteById(id);
        }catch (Exception e) {
            LOG.log(Level.SEVERE, "Erro na execucao do LivroService: deleteById", e);
            throw new ApplicationServiceException("livro.erro", new String[]{"deleteById"},
                    Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
    }

}
