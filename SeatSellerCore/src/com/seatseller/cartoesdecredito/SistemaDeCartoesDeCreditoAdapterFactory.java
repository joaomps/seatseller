package com.seatseller.cartoesdecredito;

import com.seatseller.Configuration;

/**
 * Representa uma fabrica de adaptadores para sistemas de cartoes de credito
 * 
 * @author Grupo 031
 *
 */

public class SistemaDeCartoesDeCreditoAdapterFactory {

	private static SistemaDeCartoesDeCreditoAdapterFactory instance;

	private ISistemaDeCartoesDeCreditoAdapter adapter;

	/**
	 * Devolve uma instancia da fabrica de adaptadores para sistemas de cartoes
	 * de credito
	 * 
	 * @return instancia da fabrica de adaptadores
	 */
	public static SistemaDeCartoesDeCreditoAdapterFactory getInstance() {
		if (instance == null) {
			instance = new SistemaDeCartoesDeCreditoAdapterFactory();
		}
		return instance;
	}

	/**
	 * Devolve um adaptador para sistemas de cartoes de credito dependendo do
	 * valor presente no ficheiro config.properties
	 * 
	 * @return adaptador para sistemas de cartoes de credito
	 */
	public ISistemaDeCartoesDeCreditoAdapter getSistemaDeCartoesDeCreditoAdapter() {
		Configuration config = Configuration.getInstance();
		String nomeAdapter = config.prop("cartaoAdapter");
		try {
			adapter = (ISistemaDeCartoesDeCreditoAdapter) Class
					.forName(nomeAdapter).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		return adapter;
	}

}
