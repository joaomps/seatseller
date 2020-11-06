package com.seatseller.cartoesdecredito;

import pt.portugueseexpress.PortugueseExpress;

public class PortugueseExpressAdapter
		implements ISistemaDeCartoesDeCreditoAdapter {

	private PortugueseExpress cartao;

	public PortugueseExpressAdapter() {
		cartao = new PortugueseExpress();
	}

	@Override
	public boolean validar(String num, int ccv, int mes, int ano) {
		cartao.setMonth(mes);
		cartao.setYear(ano);
		return cartao.validate();
	}

	@Override
	public boolean cativar(String num, int ccv, int mes, int ano, double qt) {
		cartao.block(qt);

		return true;

	}

	@Override
	public boolean retirar(String num, int ccv, int mes, int ano, double qt) {
		cartao.charge(qt);

		return true;
	}

}
