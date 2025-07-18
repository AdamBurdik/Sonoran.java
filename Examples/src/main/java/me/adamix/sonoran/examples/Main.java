package me.adamix.sonoran.examples;

import me.adamix.sonoran.Sonoran;
import me.adamix.sonoran.cad.SonoranCad;

public class Main {
	public static void main(String[] args) {

		String token = args[0];
		String communityId = args[1];
		String username = args[2];

		Sonoran sonoran = Sonoran.builder()
				.withCad(token, communityId)
				.build();

		SonoranCad cad = sonoran.cad();

		cad.getAccount(username, (cadAccount -> {
			System.out.println("Account: " + cadAccount);
		}));

		sonoran.shutdown();
	}
}