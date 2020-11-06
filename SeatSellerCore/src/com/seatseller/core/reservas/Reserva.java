package com.seatseller.core.reservas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.seatseller.core.utilizadores.ClienteFinal;
import com.seatseller.core.utilizadores.Utilizador;
import com.seatseller.pagamentos.Pagamento;

public class Reserva {

	private ClienteFinal cliente;
	private List<LinhaReserva> linhaReservas;
	private LinhaReserva linhaRes;
	private int codigo;
	private List<Pagamento> pagamentos;

	/**
	 * Cria uma nova reserva
	 * 
	 * @param codigo
	 *            codigo da reserva
	 */
	public Reserva(int codigo) {
		this.codigo = codigo;
		linhaReservas = new ArrayList<LinhaReserva>();
		pagamentos = new ArrayList<Pagamento>();
	}

	/**
	 * Responsabiliza um cliente pela reserva
	 * 
	 * @param cli
	 *            cliente responsavel pela reserva
	 */
	public void setCliente(Optional<Utilizador> cli) {
		if (cli.isPresent() && cli.get() instanceof ClienteFinal) {
			this.cliente = (ClienteFinal) cli.get();
		}
	}

	/**
	 * Cria uma nova linha de reserva
	 * 
	 * @param date
	 *            data da linha de reserva
	 * @param time
	 *            hora da linha de reserva
	 */
	public void novaLinha(LocalDate date, LocalTime time) {
		this.linhaRes = new LinhaReserva(date, time);
	}

	/**
	 * Devolve a linha de Reserva mais recente da reserva
	 * 
	 * @return linha de Reserva mais recente da reserva
	 */
	public LinhaReserva getLinhaR() {
		return linhaRes;
	}

	/**
	 * Devolve a data da linha de Reserva mais recente da reserva
	 * 
	 * @return data da linha de Reserva mais recente da reserva
	 */
	public LocalDate getDataCorrente() {
		return linhaRes.getDate();
	}

	/**
	 * Devolve a hora da linha de Reserva mais recente da reserva
	 * 
	 * @return hora da linha de Reserva mais recente da reserva
	 */
	public LocalTime getHoraCorrente() {
		return linhaRes.getTime();
	}

	/**
	 * Adiciona a linha de reserva a lista de linhas de reservas da reserva
	 */
	public void finalizar() {
		linhaReservas.add(linhaRes);
	}

	/**
	 * Devolve o preco associado a linha de reserva mais recente
	 * 
	 * @return preco associado a linha de reserva mais recente
	 */
	public double getPreco() {
		return linhaRes.getPreco();
	}

	/**
	 * Devolva o codigo da reserva
	 * 
	 * @return codigo da reserva
	 */
	public String getCodigo() {
		return Integer.toString(this.codigo);
	}

	/**
	 * Devolve o valor que falta pagar associado a reserva
	 * 
	 * @return valor que falta pagar associado a reserva
	 */
	public double getValorEmFalta() {
		double valorPago = 0;
		for (Pagamento p : pagamentos) {
			valorPago = p.getValorPago();
		}
		return linhaRes.getPreco() - valorPago;
	}

	/**
	 * Indica o cliente responsavel pela reserva
	 * 
	 * @return cliente responsavel pela reserva
	 */
	public ClienteFinal getCliente() {
		return this.cliente;
	}

	/**
	 * Associa um novo pagamento a lista de pagamentos da reserva
	 * 
	 * @param pg
	 *            pagamento novo
	 */
	public void registarPagamento(Pagamento pg) {
		pagamentos.add(pg);
	}

}
