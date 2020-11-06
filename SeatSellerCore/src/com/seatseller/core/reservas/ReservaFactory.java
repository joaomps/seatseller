package com.seatseller.core.reservas;

/**
 * Representa uma fabrica de codigos para reservas
 * 
 * @author Grupo 031
 *
 */
public class ReservaFactory {

	private int codigo;

	public ReservaFactory() {
		codigo = -1;
	}

	/**
	 * Devolve uma reserva com um codigo novo
	 * 
	 * @return reserva com um codigo novo
	 */
	public Reserva getProximaReserva() {
		novoCodigo();
		return new Reserva(codigo);
	}

	/**
	 * Cria um novo codigo
	 * 
	 * @return codigo novo
	 */
	private int novoCodigo() {
		return codigo += 1;
	}

}
