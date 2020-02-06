package org.interlink.grouporder.core.handler;

import org.interlink.grouporder.core.exceptions.*;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpSession;

class ExceptionsTypes {
    private ExceptionsTypes() {
    }

    public static HttpStatus getStatus(Exception e) {
        if (e instanceof BadRequestException) {
            return HttpStatus.BAD_REQUEST; // Отримання неправильних данних
        } else if (e instanceof ForbiddenException) {
            return HttpStatus.FORBIDDEN; // Запрос прийнятий однак не хочу виконувати його
        }else if (e instanceof ClassNotFoundException) {
            return HttpStatus.NOT_FOUND; // Сервер не може знайти класс
        } else if (e instanceof UnprocessableEntityException) {
            return HttpStatus.UNPROCESSABLE_ENTITY; // Тип і синтаксис правильні, однак, серверу не вдалося опрацювати інструкцію вмісту
        } else if (e instanceof NotModifiedException) {
            return HttpStatus.NOT_MODIFIED; // Не можу зробити це бо вже хтось другий зробив це.
        } else if (e instanceof GroupOrderLockedException) {
            return HttpStatus.LOCKED; // Ты кто такой? Я тебя не звал. Иди ацуда.
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR; // Непередбачувана умова яка не дозволила виконати запрос
        }
    }
}
