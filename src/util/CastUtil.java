package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Classe utilit�ria para fazer a conversão de tipos de uma collection genérica
 * para uma tipada (Generics)
 *
 */
public class CastUtil {

    private CastUtil() {
    }

    /**
     * Converte os objetos de uma Collection qualquer para uma lista tipada
     *
     * @param <T>
     * @param classe
     * @param collection
     * @return
     */
    public static <T> List<T> castList(Class<T> classe, Collection<?> collection) {
        if (collection == null) {
            return null;
        }
        List<T> lista = new ArrayList<>(collection.size());
        for (Object obj : collection) {
            lista.add(classe.cast(obj));
        }
        return lista;
    }
}