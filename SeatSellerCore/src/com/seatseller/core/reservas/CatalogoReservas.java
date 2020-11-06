package com.seatseller.core.reservas;

import java.util.HashMap;
import java.util.Map;

/**
 * Representa um catalogo de reservas
 * 
 * @author Grupo 031
 *
 */
public class CatalogoReservas {

	private Map<String, Reserva> mapa = new HashMap<>();

	/**
	 * Adiciona uma nova reserva ao catalogo
	 * 
	 * @param r
	 *            reserva a adicionar ao catalogo
	 */
	public void registarReserva(Reserva r) {
		String cod = "R" + r.getCodigo();
		mapa.put(cod, r);
	}

	/**
	 * Devolve a reserva a qual o codigo dado corresponde
	 * 
	 * @param codigo
	 *            codigo de uma possivel reserva
	 * @return reserva a qual o codigo dado corresponde
	 */
	public Reserva getReserva(String codigo) {
		return mapa.get(codigo);
	}
}
