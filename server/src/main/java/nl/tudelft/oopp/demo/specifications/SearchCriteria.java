package nl.tudelft.oopp.demo.specifications;

/**
 * Holds a basic representation of a constraint.
 */
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;

    /**
     * Stores key, operation, and value fields for filtering.
     * @param key the key whose value must be checked.
     * @param operation the operation performed.
     * @param value the value that needs to be matched.
     */
    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }
}
