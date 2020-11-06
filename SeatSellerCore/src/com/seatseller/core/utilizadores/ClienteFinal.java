package com.seatseller.core.utilizadores;

import java.util.ArrayList;
import java.util.List;

import com.seatseller.core.reservas.Reserva;
import com.seatseller.pagamentos.CartaoCredito;
import com.seatseller.pagamentos.Pagamento;

public class ClienteFinal extends Utilizador {

	private List<CartaoCredito> cartoes;
	private List<Pagamento> pagamentos;
	private List<Reserva> reservas;

	public ClienteFinal(String u, String p) {
		super(
				u,
				p);
		cartoes = new ArrayList<CartaoCredito>();
		pagamentos = new ArrayList<Pagamento>();
		reservas = new ArrayList<Reserva>();
	}

	public boolean temCC(String num) {
		boolean encontrou = false;
		for (CartaoCredito c : cartoes) {
			if (c.getNumero().equals(num)) {
				encontrou = true;
				return encontrou;
			}
		}
		return encontrou;
	}

	public CartaoCredito criaCC(String num, int ccv, int mes, int ano) {
		CartaoCredito cc = new CartaoCredito(num, ccv, mes, ano);
		cartoes.add(cc);
		return cc;
	}

	public void registarPagamento(Pagamento pg) {
		pagamentos.add(pg);
	}

	public void associarReserva(Reserva res) {
		reservas.add(res);

	}
}
