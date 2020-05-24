package ru.mycreation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mycreation.entities.VerificationToken;
import ru.mycreation.repository.TokenRepository;

import java.util.List;

@Service
public class TokenService {

    private TokenRepository tokenRepository;

    @Autowired
    public void setTokenRepository(TokenRepository tokenRepository) {this.tokenRepository = tokenRepository;}

    public VerificationToken findOneByToken(String token) { return tokenRepository.findOneByToken(token);
    }

    @Transactional
    public void save(VerificationToken vt) { tokenRepository.save(vt);
    }

    @Transactional
    public void delete(VerificationToken vt) { tokenRepository.delete(vt);
    }

    public List<VerificationToken> findAll() { return tokenRepository.findAll();
    }
}
