package com.seatseller.core.lugares;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.seatseller.core.reservas.LinhaReserva;

/**
 * Representa um lugar e as operacoes associadas ao uso deste
 * 
 * @author 031
 *
 */

public class Lugar {

	private int i;
	private int j;
	private Optional<TipoDeLugar> tipo;
	private Grelha g;
	private List<LinhaReserva> linhasReserva;

	/**
	 * Cria um novo lugar
	 * 
	 * @param i
	 *            coordenada x
	 * @param j
	 *            coordenada y
	 * @param tipo
	 *            tipo de lugar associado ao lugar
	 * @param g
	 *            grelha onde o lugar se encontra
	 */
	public Lugar(int i, int j, Optional<TipoDeLugar> tipo, Grelha g) {
		this.i = i;
		this.j = j;
		this.tipo = tipo;
		this.g = g;
		linhasReserva = new ArrayList<LinhaReserva>();
	}

	/**
	 * Devolve a coordenada x do lugar
	 * 
	 * @return coordenada x do lugar
	 */
	public int getX() {
		return this.i;
	}

	/**
	 * Devolve a coordenada y do lugar
	 * 
	 * @return coordenada y do lugar
	 */
	public int getY() {
		return this.j;
	}

	/**
	 * Altera o tipo do lugar para o tipo dado
	 * 
	 * @param tp
	 *            tipo de lugar novo
	 */
	public void definirTipo(Optional<TipoDeLugar> tp) {
		this.tipo = tp;

	}

	/**
	 * Devolva o tipo de lugar associado a este lugar
	 * 
	 * @return o tipo de lugar associado a este lugar
	 */
	public TipoDeLugar getDesignacaoTipo() {
		if (tipo.isPresent()) {
			return tipo.get();
		} else {
			return null;
		}
	}

	/**
	 * Devolva designacao do tipo de lugar associado a este lugar
	 * 
	 * @return designacao do tipo de lugar associado a este lugar
	 */
	public String tipo() {
		return getDesignacaoTipo().getDesig();
	}

	/**
	 * Devolve o preco associado ao lugar
	 * 
	 * @return preco associado ao lugar
	 */
	public double getPreco() {
		double preco = 0;
		double ind = g.getIndice();
		if (tipo.isPresent()) {
			preco = tipo.get().getPreco(ind);
		}
		return preco;
	}

	/**
	 * Verifica se o lugar esta disponivel para a data/hora dada
	 * 
	 * @param date
	 *            data na qual verifica se o lugar esta disponivel
	 * @param time
	 *            hora na qual verifica se o lugar esta disponivel
	 * @return true caso o lugar esteja disponivel na data/hora dada, false caso
	 *         contrario
	 */
	public boolean disponivel(LocalDate date, LocalTime time) {
		boolean disponivel = true;
		if (!linhasReserva.isEmpty()) {
			for (LinhaReserva l : linhasReserva) {
				if (l.getDate().equals(date) && l.getTime().equals(time)) {
					disponivel = false;
				}
			}
		}
		return disponivel;
	}

	/**
	 * Associa uma nova linha de reserva ao lugar
	 * 
	 * @param linhaR
	 *            linha de reserva a associar ao lugar
	 */
	public void adicionaLinha(LinhaReserva linhaR) {
		linhasReserva.add(linhaR);
	}

	/**
	 * Devolve a representacao textual do lugar
	 */
	@Override
	public String toString() {
		if (tipo.isPresent()) {
			TipoDeLugar tp = tipo.get();
			return "Lugar " + tp.getDesig() + ":" + i + "," + j + " em " + g.getDesig();
		}
		return "";
	}

}
