package com.seatseller.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.seatseller.api.ICriarGrelhaHandler;
import com.seatseller.api.exceptions.DoesNotExistException;
import com.seatseller.api.exceptions.NonUniqueException;
import com.seatseller.core.lugares.CatalogoTiposDeLugar;
import com.seatseller.core.lugares.TipoDeLugar;
import com.seatseller.core.lugares.CatalogoGrelhas;
import com.seatseller.core.lugares.Grelha;

public class CriarGrelhaHandler implements ICriarGrelhaHandler {

	private CatalogoGrelhas catalogoGrelhas;
	private CatalogoTiposDeLugar catalogoTL;
	private Grelha g;
	private List<String> designacoes = new ArrayList<String>();

	public CriarGrelhaHandler(CatalogoTiposDeLugar catTipos,
			CatalogoGrelhas catG) {
		catalogoGrelhas = catG;
		catalogoTL = catTipos;
	}

	@Override
	public void iniciarGrelha(String desig, double ind)
			throws NonUniqueException {
		if (!designacoes.contains(desig)) {
			g = new Grelha(desig, ind);
			designacoes.add(desig);
		} else {
			throw new NonUniqueException(
					"J� existe uma grelha com esta designa��o");
		}
	}

	@Override
	public Optional<String> indicarDimensao(int i, int j) {

		Optional<String> tlp;
		Optional<TipoDeLugar> padrao = catalogoTL.getPadrao();

		// padrao a null se nao existir
		g.criaLugares(i, j, padrao);
		if (padrao.isPresent()) {
			tlp = Optional.ofNullable(padrao.get().getDesig());
		} else {
			tlp = Optional.empty();
		}

		return tlp;
	}

	@Override
	public void indicarTipoPadrao(String desig) throws DoesNotExistException {

		Optional<TipoDeLugar> tp = catalogoTL.getTipo(desig);
		if (tp.isPresent()) {
			g.defineTipoLugarPadrao(tp);
		} else {
			throw new DoesNotExistException(
					"Nao existe um tipo de lugar com a designacao" + desig);
		}

	}

	@Override
	public void indicarTipoLugar(int i, int j, String desig)
			throws DoesNotExistException {
		Optional<TipoDeLugar> tp = catalogoTL.getTipo(desig);
		if (g.coordenadasValidas(i, j) && tp.isPresent()) {
			g.defineTipoLugar(i, j, tp);
		} else if (!g.coordenadasValidas(i, j)) {
			throw new DoesNotExistException("As coordenadas nao sao validas");
		} else if (!tp.isPresent()) {
			throw new DoesNotExistException("O tipo de lugar nao existe");
		}
	}

	@Override
	public void terminar() {
		catalogoGrelhas.acrescentaGrelha(g);
	}

}
