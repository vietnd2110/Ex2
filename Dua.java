package Ex2;

public class Dua {
    // Các thuộc tính của đũa
    private int id;
    private boolean coSanKhong; // báo trạng thái đũa có được sử dụng hay không

    /****************/

    public Dua(int ID, boolean coSanKhong) {
        setId(ID);
        setCoSanKhong(coSanKhong);
    }

    /****************/

    /**
     * @return id
     */
    public synchronized int getId() {
        return id;
    }

    /**
     * @param id
     *            thay đổi ID của đũa vì có 5 cái đũa
     */
    public synchronized void setId(int id) {
        this.id = id;
    }

    /**
     * @return the có sẵn hay không có=true, không=false;
     */
    public synchronized boolean isCoSanKhong() {
        return coSanKhong;
    }

    /**
     * @param coSanKhong
     *           thay doi gia tri có sẵn hay không có sẵn
     */
    public synchronized void setCoSanKhong(boolean coSanKhong) {
        this.coSanKhong = coSanKhong;
    }
}
