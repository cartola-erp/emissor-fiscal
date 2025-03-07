package net.cartola.emissorfiscal.loja;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LojaRepositoryTest {

    @Autowired
    private LojaRepository lojaRepository;

    @Test
    public void findAndUpdateLojaByCnpj() {
        Optional<Loja> opLoja = lojaRepository.findLojaByCnpj("03827827001323");
        assertNotNull(opLoja);
        assertTrue(opLoja.isPresent());
        Loja loja = opLoja.get();
        System.out.println(loja);

        loja.setNfceCodigoSegurancaId(99);
        loja.setNfceCodigoSegurancaNumero("akiwoenk-akd2-aldk-açk3=2039j238n82a");

        lojaRepository.save(loja);

    }
}