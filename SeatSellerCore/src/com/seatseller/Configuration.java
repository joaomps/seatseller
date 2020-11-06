package com.seatseller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
 * Configuration gathers all possible configuration variables defined in the config.properties files.
 * 
 *  Configuration follows the singleton pattern.
 */
public class Configuration {

	private static Configuration instance;

	private Properties propriedades;

	private InputStream input;

	private Configuration() {
		try {
			propriedades = new Properties();
			input = new FileInputStream("config.properties");
			propriedades.load(input);
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Devolve uma instancia de configuration
	 * 
	 * @ensures A instancia devolvida nao sera null
	 */
	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
		}
		return instance;
	}

	/**
	 * Devolve o valor da propriedade cativar no ficheiro config.properties
	 * 
	 * @return true se cativar = true, false se cativar = false
	 */
	public boolean cativarDuranteReservas() {
		return propriedades.getProperty("cativar").equals("true");
	}

	/**
	 * Devolve o valor a retirar do cartao quando se efetua um pagamento
	 * 
	 * @return valor a retirar do cartao
	 */
	public double valorDuranteReservas() {
		if (propriedades.containsKey("retirar")) {
			return Double.parseDouble(propriedades.getProperty("retirar"));
		} else {
			return 1.0;
		}
	}

	/**
	 * Dado o nome da propriedade devolve o valor presente no ficheiro
	 * config.properties
	 * 
	 * @param nomePropriedade
	 *            nome da propriedade presente no ficheiro
	 * @return Valor associado a propriedade no ficheiro
	 */
	public String prop(String nomePropriedade) {
		return propriedades.getProperty(nomePropriedade);
	}

}
