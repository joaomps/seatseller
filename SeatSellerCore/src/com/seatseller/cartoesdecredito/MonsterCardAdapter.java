package com.seatseller.cartoesdecredito;

import com.monstercard.Card;
import com.monstercard.MonsterCardAPI;

public class MonsterCardAdapter implements ISistemaDeCartoesDeCreditoAdapter {

	private Card cartao;
	private MonsterCardAPI mc;

	public MonsterCardAdapter() {
		mc = new MonsterCardAPI();
	}

	@Override
	public boolean validar(String num, int ccv, int mes, int ano) {
		cartao = new Card(num, Integer.toString(ccv), Integer.toString(mes),
				Integer.toString(ano));
		return mc.isValid(cartao);
	}

	@Override
	public boolean cativar(String num, int ccv, int mes, int ano, double qt) {
		boolean cativou = false;
		if (validar(num, ccv, mes, ano)) {
			cartao = new Card(num, Integer.toString(ccv), Integer.toString(mes),
					Integer.toString(ano));
			mc.block(cartao, qt);
			cativou = true;
		}
		return cativou;
	}

	@Override
	public boolean retirar(String num, int ccv, int mes, int ano, double qt) {
		boolean retirou = false;
		if (validar(num, ccv, mes, ano)) {
			cartao = new Card(num, Integer.toString(ccv), Integer.toString(mes),
					Integer.toString(ano));
			mc.charge(cartao, qt);
			retirou = true;
		}
		return retirou;
	}

}
