package com.example.Papa_Ak_bot;

import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;
import static java.math.RoundingMode.HALF_UP;


public class Bot extends TelegramLongPollingBot {
    String operation;
    String operationMoney;
    boolean check;

    String nameMoney;
    int money;
    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message message = update.getMessage(); //извлекли сообщение
                String chatId = message.getChatId().toString(); // извлекли чат айди
                String response = parseMess(message.getText());
                SendMessage outMessage = new SendMessage(); // наш ответ пользователю
                outMessage.setChatId(chatId);
                outMessage.setText(response);
                outMessage.setParseMode("HTML");
                // Создаем кнопки "Лонг" и "Шорт"
                if (message.getText().equals("/start")) {
                    outMessage.setReplyMarkup(getOperationKeyboard());
                }
                execute(outMessage);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String parseMess(String textMess) {
        String response = null;
        if (textMess.equals("/start")) {
            response = "Бот умеет \n 1) Считать позиции (Лонг/Шорт)";
        } else if (textMess.equals("Лонг") || textMess.equals("Шорт")) {
            check=false;
            response = "Введите размер депозита (без плеча)";
            operation=textMess;
            operationMoney = "депозит";
        }  else if (!check && operationMoney.equals("депозит")){
            money = Integer.parseInt(textMess);
            operationMoney=null;
            check=true;
            response = "Введите название монеты:";
        } else if (textMess.matches("[A-Za-z]+")) {
            nameMoney = textMess;
            response = "Введите точку входа:";
        } else if (isNumeric(textMess)) {
            double entryPoint = Double.parseDouble(textMess);

            int amount1, amount2, amount3, amount4;
            double cost2, cost3,cost4, averageCost2, averageCost3,averageCost4,exitPrice, exitPrice2,
                    exitPriceTreeProc1,exitPriceFiveProc1,exitPriceSevenProc1,
                    exitPriceTreeProc2,exitPriceFiveProc2,exitPriceSevenProc2,
                    exitPriceTreeProc3,exitPriceFiveProc3,exitPriceSevenProc3,
                    exitPriceTreeProc4,exitPriceFiveProc4,exitPriceSevenProc4;
            if (operation.equals("Лонг")) {

                amount1 = (int) Math.ceil((money / entryPoint) * 0.15);

                exitPriceTreeProc1 = 1.03 * entryPoint;
                BigDecimal resultExitPriceTreeProc1 = new BigDecimal(exitPriceTreeProc1).setScale(4, HALF_UP);
                exitPriceFiveProc1 = 1.05 * entryPoint;
                BigDecimal resultExitPriceFiveProc1 = new BigDecimal(exitPriceFiveProc1).setScale(4, HALF_UP);
                exitPriceSevenProc1 = 1.07 * entryPoint;
                BigDecimal resultExitPriceSevenProc1 = new BigDecimal(exitPriceSevenProc1).setScale(4, HALF_UP);


                cost2 = 0.95 * entryPoint;
                BigDecimal resultCost2 = new BigDecimal(cost2).setScale(4, HALF_UP);
                averageCost2 = (cost2 + entryPoint) / 2;
                BigDecimal resultAverageCost2 = new BigDecimal(averageCost2).setScale(4, HALF_UP);
                amount2 = (int) Math.ceil((money / entryPoint) * 0.2);

                exitPriceTreeProc2 = 1.03 * averageCost2;
                BigDecimal resultExitPriceTreeProc2 = new BigDecimal(exitPriceTreeProc2).setScale(4, HALF_UP);
                exitPriceFiveProc2 = 1.05 * averageCost2;
                BigDecimal resultExitPriceFiveProc2 = new BigDecimal(exitPriceFiveProc2).setScale(4, HALF_UP);
                exitPriceSevenProc2 = 1.07 * averageCost2;
                BigDecimal resultExitPriceSevenProc2 = new BigDecimal(exitPriceSevenProc2).setScale(4, HALF_UP);

                int sumAmount2 = amount1 + amount2;



                cost3 = 0.9 * entryPoint;
                BigDecimal resultCost3 = new BigDecimal(cost3).setScale(4, HALF_UP);
                amount3 = (int) ceil((money/ entryPoint) * 0.3);
                averageCost3 = (amount3 * cost3 + amount2 * cost2 + amount1 * entryPoint) / (amount1 + amount2 + amount3);
                BigDecimal resultAverageCost3 = new BigDecimal(averageCost3).setScale(4, HALF_UP);

                exitPriceTreeProc3 = 1.03 * averageCost3;
                BigDecimal resultExitPriceTreeProc3 = new BigDecimal(exitPriceTreeProc3).setScale(4, HALF_UP);
                exitPriceFiveProc3 = 1.05 * averageCost3;
                BigDecimal resultExitPriceFiveProc3 = new BigDecimal(exitPriceFiveProc3).setScale(4, HALF_UP);
                exitPriceSevenProc3 = 1.07 * averageCost3;
                BigDecimal resultExitPriceSevenProc3 = new BigDecimal(exitPriceSevenProc3).setScale(4, HALF_UP);

                int sumAmount3 = amount1 + amount2 + amount3;


                cost4 = 0.75 * entryPoint;
                BigDecimal resultCost4 = new BigDecimal(cost4).setScale(4, HALF_UP);
                amount4 = (int) ceil((money/ entryPoint) * 0.5);
                averageCost4 = (amount4 * cost4 + amount3 * cost3 + amount2 * cost2 + amount1 * entryPoint) / (amount1 + amount2 + amount3 + amount4);
                BigDecimal resultAverageCost4 = new BigDecimal(averageCost4).setScale(4, HALF_UP);

                exitPriceTreeProc4 = 1.03 * averageCost4;
                BigDecimal resultExitPriceTreeProc4 = new BigDecimal(exitPriceTreeProc4).setScale(4, HALF_UP);
                exitPriceFiveProc4 = 1.05 * averageCost4;
                BigDecimal resultExitPriceFiveProc4 = new BigDecimal(exitPriceFiveProc4).setScale(4, HALF_UP);
                exitPriceSevenProc4 = 1.07 * averageCost4;
                BigDecimal resultExitPriceSevenProc4 = new BigDecimal(exitPriceSevenProc4).setScale(4, HALF_UP);

                int sumAmount4 = amount1 + amount2 + amount3 + amount4;


                response = ("<b>\uD83E\uDE99 Монета: " + nameMoney + " </b> (Лонг) " + "\n" +
                        "<b>\uD83D\uDCB0 Депозит: " + money + " </b> \n\n" +


                        "\uD83D\uDFE2  <b>Вход  -->  </b> " + entryPoint +
                        "\n<b>Размер позиции: </b>" + amount1 +
                        "\n\uD83D\uDD34  <b>Выход (3%)  -->  </b>" + resultExitPriceTreeProc1 +
                        "\n\uD83D\uDD34  <b>Выход (5%)  -->  </b>" + resultExitPriceFiveProc1 +
                        "\n\uD83D\uDD34  <b>Выход (7%)  -->  </b>" + resultExitPriceSevenProc1 +


                        "\n\n\uD83D\uDFE2 \uD83D\uDFE2  <b>Вход  -->  </b> " + resultCost2 +
                        "\n<b>Размер позиции: </b>" + amount2 +
                        "\n\uD83D\uDD34 \uD83D\uDD34  <b>Средняя цена  -->  </b>" + resultAverageCost2 +
                        "\n\uD83D\uDD34 \uD83D\uDD34  <b>Выход (3%)  -->  </b>" + resultExitPriceTreeProc2 +
                        "\n\uD83D\uDD34 \uD83D\uDD34  <b>Выход (5%)  -->  </b>" + resultExitPriceFiveProc2 +
                        "\n\uD83D\uDD34 \uD83D\uDD34  <b>Выход (7%)  -->  </b>" + resultExitPriceSevenProc2 +
                        "\n\uD83D\uDD34 \uD83D\uDD34  <b>Итоговое количество позиций  -->  </b>" + sumAmount2 +


                        "\n\n\uD83D\uDFE2 \uD83D\uDFE2 \uD83D\uDFE2  <b>Вход  -->  </b>" + resultCost3+
                        "\n<b>Размер позиции: </b>" + amount3 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Средняя цена  -->  </b>" + resultAverageCost3 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Выход (3%)  -->  </b>" + resultExitPriceTreeProc3 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Выход (5%)  -->  </b>" + resultExitPriceFiveProc3 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Выход (7%)  -->  </b>" + resultExitPriceSevenProc3 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Итоговое количество позиций  -->  </b>" + sumAmount3 +


                        "\n\n\uD83D\uDFE2 \uD83D\uDFE2 \uD83D\uDFE2 \uD83D\uDFE2  <b>Вход  -->  </b>" + resultCost4+
                        "\n<b>Размер позиции: </b>" + amount4 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Средняя цена  -->  </b>" + resultAverageCost4 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Выход (3%)  -->  </b>" + resultExitPriceTreeProc4 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Выход (5%)  -->  </b>" + resultExitPriceFiveProc4 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Выход (7%)  -->  </b>" + resultExitPriceSevenProc4 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Итоговое количество позиций  -->  </b>" + sumAmount4);

            } else if (operation.equals("Шорт")) {
                amount1 = (int) Math.ceil((money / entryPoint) * 0.15);

                exitPriceTreeProc1 = 0.97 * entryPoint;
                BigDecimal resultExitPriceTreeProc1 = new BigDecimal(exitPriceTreeProc1).setScale(4, HALF_UP);
                exitPriceFiveProc1 = 0.95 * entryPoint;
                BigDecimal resultExitPriceFiveProc1 = new BigDecimal(exitPriceFiveProc1).setScale(4, HALF_UP);
                exitPriceSevenProc1 = 0.93 * entryPoint;
                BigDecimal resultExitPriceSevenProc1 = new BigDecimal(exitPriceSevenProc1).setScale(4, HALF_UP);


                cost2 = 1.05 * entryPoint;
                BigDecimal resultCost2 = new BigDecimal(cost2).setScale(4, HALF_UP);
                amount2 = amount1;
                averageCost2 = (cost2 + entryPoint) / 2;
                BigDecimal resultAverageCost2 = new BigDecimal(averageCost2).setScale(4, HALF_UP);

                exitPriceTreeProc2 = 0.97 * averageCost2;
                BigDecimal resultExitPriceTreeProc2 = new BigDecimal(exitPriceTreeProc2).setScale(4, HALF_UP);
                exitPriceFiveProc2 = 0.95 * averageCost2;
                BigDecimal resultExitPriceFiveProc2 = new BigDecimal(exitPriceFiveProc2).setScale(4, HALF_UP);
                exitPriceSevenProc2 = 0.93 * averageCost2;
                BigDecimal resultExitPriceSevenProc2 = new BigDecimal(exitPriceSevenProc2).setScale(4, HALF_UP);

                int sumAmount2 = amount1 + amount2;

                cost3 = 1.1 * entryPoint;
                BigDecimal resultCost3 = new BigDecimal(cost3).setScale(4, HALF_UP);
                amount3 = (int) ceil((money / entryPoint) * 0.2);
                averageCost3 = (amount3 * cost3 + amount2 * cost2 + amount1 * entryPoint) / (amount1 + amount2 + amount3);
                BigDecimal resultAverageCost3 = new BigDecimal(averageCost3).setScale(4, HALF_UP);

                exitPriceTreeProc3 = 0.97 * averageCost3;
                BigDecimal resultExitPriceTreeProc3 = new BigDecimal(exitPriceTreeProc3).setScale(4, HALF_UP);
                exitPriceFiveProc3 = 0.95 * averageCost3;
                BigDecimal resultExitPriceFiveProc3 = new BigDecimal(exitPriceFiveProc3).setScale(4, HALF_UP);
                exitPriceSevenProc3 = 0.93 * averageCost3;
                BigDecimal resultExitPriceSevenProc3 = new BigDecimal(exitPriceSevenProc3).setScale(4, HALF_UP);

                int sumAmount3 = amount1 + amount2 + amount3;


                cost4 = 1.25 * entryPoint;
                BigDecimal resultCost4 = new BigDecimal(cost4).setScale(4, HALF_UP);
                amount4 = (int) ceil((money / entryPoint) * 0.37);
                averageCost4 = (amount4 * cost4 + amount3 * cost3 + amount2 * cost2 + amount1 * entryPoint) / (amount1 + amount2 + amount3 + amount4);
                BigDecimal resultAverageCost4 = new BigDecimal(averageCost4).setScale(4, HALF_UP);

                exitPriceTreeProc4 = 0.97 * averageCost4;
                BigDecimal resultExitPriceTreeProc4 = new BigDecimal(exitPriceTreeProc4).setScale(4, HALF_UP);
                exitPriceFiveProc4 = 0.95 * averageCost4;
                BigDecimal resultExitPriceFiveProc4 = new BigDecimal(exitPriceFiveProc4).setScale(4, HALF_UP);
                exitPriceSevenProc4 = 0.93 * averageCost4;
                BigDecimal resultExitPriceSevenProc4 = new BigDecimal(exitPriceSevenProc4).setScale(4, HALF_UP);

                int sumAmount4 = amount1 + amount2 + amount3 + amount4;

                response = ("<b>\uD83E\uDE99 Монета: " + nameMoney + "</b> (Шорт) \n" +
                        "<b>\uD83D\uDCB0 Депозит: " + money + " </b> \n\n" +


                        "\uD83D\uDFE2  <b>Вход  -->  </b> " + entryPoint +
                        "\n<b>Размер позиции: </b>" + amount1 +
                        "\n\uD83D\uDD34  <b>Выход (3%)  -->  </b>" + resultExitPriceTreeProc1 +
                        "\n\uD83D\uDD34  <b>Выход (5%)  -->  </b>" + resultExitPriceFiveProc1 +
                        "\n\uD83D\uDD34  <b>Выход (7%)  -->  </b>" + resultExitPriceSevenProc1 +


                        "\n\n\uD83D\uDFE2 \uD83D\uDFE2  <b>Вход  -->  </b> " + resultCost2 +
                        "\n<b>Размер позиции: </b>" + amount2 +
                        "\n\uD83D\uDD34 \uD83D\uDD34  <b>Средняя цена  -->  </b>" + resultAverageCost2 +
                        "\n\uD83D\uDD34 \uD83D\uDD34  <b>Выход (3%)  -->  </b>" + resultExitPriceTreeProc2 +
                        "\n\uD83D\uDD34 \uD83D\uDD34  <b>Выход (5%)  -->  </b>" + resultExitPriceFiveProc2 +
                        "\n\uD83D\uDD34 \uD83D\uDD34  <b>Выход (7%)  -->  </b>" + resultExitPriceSevenProc2 +
                        "\n\uD83D\uDD34 \uD83D\uDD34  <b>Итоговое количество позиций  -->  </b>" + sumAmount2 +


                        "\n\n\uD83D\uDFE2 \uD83D\uDFE2 \uD83D\uDFE2  <b>Вход  -->  </b>" + resultCost3+
                        "\n<b>Размер позиции: </b>" + amount3 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Средняя цена  -->  </b>" + resultAverageCost3 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Выход (3%)  -->  </b>" + resultExitPriceTreeProc3 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Выход (5%)  -->  </b>" + resultExitPriceFiveProc3 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Выход (7%)  -->  </b>" + resultExitPriceSevenProc3 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Итоговое количество позиций  -->  </b>" + sumAmount3 +


                        "\n\n\uD83D\uDFE2 \uD83D\uDFE2 \uD83D\uDFE2 \uD83D\uDFE2  <b>Вход  -->  </b>" + resultCost4+
                        "\n<b>Размер позиции: </b>" + amount4 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Средняя цена  -->  </b>" + resultAverageCost4 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Выход (3%)  -->  </b>" + resultExitPriceTreeProc4 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Выход (5%)  -->  </b>" + resultExitPriceFiveProc4 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Выход (7%)  -->  </b>" + resultExitPriceSevenProc4 +
                        "\n\uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34 \uD83D\uDD34  <b>Итоговое количество позиций  -->  </b>" + sumAmount4);

            }

        } else {
            response = "Введи название на английском или используй точку при вводе числа";
        }
        return response;
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    // Метод создания кнопок "Лонг" и "Шорт" и "Конвертор валют"
    public static ReplyKeyboardMarkup getOperationKeyboard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Лонг");
        row.add("Шорт");
        keyboard.add(row);
        markup.setKeyboard(keyboard);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        return markup;
    }


    @Override
    public String getBotUsername() {
        return "Papa_Ak_bot";
    }

    @Override
    public String getBotToken() {
        return "6270969692:AAElvvioZSRGftnZfCSAVHWf9c4gZ9U-umY";
    }
}
