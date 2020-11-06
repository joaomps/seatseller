package com.seatseller.core.lugares.alocacao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.lugares.Lugar;
import com.seatseller.core.lugares.TipoDeLugar;

/**
 * Define estrategias de procura de lugar disponivel numa grelha
 * 
 * @author Grupo 031
 *
 */
public interface IEncontrarLugarStrategy {

	/**
	 * Devolve um lugar do tipo e da grelha indicados, disponivel na data e hora
	 * indicados ou empty se tal lugar nao existir
	 * 
	 * @param g
	 *            grelha onde o lugar se deve encontrar
	 * @param tp
	 *            tipo de lugar associado ao lugar
	 * @param d
	 *            data na qual o lugar deve estar disponivel
	 * @param t
	 *            hora na qual o lugar deve estar disponivel
	 * @return lugar do tipo e da grelha indicados, disponivel na data e hora
	 *         indicados ou empty se tal lugar nao existir
	 */
	Optional<Lugar> getLugar(Grelha g, TipoDeLugar tp, LocalDate d,
			LocalTime t);
}
