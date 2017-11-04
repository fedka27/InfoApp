package test.infoapp.util;

import android.support.annotation.Nullable;

public class TextUtils {

    @Nullable
    public static String withSeparator(String separatorChar, String... values) {
        StringBuilder textSeparated = new StringBuilder();


//        пройтись по всем элементам массива, которые должны быть разределены;
        for (int i = 0; i < values.length; i++) {
//            получение элемента
            String val = values[i];
//            переменная - есть ли следующий элемент?
            boolean isNextVal = false;
//
//            поиск следующего элемента;
            for (int checkNextVal = i + 1; checkNextVal < values.length; checkNextVal++) {
                try {
//                    любой следующий элемент возращает true;
                    if (values[checkNextVal] != null && !values[checkNextVal].isEmpty()) {
                        isNextVal = true;
                        break;
                    }
                } catch (IndexOutOfBoundsException e) {
//                    если следующего элемента нет вернуть результат;
                    isNextVal = false;
                }
            }

//            если следующего элемента нет - разделитель не ставить;
            if (val != null && !val.isEmpty() && !isNextVal) {
                textSeparated.append(val);
            }
//             поставить разделитель если есть еще один элемент;
            else if (val != null && !val.isEmpty() && isNextVal) {
                textSeparated.append(val).append(separatorChar);
            }
        }

        return textSeparated.toString();
    }

}
