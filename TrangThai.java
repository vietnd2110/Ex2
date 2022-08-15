package Ex2;

public enum TrangThai {
    DOIBUNG("doi bung"), DANGAN("dang an"), DANGSUYNGHI("dang suy nghi");

    private final String str;

    TrangThai(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
