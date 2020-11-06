package com.seatseller.core.lugares.alocacao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.lugares.Lugar;
import com.seatseller.core.lugares.TipoDeLugar;

/**
 * Estrategia que consiste em encontrar o lugar cuja soma das distancias a todos
 * os lugares indisponiveis seja maior
 * 
 * @author Grupo 031
 *
 */
public class LugarMaisAfastadoStrategy implements IEncontrarLugarStrategy {

	@Override
	public Optional<Lugar> getLugar(Grelha g, TipoDeLugar tp, LocalDate d,
			LocalTime t) {
		List<Lugar> listIndisp = lugaresIndisponiveis(g.getListaLugares(), d,
				t);
		List<Lugar> disp = lugaresDisponiveis(g.getListaLugares(), d, t);
		return lugarMaisAfastado(disp, listIndisp);
	}

	/**
	 * Calcula a distancia entre dois lugares
	 * 
	 * @param l1
	 *            lugar
	 * @param l2
	 *            lugar
	 * @return distancia entre os lugares dados
	 */
	private static double distancia(Lugar l1, Lugar l2) {
		return Math.sqrt(
				(l1.getX() - l2.getX()) ^ 2 + (l1.getY() - l2.getY()) ^ 2);
	}

	/**
	 * Devolve o lugar cuja soma das distancias a todos os lugares indisponiveis
	 * seja maior
	 * 
	 * @param disponiveis
	 *            lista de lugares disponiveis
	 * @param indisponiveis
	 *            lista de lugares indisponiveis
	 * @return lugar mais afastado
	 */
	private static Optional<Lugar> lugarMaisAfastado(List<Lugar> disponiveis,
			List<Lugar> indisponiveis) {
		double maiorDistancia = 0;
		double currentDist;
		Optional<Lugar> maisAfastado = Optional.empty();

		for (Lugar l : disponiveis) {
			currentDist = 0;
			for (Lugar ind : indisponiveis) {
				currentDist += distancia(l, ind);
			}
			if (currentDist > maiorDistancia) {
				maisAfastado = Optional.ofNullable(l);
			}
		}

		return maisAfastado;
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

	/**
	 * Devolva uma lista de lugares indisponiveis na data/hora dados
	 * 
	 * @param lista
	 *            lista de lugares
	 * @param d
	 *            data na qual o lugar deve estar indisponivel
	 * @param t
	 *            hora na qual o lugar deve estar indisponivel
	 * @return lista de lugares indisponiveis na data/hora dados
	 */
	private static List<Lugar> lugaresIndisponiveis(List<Lugar> lista, LocalDate d,
			LocalTime t) {
		List<Lugar> listIndisp = new ArrayList<Lugar>();
		for (Lugar l : lista) {
			if (!l.disponivel(d, t)) {
				listIndisp.add(l);
			}
		}
		return listIndisp;
	}

}
