package com.seatseller.core.lugares.alocacao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.lugares.Lugar;
import com.seatseller.core.lugares.TipoDeLugar;

/**
 * Estrategia que consiste em encontrar um lugar disponivel aleatoriamente
 * 
 * @author Grupo 031
 *
 */

public class LugarAleatorioStrategy implements IEncontrarLugarStrategy {

	@Override
	public Optional<Lugar> getLugar(Grelha g, TipoDeLugar tp, LocalDate d,
			LocalTime t) {
		List<Lugar> disponiveis = lugaresDisponiveis(g.getListaLugares(), d, t);
		int tamanho = disponiveis.size();
		Random gerador = new Random();
		int posicaoRandom = gerador.nextInt(tamanho);

		return Optional.ofNullable(disponiveis.get(posicaoRandom));
	}

	/**
	 * Devolva uma lista de lugares disponiveis na data/hora dados
	 * 
	 * @param lista
	 *            lista de lugares
	 * @param d
	 *            data na qual o lugar deve estar disponivel
	 * @param t
	 *            hora na qual o lugar deve estar disponivel
	 * @return lista de lugares disponiveis na data/hora dados
	 */
	private static List<Lugar> lugaresDisponiveis(List<Lugar> lista, LocalDate d,
			LocalTime t) {
		List<Lugar> listIndisp = new ArrayList<Lugar>();
		for (Lugar l : lista) {
			if (l.disponivel(d, t)) {
				listIndisp.add(l);
			}
		}
		return listIndisp;
	}
}
