package com.seatseller.core.lugares.alocacao;

import com.seatseller.Configuration;

/**
 * Representa uma fabrica de estrategias para encontrar lugares disponiveis
 * 
 * @author Joto
 *
 */
public class EncontrarLugarStrategyFactory {

	private static EncontrarLugarStrategyFactory instance;

	private IEncontrarLugarStrategy strat;

	/**
	 * Devolve uma instancia da fabrica de estrategias para encontrar lugares
	 * disponiveis
	 * 
	 * @return instancia da fabrica de estrategias para encontrar lugares
	 *         disponiveis
	 */
	public static EncontrarLugarStrategyFactory getInstance() {
		if (instance == null) {
			instance = new EncontrarLugarStrategyFactory();
		}
		return instance;
	}

	/**
	 * Devolve uma estrategia dependendo da que esta presente no ficheiro
	 * config.properties
	 * 
	 * @return estrategia para encontrar lugares disponiveis
	 */
	public IEncontrarLugarStrategy getEncontrarLugarStrategy() {
		Configuration config = Configuration.getInstance();
		String nomeEstrategia = config.prop("encontrarLugarStrategy");
			try {
				strat = (IEncontrarLugarStrategy) Class.forName(nomeEstrategia)
						.newInstance();
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				e.printStackTrace();
			}
		return strat;
	}
}
