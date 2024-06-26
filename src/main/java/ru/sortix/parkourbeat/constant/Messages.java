package ru.sortix.parkourbeat.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {

    public static final String MISSING_PERMISSION = "Недостаточно прав";
    public static final String PLAYER_ONLY = "Команда только для игроков";
    public static final String COMMAND_USAGE = "Используйте";

    public static final String NOT_LEVEL_OWNER = "Вы не являетесь владельцем этого уровня";
    public static final String USE_LEVEL_PARAMETERS_ITEM = "Используйте предмет \"Параметры уровня\"";

    public static final String GLOBAL_DATA_CONVERSION_REPORT =
        "Конвертация данных %d уровней завершена успешно, а также %d с ошибкой";
    public static final String SUCCESSFUL_LEVEL_DATA_CONVERSION = "Конвертация данных уровня %s завершена успешно";
    public static final String FAILED_LEVEL_DATA_CONVERSION = "Конвертация данных уровня %s завершена неудачно";

    public static final String SUCCESSFUL_LEVEL_DELETION = "Вы успешно удалили уровень %s";
    public static final String LEVEL_DELETION_ALREADY_DELETED = "Уровень %s был удален";
    public static final String FAILED_LEVEL_DELETION = "Не удалось удалить уровень %s";
    public static final String PHYSICS_DEBUG_SWITCHED = "Отладка движка физики %s";

}
