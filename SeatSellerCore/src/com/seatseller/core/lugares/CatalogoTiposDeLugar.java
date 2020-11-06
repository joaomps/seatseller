package com.seatseller.core.lugares;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Representa um catalogo de tipos de lugar
 * 
 * @author Grupo 031
 *
 */

public class CatalogoTiposDeLugar {
	private Map<String, TipoDeLugar> tipos;
	private Optional<TipoDeLugar> tipoPadrao;

	public CatalogoTiposDeLugar() {
		tipos = new HashMap<>();
		tipoPadrao = Optional.empty();
	}

	/**
	 * Cria um novo tipo de lugar e adiciona-o ao catalogo
	 * 
	 * @param desig
	 *            nome do tipo de lugar a ser criado
	 * @param desc
	 *            descricao do tipo de lugar a ser criado
	 * @param preco
	 *            preco do tipo de lugar a ser criado
	 * @param padrao
	 *            se true este tipo de lugar sera o tipo de lugar padrao, se
	 *            false nao sera o tipo padrao
	 */
	public void criarTipoDeLugar(String desig, String desc, double preco,
			boolean padrao) {
		TipoDeLugar t = new TipoDeLugar(desig, desc, preco);
		tipos.put(desig, t);
		if (padrao) {
			tipoPadrao = Optional.ofNullable(t);
		}
	}

	/**
	 * Devolve o tipo de lugar padrao do catalogo caso este exista
	 * 
	 * @return tipo de lugar padrao ou empty
	 */
	public Optional<TipoDeLugar> getPadrao() {
		return tipoPadrao;
	}

	/**
	 * Devolve o tipo de lugar cuja designacao e igual a dada caso exista algum
	 * no catalogo
	 * 
	 * @param desig
	 *            designacao do tipo de lugar a procurar
	 * @return tipo de lugar ou empty
	 */
	public Optional<TipoDeLugar> getTipo(String desig) {
		return Optional.ofNullable(tipos.get(desig));
	}
}
