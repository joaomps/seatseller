package com.seatseller.core.lugares;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.seatseller.api.wrappers.Combinacao;

/**
 * Representa um catalogo de grelhas
 * 
 * @author Grupo 031
 *
 */

public class CatalogoGrelhas {

	private Map<String, Grelha> tipos;
	private List<Combinacao> combinacoes;

	public CatalogoGrelhas() {
		tipos = new HashMap<>();
		combinacoes = new ArrayList<Combinacao>();
	}

	/**
	 * Acrescenta uma grelha ao catalogo
	 * 
	 * @param g
	 *            grelha que sera acrescentada ao catalogo
	 */
	public void acrescentaGrelha(Grelha g) {
		String nomeGrelha = g.getDesig();
		tipos.put(nomeGrelha, g);
	}

	/**
	 * Verifica se a grelha com a designacao dada ja se encontra no catalogo
	 * 
	 * @param designacao
	 *            nome da grelha
	 * @return true caso a grelha ja esteja no catalogo, false caso ainda nao
	 *         esteja no catalogo
	 */
	public boolean existeGrelha(String designacao) {
		return tipos.containsKey(designacao);
	}

	/**
	 * Devolve uma grelha do catalogo dada a sua designacao
	 * 
	 * @param desig
	 *            nome da grelha
	 * @return grelha cujo parametro designacao seja igual a 'desig'
	 */
	public Grelha getGrelha(String desig) {
		return tipos.get(desig);
	}

	/**
	 * Devolve um objeto iteravel que contem as combinacoes <nome da grelha,
	 * designacao do tipo de lugar, preco do lugar> para os lugares disponiveis
	 * da grelha
	 * 
	 * @param date
	 *            data na qual o lugar esta disponivel
	 * @param time
	 *            hora na qual o lugar esta disponivel
	 * @return objeto iteravel do tipo Combinacao
	 */
	public Iterable<Combinacao> getCombinacoes(LocalDate date, LocalTime time) {
		for (Grelha g : tipos.values()) {
			List<Combinacao> lc = g.getCombinacoes(date, time);

			for (Combinacao c : lc) {
				combinacoes.add(c);
			}
		}
		return combinacoes;
	}
}
