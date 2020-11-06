package com.seatseller.handlers;

import java.util.Optional;

import com.seatseller.api.IAssociarGrelhaHandler;
import com.seatseller.api.INotificacaoReceiver;
import com.seatseller.core.lugares.CatalogoGrelhas;
import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.utilizadores.Funcionario;
import com.seatseller.core.utilizadores.Utilizador;

public class AssociarGrelhaHandler implements IAssociarGrelhaHandler {

	private Funcionario user;
	private CatalogoGrelhas catalogoGrelhas;

	public AssociarGrelhaHandler(Utilizador utilizador, CatalogoGrelhas catG) {
		user = (Funcionario) utilizador;
		catalogoGrelhas = catG;
	}

	@Override
	public void associarGrelha(String desig, INotificacaoReceiver c) {
		Optional<Grelha> g = Optional
				.ofNullable(catalogoGrelhas.getGrelha(desig));
		if (g.isPresent()) {
			user.associarGrelha(g, c);
		}
	}
}
