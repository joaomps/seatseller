package com.seatseller.core.lugares.alocacao;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.lugares.Lugar;
import com.seatseller.core.lugares.TipoDeLugar;

/**
 * Estrategia que consiste em encontrar o primeiro lugar disponivel pela ordem
 * em que se encontra na grelha
 * 
 * @author Grupo 031
 *
 */

public class PrimeiroLugarStrategy implements IEncontrarLugarStrategy {

	@Override
	public Optional<Lugar> getLugar(Grelha g, TipoDeLugar tp, LocalDate d,
			LocalTime t) {
		Optional<Lugar> lugarDisp = Optional.empty();
		List<Lugar> lista = g.getListaLugares();

		// percorre lugares
		for (Lugar l : lista) {
			// verifica se lugar esta disponivel
			if (l.disponivel(d, t) && l.tipo().equals(tp.getDesig())) {
				// comparara tipo do lugar ao tipo dado
				lugarDisp = Optional.ofNullable(l);
				break;
			}
		}

		return lugarDisp;
	}

}
