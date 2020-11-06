package com.seatseller.core.reservas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.seatseller.core.lugares.Lugar;

public class LinhaReserva {

	private LocalDate date;
	private LocalTime time;
	private List<Lugar> listaLugares;

	/**
	 * Cria uma nova linha de reserva
	 * 
	 * @param date
	 *            data da linha de reserva
	 * @param time
	 *            hora da linha de reserva
	 */
	public LinhaReserva(LocalDate date, LocalTime time) {
		this.date = date;
		this.time = time;
		listaLugares = new ArrayList<Lugar>();
	}

	/**
	 * Devolve a data da linha de reserva
	 * 
	 * @return data da linha de reserva
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Devolve a hora da linha de reserva
	 * 
	 * @return hora da linha de reserva
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * Associa um novo lugar a linha de reserva
	 * 
	 * @param lugar
	 *            lugar novo
	 */
	public void associaLugar(Lugar lugar) {
		listaLugares.add(lugar);
	}

	/**
	 * Devolve o preco total associado aos lugares presentes na linha de reserva
	 * 
	 * @return preco total associado aos lugares presentes na linha de reserva
	 */
	public double getPreco() {
		double preco = 0;
		for (Lugar lug : listaLugares) {
			preco += lug.getPreco();
		}
		return preco;
	}

}
