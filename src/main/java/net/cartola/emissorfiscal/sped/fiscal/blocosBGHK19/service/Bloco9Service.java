package net.cartola.emissorfiscal.sped.fiscal.blocosBGHK19.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.SpedFiscal;
import net.cartola.emissorfiscal.sped.fiscal.bloco9.Bloco9;

@Service
public class Bloco9Service {

	public Bloco9 criarBloco(SpedFiscal spedFiscal) {
		// TODO Auto-generated method stub
		return null;
	}

	private Bloco9 criarBloco(String file) throws FileNotFoundException, IOException  {
		return criarBloco9(new File(file));
	}

	private Bloco9 criarBloco9(File file) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	
		
}
