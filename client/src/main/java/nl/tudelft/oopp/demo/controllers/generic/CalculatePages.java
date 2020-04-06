package nl.tudelft.oopp.demo.controllers.generic;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.util.Pair;

/**
 * Takes care of calculating and displaying the right pages for some table.
 */
public class CalculatePages {

    /**
     * Calculates the pages for some table.
     * @param itemList = the list of items.
     * @param total = the total amount of pages.
     * @param page = the current page.
     * @param items = the list of items.
     * @param size = the size of one page.
     * @param <T> = some type.
     * @return a pair of the new page and new total size.
     */
    public static <T> Pair<Integer, Integer> calculatePages(ObservableList<T> itemList, Integer total, Integer page, List<T> items, Integer size) {
        itemList.clear();
        total = (int) (Math.ceil(items.size() / ((double) size)));
        if (items.size() == 0) {
            page = 0;
        }
        if (page == 0 && items.size() != 0) {
            page = 1;
        }
        if (total > 0) {
            page = Math.max(page, 1);
        }
        if (items.size() > size) {
            for (int i = 0; i < size; i++) {
                try {
                    itemList.add(items.get((i - size) + page * size));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        } else {
            itemList.addAll(items);
        }
        return new Pair<>(total, page);
    }
}
