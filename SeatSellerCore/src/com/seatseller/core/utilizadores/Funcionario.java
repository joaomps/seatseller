package com.seatseller.core.utilizadores;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import com.seatseller.api.INotificacaoReceiver;

import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.lugares.Lugar;

/**
 * Representa um funcionario
 * 
 * @author Grupo 031
 *
 */
public class Funcionario extends Utilizador implements Observer {

	// Hora de início de turnos
	private LocalTime start;
	// Hora de final de turnos
	private LocalTime end;

	private List<INotificacaoReceiver> receivers;

	/**
	 * Cria um novo funcionario
	 * 
	 * @param u
	 *            nome da conta do funcionario
	 * @param p
	 *            password da conta do funcionario
	 * @param start
	 *            hora de inicio do turno
	 * @param end
	 *            hora de fim do turno
	 */
	public Funcionario(String u, String p, LocalTime start, LocalTime end) {
		super(
				u,
				p);
		this.start = start;
		this.end = end;
		receivers = new ArrayList<INotificacaoReceiver>();
	}

	/**
	 * Deve fazser login apenas se a password estiver correta, e se a hora
	 * actual estiver entre as horas de inicio e final de turnos deste
	 * Funcionario.
	 * 
	 * @see com.seatseller.core.utilizadores.Utilizador#tryLogin(java.lang.String)
	 */
	@Override
	public boolean tryLogin(String pw) {
		LocalTime current = LocalTime.now();
		return super.tryLogin(pw) && current.isAfter(start)
				&& current.isBefore(end);

		// nao leva hora em conta, hora actual?
		// return super.tryLogin(pw);
	}

	/**
	 * O funcionario ira receber notificacoes da grelha dada
	 * 
	 * @param g
	 *            grelha
	 * @param c
	 *            objeto que recebera notificacoes
	 */
	public void associarGrelha(Optional<Grelha> g, INotificacaoReceiver c) {
		if (g.isPresent()) {
			g.get().addObserver(this);
			this.adicionaNotificationReceiver(c);
		}
	}

	/**
	 * Adiciona objeto a lista de objetos que recebem notificacoes
	 * 
	 * @param c
	 *            objeto que ira receber notificacoes
	 */
	private void adicionaNotificationReceiver(INotificacaoReceiver c) {
		receivers.add(c);

	}

	/**
	 * O funcionario ira deixar de receber notificacoes da grelha dada
	 * 
	 * @param g
	 *            grelha
	 * @param c
	 *            objeto que ira deixar de receber notificacoes
	 */
	public void desassociarGrelha(Optional<Grelha> g, INotificacaoReceiver c) {
		if (g.isPresent()) {
			g.get().deleteObserver(this);
			this.removeNotificationReceiver(c);
		}
	}

	/**
	 * Remove objeto a lista de objetos que recebem notificacoes
	 * 
	 * @param c
	 *            objeto que ira deixa de receber notificacoes
	 */
	private void removeNotificationReceiver(INotificacaoReceiver c) {
		receivers.remove(c);

	}

	@Override
	public void update(Observable o, Object arg) {
		Grelha g = (Grelha) o;
		Lugar l = (Lugar) arg;
		for (INotificacaoReceiver c : receivers) {
			c.notificar(g.getDesig(), l.toString());
		}
	}

}
