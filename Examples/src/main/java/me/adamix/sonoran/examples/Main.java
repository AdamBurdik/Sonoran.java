package me.adamix.sonoran.examples;

import me.adamix.sonoran.Sonoran;
import me.adamix.sonoran.cad.SonoranCad;
import me.adamix.sonoran.cad.data.general.CadAccount;
import me.adamix.sonoran.cad.data.Result;
import me.adamix.sonoran.cad.data.general.CadServer;

import java.util.List;

public class Main {
	public static void main(String[] args) {

		String token = args[0];
		String communityId = args[1];
		String username = args[2];

		Sonoran sonoran = Sonoran.builder()
				.withCad(token, communityId)
				.build();

		SonoranCad cad = sonoran.cad();

		cad.getAccount(username, (result -> {
			switch (result) {
				case Result.Success<CadAccount> success -> System.out.println("Account: " + success.value());
				case Result.Error<CadAccount> error -> System.out.println(error.message());
				case Result.Exception<CadAccount> exception -> throw new RuntimeException(exception.exception());
				default -> throw new IllegalStateException("Unexpected value: " + result);
			}
		}));

		cad.getServers((result) -> {
			switch (result) {
				case Result.Success<List<CadServer>> success -> {
					for (CadServer cadServer : success.value()) {
						System.out.println("Server: " + cadServer);
					}
				}
				case Result.Error<List<CadServer>> error -> System.out.println(error.message());
				case Result.Exception<List<CadServer>> exception -> throw new RuntimeException(exception.exception());
				default -> throw new IllegalStateException("Unexpected value: " + result);
			}
		});

		cad.getVersion((result) -> {
			switch (result) {
				case Result.Success<String> success -> System.out.println("Version: " + success.value());
				case Result.Error<String> error -> System.out.println(error.message());
				case Result.Exception<String> exception -> throw new RuntimeException(exception.exception());
				default -> throw new IllegalStateException("Unexpected value: " + result);
			}
		});

		sonoran.shutdown();
	}
}