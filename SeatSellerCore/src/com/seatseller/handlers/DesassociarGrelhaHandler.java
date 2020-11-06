package com.seatseller.handlers;

import java.util.Optional;

import com.seatseller.api.IDesassociarGrelhaHandler;
import com.seatseller.api.INotificacaoReceiver;
import com.seatseller.core.lugares.CatalogoGrelhas;
import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.utilizadores.Funcionario;
import com.seatseller.core.utilizadores.Utilizador;

public class DesassociarGrelhaHandler implements IDesassociarGrelhaHandler {

	private Funcionario user;
	private CatalogoGrelhas catalogoGrelhas;

	public DesassociarGrelhaHandler(Utilizador utilizador,
			CatalogoGrelhas catG) {
		user = (Funcionario) utilizador;
		catalogoGrelhas = catG;
	}

	@Override
	public void desassociarGrelha(String desig, INotificacaoReceiver c) {
		Optional<Grelha> g = Optional
				.ofNullable(catalogoGrelhas.getGrelha(desig));
		if (g.isPresent()) {
			user.desassociarGrelha(g, c);
		}
	}
}
