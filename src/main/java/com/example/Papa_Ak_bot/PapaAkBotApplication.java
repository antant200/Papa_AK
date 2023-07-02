package com.example.Papa_Ak_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class PapaAkBotApplication {

	public static void main(String[] args) throws TelegramApiException {
		SpringApplication.run(PapaAkBotApplication.class, args);
		TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
		try {
			botsApi.registerBot(new Bot());
		} catch (TelegramApiException e) {
			throw new RuntimeException("Error registering bot", e);
		}
		while (true) {
			try {
				Thread.sleep(1000); // 1 second delay
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
